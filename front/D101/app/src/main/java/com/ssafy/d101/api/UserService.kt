package com.ssafy.d101.api

import com.ssafy.d101.model.FoodInfo
import com.ssafy.d101.model.FoodItem
import com.ssafy.d101.model.FoodResponse
import com.ssafy.d101.model.RegisterResponse
import com.ssafy.d101.model.UserInfo
import com.ssafy.d101.model.UserSubInfo
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import retrofit2.http.PUT

interface UserService {
    @POST("user/login")
    fun registerUser(@Body userInfo: UserInfo): Call<RegisterResponse>

    @GET("user/info/profile")
    suspend fun getUserSubInfo() : Response<UserSubInfo>

    @PUT("user/info/profile")
    suspend fun updateUserSubInfo(@Body userSubInfo: UserSubInfo) : Response<Any>
}

interface foodSearchService {
    @GET("food/search")
    suspend fun fetchFoodItems(@Query("name") foodName: String): Response<List<FoodItem>>
}

interface UserAdditionFoodService {
    @POST("food/insert")
    suspend fun postUserAdditionFood(@Body foodInfo : FoodInfo): Response<FoodResponse>
}