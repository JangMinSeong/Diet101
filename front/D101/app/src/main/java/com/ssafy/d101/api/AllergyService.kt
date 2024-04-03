package com.ssafy.d101.api

import com.ssafy.d101.model.AllergyInfo
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AllergyService {
    @POST("user/info/allergy")
    suspend fun postAllergy(@Body allergyInfo: AllergyInfo): Response<AllergyInfo>
}