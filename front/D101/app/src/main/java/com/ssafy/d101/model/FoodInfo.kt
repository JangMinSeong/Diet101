package com.ssafy.d101.model

data class FoodInfo(
    val id: Int,
    val name: String,
    val majorCategory: String,
    val minorCategory: String,
    val dbGroup: String,
    val manufacturer: String,
    val portionSize: Int,
    val totalSize: Double,
    val unit: String,
    val calorie: Int,
    val carbohydrate: Double,
    val protein: Double,
    val fat: Double,
    val transFat: Double,
    val saturatedFat: Double,
    val cholesterol: Double,
    val natrium: Double,
    val sugar: Double
)

data class FoodResponse(
    val name: String,
    val majorCategory: String,
    val minorCategory: String,
    val dbGroup: String,
    val manufacturer: String,
    val portionSize: Int,
    val totalSize: Int,
    val unit: String,
    val calorie: Int,
    val carbohydrate: Double,
    val protein: Double,
    val fat: Double,
    )