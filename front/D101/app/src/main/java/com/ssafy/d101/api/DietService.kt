package com.ssafy.d101.api;

import com.ssafy.d101.model.AnalysisDiet
import com.ssafy.d101.model.DietInfo;

import retrofit2.Call;
import retrofit2.Response
import retrofit2.http.GET;
import retrofit2.http.Query;

interface DietService {
    @GET("diet/analysis")
    suspend fun getAnalysisDiet(
        @Query("date") date: String,
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String
    ): Response<AnalysisDiet>

    @GET("diet/date")
    suspend fun getDayDiet(@Query("date") date: String): Response<List<DietInfo>>
}