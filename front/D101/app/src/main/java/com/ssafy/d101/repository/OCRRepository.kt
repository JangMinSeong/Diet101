package com.ssafy.d101.repository

import android.util.Log
import com.ssafy.d101.api.OCRService
import com.ssafy.d101.model.OCRInfo
import com.ssafy.d101.model.OCRRequest
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class OCRRepository @Inject constructor(private val ocrService: OCRService) {

    suspend fun saveOCRFromBack(ocrReq: OCRRequest): Result<String> {
        return try {
            val response = ocrService.saveOCR(ocrReq)
            if(response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to save OCR"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    private val _dayOCR = MutableStateFlow<List<OCRInfo>>(emptyList())
    val dayOCR: StateFlow<List<OCRInfo>> = _dayOCR.asStateFlow()


    suspend fun getDayOCR(date: String): StateFlow<List<OCRInfo>?> {
        val result = getDayOCRFromBack(date)
        result.onSuccess { ocr ->
            _dayOCR.value = ocr
        }.onFailure { exception ->
            Log.e("OCRRepository", "Failed to get day OCR: ${exception.message}")
        }
        return dayOCR
    }

    private suspend fun getDayOCRFromBack(date: String): Result<List<OCRInfo>> {
        return try {
            val response = ocrService.getDateOCR(date)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get day OCR"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}