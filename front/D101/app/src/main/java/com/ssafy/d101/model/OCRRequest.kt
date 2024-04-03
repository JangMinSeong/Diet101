package com.ssafy.d101.model

data class OCRRequest (
    val name : String,
    val type : String,
    val time : String,
    val calorie : Int,
    val carbohydrate : Double,
    val protein : Double,
    val fat : Double
)