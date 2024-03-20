package com.ssafy.d101.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.d101.ui.view.screens.AllergyScreen
import com.ssafy.d101.ui.view.screens.BMIScreen
import com.ssafy.d101.ui.view.screens.HomeScreen
import com.ssafy.d101.ui.view.screens.LandingScreen
import com.ssafy.d101.ui.view.screens.MyPageScreen
import com.ssafy.d101.ui.view.screens.StartScreen

@Composable
fun SetUpNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Start.route
    ) {
        composable(Screens.Landing.route) { LandingScreen(navController) }
        composable(Screens.Home.route) { HomeScreen(navController) }
        composable(Screens.MyPage.route) { MyPageScreen(navController) }
        composable(Screens.BMI.route) { BMIScreen(navController) }
        composable(Screens.Allergy.route) { AllergyScreen(navController) }
        composable(Screens.Start.route) { StartScreen(navController) }
    }
}