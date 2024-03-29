package com.ssafy.d101.api

import com.ssafy.d101.model.FoodItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FoodSearchService {
    @GET("food/search")
    suspend fun fetchFoodItems(@Query("name") foodName: String): Response<List<FoodItem>>
}