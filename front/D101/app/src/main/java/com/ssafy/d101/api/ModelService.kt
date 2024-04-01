package com.ssafy.d101.api

import com.ssafy.d101.model.YoloResponse
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ModelService {
    @Multipart
    @POST("model/checkcal")
    suspend fun checkCal(@Part file: MultipartBody.Part) : Response<List<YoloResponse>>

    @POST("model/ocr")
    fun checkOCR()
}