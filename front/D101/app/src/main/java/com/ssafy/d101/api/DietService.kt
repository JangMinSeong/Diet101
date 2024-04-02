package com.ssafy.d101.api

import com.ssafy.d101.model.AnalysisDiet
import com.ssafy.d101.model.CreateMealReq
import com.ssafy.d101.model.DietInfo
import okhttp3.MultipartBody

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface DietService {
    @GET("diet/analysis")
    suspend fun getAnalysisDiet(
        @Query("date") date: String,
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String
    ): Response<AnalysisDiet>

    @GET("diet/date")
    suspend fun getDayDiet(@Query("date") date: String): Response<List<DietInfo>>

    @POST("diet/meal")
    suspend fun saveMeal(@Part file: MultipartBody.Part, @Body createMealReq: CreateMealReq): Response<Any>
}