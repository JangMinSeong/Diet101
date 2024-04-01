package com.ssafy.d101.repository

import android.content.Context
import android.net.Uri
import android.util.Log
import com.ssafy.d101.api.ModelService
import com.ssafy.d101.api.UserService
import com.ssafy.d101.model.UserInfo
import com.ssafy.d101.model.UserSubInfo
import com.ssafy.d101.model.YoloResponse
import com.ssafy.d101.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject

class ModelRepository @Inject constructor(private val modelService: ModelService) {
    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri = _imageUri.asStateFlow()

    private val _yoloInfo = MutableStateFlow<List<YoloResponse>?>(null)
    val yoloInfo = _yoloInfo.asStateFlow()

    suspend fun transferImageToYolo(context: Context) : StateFlow<List<YoloResponse>?> {
        val result = transferImageToYoloFromBack(context)
        result.onSuccess { yoloInfo ->
            _yoloInfo.value = yoloInfo
        }.onFailure { exception ->
            Log.e("ModelRepository", "Failed to get model yolo: ${exception.message}")
        }
        return yoloInfo
    }

    suspend fun transferImageToYoloFromBack(context: Context): Result<List<YoloResponse>> {
        return try {
            val imageUri = imageUri.value ?: return Result.failure(Exception("Image Uri is null"))
            val inputStream = context.contentResolver.openInputStream(imageUri) ?: return Result.failure(Exception("Failed to open image stream"))
            val contentType = "image/jpeg".toMediaTypeOrNull()

            // Buffer the inputStream into a temporary file
            val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir).apply {
                outputStream().use {
                    inputStream.copyTo(it)
                }
            }

            val requestFile = tempFile.asRequestBody(contentType)
            val body = MultipartBody.Part.createFormData("file", tempFile.name, requestFile)
            val response = modelService.checkCal(body)

            tempFile.delete() // Clean up the temporary file

            if (response.isSuccessful) {
                _yoloInfo.emit(response.body())
                Result.success(response.body()!!)

            } else {
                Log.e("Model", "Failed to get YoloResponse")
                Result.failure(Exception("Failed to get YoloResponse"))
            }
        } catch (e: Exception) {
            Log.e("Model", "Exception during TransferImageToYolo", e)
            Result.failure(e)
        }
    }

    suspend fun setImageUri(uri : Uri) {
        _imageUri.emit(uri)
    }
}