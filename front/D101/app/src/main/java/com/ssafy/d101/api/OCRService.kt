package com.ssafy.d101.api

import com.ssafy.d101.model.OCRRequest

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface OCRService {
    @POST("ocr")
    suspend fun saveOCR(@Body ocrRequest: OCRRequest) : Response<String>
}