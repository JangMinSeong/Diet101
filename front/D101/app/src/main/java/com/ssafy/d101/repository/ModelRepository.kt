package com.ssafy.d101.repository

import android.net.Uri
import android.util.Log
import com.ssafy.d101.api.ModelService
import com.ssafy.d101.api.UserService
import com.ssafy.d101.model.UserInfo
import com.ssafy.d101.model.UserSubInfo
import com.ssafy.d101.model.YoloResponse
import com.ssafy.d101.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
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

    suspend fun TransferImageToYolo(fileUri: Uri): Result<List<YoloResponse>> {
        return try {
            val file = File(fileUri.path) // Uri를 실제 파일 경로로 변환
            val contentType = "image/jpeg".toMediaType()
            val requestFile = file.asRequestBody(contentType)
            val body = MultipartBody.Part.createFormData("file", file.name, requestFile)
            val response = modelService.checkCal(body)
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

    fun setImageUri(uri : Uri) {

    }
}