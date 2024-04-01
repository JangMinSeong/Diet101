package com.ssafy.d101.api

import com.ssafy.d101.model.FoodInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodService {
    @GET("food/recommend")
    suspend fun getRecommendFood(@Query("kcal") kcal: String) : Response<List<FoodInfo>>

}