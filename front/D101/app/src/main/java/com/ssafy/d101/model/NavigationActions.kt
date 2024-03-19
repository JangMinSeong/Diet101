package com.ssafy.d101.model

import androidx.navigation.NavHostController

class NavigationActions(private val navController: NavHostController) {
    fun navigateToLanding() = navController.navigate("landing") {
        popUpTo("main") {
            inclusive = true
        }
    }

    fun navigateToMain() = navController.navigate("main") {
        popUpTo("landing") {
            inclusive = true
        }
    }

    fun opeenAddFoodModal() {
        // Todo: AddFoodModal을 열기 위한 로직 구현
    }
}