package com.ssafy.d101.navigation

sealed class Screens(val route: String) {
    object Landing : Screens("landing")
    object Home : Screens("home")
    object MyPage : Screens("myPage")
}