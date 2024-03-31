package com.ssafy.d101.api;

import com.ssafy.d101.model.AnalysisDiet
import com.ssafy.d101.model.DietInfo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

interface DietService {
    @GET("diet/analysis")
    suspend fun getWeekDiet(
        @Query("date") date: String,
        @Query("dateFrom") dateFrom: String,
        @Query("dateTo") dateTo: String
    ): AnalysisDiet

}
