package com.ssafy.d101.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.ssafy.d101.ui.view.screens.AiAnalysisScreen
import com.ssafy.d101.ui.view.screens.AllergyScreen
import com.ssafy.d101.ui.view.screens.AnalysisResultScreen
import com.ssafy.d101.ui.view.screens.BMIScreen
import com.ssafy.d101.ui.view.screens.DietAnalysis
import com.ssafy.d101.ui.view.screens.FoodAdditionScreen
import com.ssafy.d101.ui.view.screens.FoodResistScreen
import com.ssafy.d101.ui.view.screens.FoodSearchScreen
import com.ssafy.d101.ui.view.screens.HomeScreen
import com.ssafy.d101.ui.view.screens.LandingScreen
import com.ssafy.d101.ui.view.screens.LoadingScreen
import com.ssafy.d101.ui.view.screens.LoginSuccessScreen
import com.ssafy.d101.ui.view.screens.MyPageScreen
import com.ssafy.d101.ui.view.screens.RecommendScreen
import com.ssafy.d101.ui.view.screens.SignUpActivityLevelScreen
import com.ssafy.d101.ui.view.screens.SignUpCompleteScreen
import com.ssafy.d101.ui.view.screens.SignUpScreen
import com.ssafy.d101.ui.view.screens.StartScreen
import com.ssafy.d101.ui.view.screens.UserInfoScreen
import com.ssafy.d101.ui.view.screens.SignUpHeightScreen
import com.ssafy.d101.ui.view.screens.SignUpWeightScreen
import com.ssafy.d101.viewmodel.KakaoAuthViewModel
import com.ssafy.d101.viewmodel.UserViewModel

@Composable
fun SetUpNavGraph(
    navController: NavHostController,
    kakaoAuthViewModel: KakaoAuthViewModel,
    userViewModel: UserViewModel
) {
    var isLoginChecked by remember { mutableStateOf(false) }
    var isLoggedIn by remember { mutableStateOf(false) }

//    LaunchedEffect(key1 = true) {
//        kakaoAuthViewModel.checkLogin()
//        kakaoAuthViewModel.isLoggedIn.collect { loginStatus ->
//            isLoggedIn = loginStatus
//            isLoginChecked = true
//        }
//    }
    val startDestination = if (isLoggedIn) Screens.Home.route else Screens.Start.route
    NavHost(
        navController = navController,
        startDestination = Screens.Loading.route
    ) {
        composable(Screens.Landing.route) { LandingScreen(navController) }
        composable(Screens.Home.route) { HomeScreen(navController) }
        composable(Screens.MyPage.route) { MyPageScreen(navController) }
        composable(Screens.BMI.route) { BMIScreen(navController) }
        composable(Screens.Allergy.route) { AllergyScreen(navController) }
        composable(Screens.Start.route) { StartScreen(navController) }
        composable(Screens.SignUp.route) { SignUpScreen(navController) }
        composable(Screens.SignUpComplete.route) { SignUpCompleteScreen(navController) }
        composable(Screens.LoginSuccess.route) { LoginSuccessScreen(navController) }
        composable(Screens.Recommend.route) { RecommendScreen(navController) }
        composable(Screens.UserInfo.route) { UserInfoScreen(navController) }
        composable(Screens.FoodResist.route) { FoodResistScreen(navController) }
        composable(Screens.FoodAddition.route) { FoodAdditionScreen(navController) }
        composable(Screens.Height.route) { SignUpHeightScreen(navController) }
        composable(Screens.Weight.route) { SignUpWeightScreen(navController) }
        composable(Screens.ActivityLevel.route) { SignUpActivityLevelScreen(navController) }
        composable(Screens.Loading.route) { LoadingScreen(navController) }
        composable(
            Screens.FoodSearch.route,
            arguments = listOf(navArgument("foodName") { type = NavType.StringType })
        ) { backStackEntry ->
            val foodName = backStackEntry.arguments?.getString("foodName") ?: ""
            FoodSearchScreen(navController, foodName)
        }
        composable(Screens.Landing.route) { LandingScreen(navController) }
        composable(Screens.Home.route) { HomeScreen(navController) }
        composable(Screens.MyPage.route) { MyPageScreen(navController) }
        composable(Screens.BMI.route) { BMIScreen(navController) }
        composable(Screens.Allergy.route) { AllergyScreen(navController) }
        composable(Screens.Start.route) { StartScreen(navController) }
        composable(Screens.SignUp.route) { SignUpScreen(navController) }
        composable(Screens.SignUpComplete.route) { SignUpCompleteScreen(navController) }
        composable(Screens.LoginSuccess.route) { LoginSuccessScreen(navController) }
        composable(Screens.UserInfo.route) { UserInfoScreen(navController) }
        composable(Screens.AnalysisDiet.route) { DietAnalysis(navController) }
        composable(Screens.Recommend.route) { RecommendScreen(navController) }
        composable(Screens.UserInfo.route) { UserInfoScreen(navController) }
        composable(Screens.AiLoading.route) { AiAnalysisScreen(navController) }
        composable(Screens.AnalysisResult.route) { AnalysisResultScreen(navController) }

    }

}

//    kakaoAuthViewModel.checkLogin()
//    val isLoggedIn = kakaoAuthViewModel.isLoggedIn.collectAsState()



