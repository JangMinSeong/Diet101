package com.ssafy.d101.model

data class UserInfo(
    val oauthId: String,
    val email: String,
    val username: String,
    val image: String,
    val gender: String,
    val age: Int,
)

data class RegisterResponse(
    val success: Boolean,
    val message: String,
)
