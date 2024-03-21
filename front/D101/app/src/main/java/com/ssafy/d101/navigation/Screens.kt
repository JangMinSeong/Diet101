package com.ssafy.d101.navigation

sealed class Screens(val route: String) {
    object Landing : Screens("landing")
    object Home : Screens("home")
    object MyPage : Screens("myPage")
    object BMI : Screens("bmi")
    object Allergy : Screens("allergy")
    object Start : Screens("start")
    object UserInfo : Screens("userinfo")
}