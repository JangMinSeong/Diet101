package com.ssafy.d101.api

import com.ssafy.d101.model.FoodInfo
import com.ssafy.d101.model.FoodResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface userAdditionFoodService {
    @POST("food/insert")
    suspend fun postUserAdditionFood(@Body foodInfo : FoodInfo): Response<FoodResponse>
}