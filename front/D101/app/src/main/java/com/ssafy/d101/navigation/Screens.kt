package com.ssafy.d101.navigation

sealed class Screens(val route: String) {
    data object Landing : Screens("landing")
    data object Home : Screens("home")
    data object MyPage : Screens("myPage")
    data object BMI : Screens("bmi")
    data object Allergy : Screens("allergy")
    data object Start : Screens("start")
    data object SignUp : Screens("signUp")
    data object SignUpComplete : Screens("signUpComplete")
    data object LoginSuccess : Screens("loginSuccess")
    data object UserInfo : Screens("userinfo")
    data object AnalysisDiet : Screens("anaylsisDiet")
    data object Recommend : Screens("recommend")
    data object FoodResist : Screens("foodResist")
    data object FoodAddition : Screens("foodAddition")
    data object FoodSearch : Screens("foodSearch/{foodName}") {
        fun createRoute(foodName: String) = "foodSearch/$foodName"
    }
    data object Height : Screens("height")
    data object Weight : Screens("weight")
    data object ActivityLevel : Screens("activityLevel")
    data object Loading: Screens("loading")
}