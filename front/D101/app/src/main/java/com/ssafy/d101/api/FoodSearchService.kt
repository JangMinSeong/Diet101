package com.ssafy.d101.api

import com.ssafy.d101.model.FoodInfo
import com.ssafy.d101.model.FoodItem
import com.ssafy.d101.model.FoodResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface FoodSearchService {
    // 음식 API 불러오는 로직
    @GET("food/search")
    suspend fun fetchFoodItems(@Query("name") foodName: String): Response<List<FoodItem>>

    @GET("food/ranking")
    suspend fun testest()

    // 사용자가 추가한 음식 POST 요청
    @POST("food/insert")
    suspend fun postUserAdditionFood(@Body foodInfo : FoodInfo): Response<FoodResponse>
}