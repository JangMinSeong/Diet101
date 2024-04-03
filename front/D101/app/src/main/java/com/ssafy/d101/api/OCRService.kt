package com.ssafy.d101.api

import com.ssafy.d101.model.OCRInfo
import com.ssafy.d101.model.OCRRequest

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface OCRService {
    @POST("ocr")
    suspend fun saveOCR(@Body ocrRequest: OCRRequest) : Response<String>

    @GET("ocr/date")
    suspend fun getDateOCR (@Query("date") date: String) : Response<List<OCRInfo>>
}