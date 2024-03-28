package com.ssafy.d101.api

import com.ssafy.d101.model.RegisterResponse
import com.ssafy.d101.model.UserInfo
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("user/login")
    fun registerUser(@Body userInfo: UserInfo): Call<RegisterResponse>

    @POST("user/reissue")
    fun reissueToken(@Body registerResponse: RegisterResponse): Call<RegisterResponse>
}