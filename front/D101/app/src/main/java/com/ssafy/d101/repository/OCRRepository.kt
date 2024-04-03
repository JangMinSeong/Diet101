package com.ssafy.d101.repository

import com.ssafy.d101.api.OCRService
import com.ssafy.d101.model.OCRRequest
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

}