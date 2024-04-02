package com.ssafy.d101.repository

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import com.ssafy.d101.api.ModelService
import com.ssafy.d101.api.UserService
import com.ssafy.d101.model.OCRResponse
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

    private val _ocrInfo = MutableStateFlow<OCRResponse?> (null)
    val ocrInfo = _ocrInfo.asStateFlow()

    private val _option = MutableStateFlow<Boolean?>(true)   //true : yolo, false : ocr
    val option = _option.asStateFlow()

    private val _context = MutableStateFlow<Context?>(null)
    val context = _context.asStateFlow()

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

            // 이미지 크기를 줄이기 위한 BitmapFactory.Options 설정
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true // 실제 Bitmap을 생성하지 않고, 이미지의 크기 정보만을 로드
                BitmapFactory.decodeStream(inputStream, null, this)
                inSampleSize = calculateInSampleSize(this, 800, 800) // 원하는 최대 너비와 높이
                inJustDecodeBounds = false // Bitmap을 실제로 로드
            }
            inputStream.close()

            // 크기가 조정된 Bitmap 로드
            val resizedInputStream = context.contentResolver.openInputStream(imageUri) ?: return Result.failure(Exception("Failed to open image stream again"))
            val bitmap = BitmapFactory.decodeStream(resizedInputStream, null, options)
            resizedInputStream.close()

            // Bitmap을 임시 파일로 저장
            val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir).apply {
                outputStream().use { out ->
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 85, out) // JPEG 형식으로 압축. 품질은 85로 설정
                }
            }

            val contentType = "image/jpeg".toMediaTypeOrNull()
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


    suspend fun transferImageToOCR(context: Context) : StateFlow<OCRResponse?> {
        val result = transferImageToOCRFromBack(context)
        result.onSuccess { ocrInfo ->
            _ocrInfo.value = ocrInfo
        }.onFailure { exception ->
            Log.e("ModelRepository", "Failed to get model yolo: ${exception.message}")
        }
        return ocrInfo
    }

    suspend fun transferImageToOCRFromBack(context: Context): Result<OCRResponse> {
        return try {
            val imageUri = imageUri.value ?: return Result.failure(Exception("Image Uri is null"))
            val inputStream = context.contentResolver.openInputStream(imageUri) ?: return Result.failure(Exception("Failed to open image stream"))

            // 이미지 크기를 줄이기 위한 BitmapFactory.Options 설정
            val options = BitmapFactory.Options().apply {
                inJustDecodeBounds = true // 실제 Bitmap을 생성하지 않고, 이미지의 크기 정보만을 로드
                BitmapFactory.decodeStream(inputStream, null, this)
                inSampleSize = calculateInSampleSize(this, 800, 800) // 원하는 최대 너비와 높이
                inJustDecodeBounds = false // Bitmap을 실제로 로드
            }
            inputStream.close()

            // 크기가 조정된 Bitmap 로드
            val resizedInputStream = context.contentResolver.openInputStream(imageUri) ?: return Result.failure(Exception("Failed to open image stream again"))
            val bitmap = BitmapFactory.decodeStream(resizedInputStream, null, options)
            resizedInputStream.close()

            // Bitmap을 임시 파일로 저장
            val tempFile = File.createTempFile("upload", ".jpg", context.cacheDir).apply {
                outputStream().use { out ->
                    bitmap?.compress(Bitmap.CompressFormat.JPEG, 85, out) // JPEG 형식으로 압축. 품질은 85로 설정
                }
            }

            val contentType = "image/jpeg".toMediaTypeOrNull()
            val requestFile = tempFile.asRequestBody(contentType)
            val body = MultipartBody.Part.createFormData("file", tempFile.name, requestFile)
            val response = modelService.checkOCR(body)

            tempFile.delete() // Clean up the temporary file

            if (response.isSuccessful) {
                _ocrInfo.emit(response.body())
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

    suspend fun setOption(option : Boolean) {
        _option.emit(option)
    }
    suspend fun setImageUri(uri : Uri) {
        _imageUri.emit(uri)
    }
    suspend fun setContext(context: Context) {
        _context.emit(context)
    }

    // 비트맵의 샘플링 크기를 계산
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val (height: Int, width: Int) = options.run { outHeight to outWidth }
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val halfHeight: Int = height / 2
            val halfWidth: Int = width / 2

            // 샘플링 사이즈를 계산. 이 값은 2의 거듭제곱이어야 합니다.
            while ((halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }
}