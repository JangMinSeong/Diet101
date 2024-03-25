package com.ssafy.d101.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.ssafy.d101.ui.view.screens.AllergyScreen
import com.ssafy.d101.ui.view.screens.BMIScreen
import com.ssafy.d101.ui.view.screens.HomeScreen
import com.ssafy.d101.ui.view.screens.LandingScreen
import com.ssafy.d101.ui.view.screens.LoginSuccessScreen
import com.ssafy.d101.ui.view.screens.MyPageScreen
import com.ssafy.d101.ui.view.screens.SignUpCompleteScreen
import com.ssafy.d101.ui.view.screens.SignUpScreen
import com.ssafy.d101.ui.view.screens.StartScreen
import com.ssafy.d101.viewmodel.KakaoAuthViewModel

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    kakaoAuthViewModel: KakaoAuthViewModel
) {
    kakaoAuthViewModel.checkLogin()
    val isLoggedIn = kakaoAuthViewModel.isLoggedIn.collectAsState()
    val startDestination = if (isLoggedIn.value) Screens.Home.route else Screens.Start.route
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.Landing.route) { LandingScreen(navController) }
        composable(Screens.Home.route) { HomeScreen(navController) }
        composable(Screens.MyPage.route) { MyPageScreen(navController, kakaoAuthViewModel) }
        composable(Screens.BMI.route) { BMIScreen(navController) }
        composable(Screens.Allergy.route) { AllergyScreen(navController) }
        composable(Screens.Start.route) { StartScreen(navController) }
        composable(Screens.SignUp.route) { SignUpScreen(navController, kakaoAuthViewModel) }
        composable(Screens.SignUpComplete.route) { SignUpCompleteScreen(navController) }
        composable(Screens.LoginSuccess.route) { LoginSuccessScreen(navController) }
    }
}