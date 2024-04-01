package com.ssafy.d101.model

data class FoodAddInfo(
    val id: Int,
    val name: String,
    val majorCategory: String,
    val minorCategory: String,
    val dbGroup: String,
    val manufacturer: String,
    val portionSize: Int,
    val totalSize: Double,
    val unit: String,
    val eatenAmount: Double,
    val calorie: Int,
    val carbohydrate: Double,
    val protein: Double,
    val fat: Double,
)
