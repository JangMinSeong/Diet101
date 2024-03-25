// navigation/AppScaffold.kt
package com.ssafy.d101.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ssafy.d101.model.NavigationActions
import com.ssafy.d101.ui.view.components.BottomNavigationBar
import com.ssafy.d101.ui.view.screens.HomeScreen
import com.ssafy.d101.ui.view.screens.LandingScreen

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AppScaffold() {
    val navController = rememberNavController()
    val actions = NavigationActions(navController)

    // LaunchedEffect를 사용하여 초기화 작업을 수행
    LaunchedEffect(key1 = "init") {
        navController.navigate("landing") {
            // 초기화시 필요한 로직이 있으면 여기에 작성합니다.
        }
    }

    Scaffold(
        bottomBar = {
            println("currentDestination: ${navController.currentDestination?.route}")
            if (shouldShowBottomBar(navController.currentDestination?.route)) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = "landing",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("landing") { LandingScreen(navController) }
            composable("home") { HomeScreen(navController) }
//            composable("myPage") { MyPageScreen(navController) }
            // Add other composable screens and navigation logic here.
        }
    }
}

private fun shouldShowBottomBar(route: String?): Boolean {
    // 여기서 route에 따라 BottomNavigationBar를 표시할 지 결정합니다.
    // "landing" 화면에서는 BottomNavigationBar를 숨겨야 합니다.
    return route != "landing"
}
