package com.ssafy.d101.model

enum class Dunchfast {
    BREAKFAST, BRUNCH, LUNCH, LINNER, DINNER, NIGHT, SNACK, BEVERAGE, ALCOHOL
}

data class IntakeInfo (
    val food : FoodInfo,
    val amount : Double,
)

data class DietInfo(
    val meal_id : Long,
    val image : String,
    val time : String,
    val type : Dunchfast,
    val kcal : Int,
    val intake : List<IntakeInfo>
)

data class Diets (
    val meals : List<DietInfo>
)

data class CalAnnualNutrient(
    val month : Int,
    val totalCalorie : Int,
    val totalCarbohydrate : Double,
    val totalProtein : Double,
    val totalFat : Double
)

data class AnalysisDiet(
    val dailyDiet : List<DietInfo>,
    val weeklyDiet : List<DietInfo>,
    val annualNutrients : List<CalAnnualNutrient>,
    val totalRank : List<String>
)

data class CreateMealReq(
    val type: String,
    val time: String,
    val intakes: List<IntakeReq>
)

data class IntakeReq(
    val food_id: Long,
    val name : String,
    val amount: Double,
    val kcal: Int,
    val carbohydrate: Double,
    val protein: Double,
    val fat: Double
)

data class DailyNutrient(
    val totalCalorie: Int,
    val totalCarbohydrate: Double,
    val totalProtein: Double,
    val totalFat: Double
)