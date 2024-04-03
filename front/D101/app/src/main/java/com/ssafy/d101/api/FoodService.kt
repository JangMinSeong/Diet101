package com.ssafy.d101.api

import com.ssafy.d101.model.AllergyInfo
import com.ssafy.d101.model.FoodInfo
import com.ssafy.d101.model.FoodResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FoodService {
    @GET("food/recommend")
    suspend fun getRecommendFood(@Query("kcal") kcal: String) : Response<List<FoodInfo>>
}