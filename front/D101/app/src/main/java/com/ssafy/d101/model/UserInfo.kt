package com.ssafy.d101.model

data class UserInfo(
    val oauthId: String,
    val email: String,
    val username: String,
    val image: String,
    val gender: String,
    val age: Int,
)

data class UserSubInfo(
    val activity : Int = -1,
    val calorie : Int = 0,
    val height : Int = 0,
    val weight : Int = 0
)

data class RegisterResponse(
    val email: String,
    val accessToken: String,
    val refreshToken: String
)