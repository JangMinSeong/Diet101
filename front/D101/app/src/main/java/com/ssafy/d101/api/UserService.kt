package com.ssafy.d101.api

import com.ssafy.d101.model.RegisterResponse
import com.ssafy.d101.model.UserInfo
import com.ssafy.d101.model.UserSubInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface UserService {
    @POST("user/login")
    suspend fun registerUser(@Body userInfo: UserInfo): Response<RegisterResponse>

    @GET("user/info/profile")
    suspend fun getUserSubInfo() : Response<UserSubInfo>

    @POST("user/info/profile")
    suspend fun updateUserSubInfo(@Body userSubInfo: UserSubInfo) : Response<Any>
}