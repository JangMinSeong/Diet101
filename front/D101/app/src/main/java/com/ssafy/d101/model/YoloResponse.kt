package com.ssafy.d101.model

data class YoloResponse(
    val tag: String,
    val left_top: List<Double>,
    val width: Double,
    val height: Double,
    val yoloFoodDto: YoloFood
)


data class YoloFood(
    val food_id: Long,
    val calorie: Int,
    val carbohydrate: Double,
    val protein: Double,
    val fat: Double
)
