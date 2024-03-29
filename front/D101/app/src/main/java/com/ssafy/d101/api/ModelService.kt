package com.ssafy.d101.api

import retrofit2.http.POST

interface ModelService {
    @POST("model/checkcal")
    fun checkCal()

    @POST("model/ocr")
    fun checkOCR()
}