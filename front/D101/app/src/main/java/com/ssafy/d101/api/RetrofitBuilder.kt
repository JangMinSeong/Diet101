package com.ssafy.d101.api

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    private const val BASE_URL = "http://j10d101.p.ssafy.io:8000/api/"
    private const val TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJxa3J0a2ZrZDE1OUBuYXZlci5jb20iLCJqdGkiOiJBVEsiLCJhdXRoIjoiUk9MRV9VU0VSIiwiZXhwIjozNzM0MTkxNTg1MDg0fQ.k-jCWgCQDl7XE_wuusVNQU_WTxK-232kgZDvFOFdn1o"

    // Interceptor 생성
    private val authInterceptor = Interceptor { chain ->
        val newRequest = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer $TOKEN")
            .build()
        chain.proceed(newRequest)
    }

    // OkHttpClient 인스턴스를 생성
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()

    val userService: UserService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserService::class.java)
    }

    // foodSearchService
    val foodSerchService: foodSearchService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(foodSearchService::class.java)
    }

    // userAdditionFood
    val userAdditionFoodService: UserAdditionFoodService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(UserAdditionFoodService::class.java)
    }
}