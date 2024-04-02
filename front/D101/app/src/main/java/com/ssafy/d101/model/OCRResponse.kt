package com.ssafy.d101.model

data class OCRResponse (
    val calorie : Int,
    val carbohydrate : Double,
    val sugar : Double,
    val protein : Double,
    val fat : Double,
    val saturated : Double,
    val trans : Double,
    val cholesterol : Double,
    val sodium : Double
)