package com.ssafy.d101

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.ssafy.d101.model.BottomNavItem
import com.ssafy.d101.navigation.AppScaffold
import com.ssafy.d101.navigation.Screens
import com.ssafy.d101.navigation.SetUpNavGraph
import com.ssafy.d101.ui.theme.D101Theme
import com.ssafy.d101.ui.view.components.BottomNavigationBar
import com.ssafy.d101.ui.view.screens.BMIScreen
import com.ssafy.d101.ui.view.screens.HomeScreen
import com.ssafy.d101.ui.view.screens.LandingScreen
import com.ssafy.d101.ui.view.screens.MyPageScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp()
        }
    }
}

@Composable
fun MyApp() {
    D101Theme {
        val navController = rememberNavController()
        // 현재 라우트 상태를 기반으로 하단 네비게이션 바의 표시 여부를 결정
        Scaffold(
            bottomBar = {
                // 현재 네비게이션 스택에서 가장 상위에 있는 라우트 정보
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                // Landing 페이지가 아닐 때 하단 네비게이션 바 표시
                if (currentRoute != null && currentRoute != Screens.Landing.route) {
                    BottomNavigationBar(navController = navController)
                }
            }
        ) { innerPadding ->
            SetUpNavGraph(navController = navController)
        }
    }
//    val navController = rememberNavController()
//    SetUpNavGraph(navController = navController)
//    val isLoggedIn = checkUserLoggedIn()
//
//    val startDestination = if (isLoggedIn) "home" else "landing"

    //AppScaffold()

//    NavHost(navController = navController, startDestination = startDestination) {
//        composable("myPage") { MyPageScreen(navController) }
//        composable("home") { HomeScreen(navController) }
//    }
}

// 사용자 로그인 상태를 확인하는 함수 (예시 구현)
fun checkUserLoggedIn(): Boolean {
    // Todo: 사용자 로그인 상태 확인 로직 구현
    return true
}