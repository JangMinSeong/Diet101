package com.ssafy.d101

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.ssafy.d101.navigation.Screens
import com.ssafy.d101.navigation.SetUpNavGraph
import com.ssafy.d101.ui.theme.D101Theme
import com.ssafy.d101.ui.view.components.BottomNavigationBar
import com.ssafy.d101.viewmodel.KakaoAuthViewModel
import com.ssafy.d101.viewmodel.UserViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val kakaoAuthViewModel : KakaoAuthViewModel by viewModels()
    private val userViewModel: UserViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp(kakaoAuthViewModel, userViewModel)
        }
    }
}

@Composable
fun MyApp(kakaoAuthViewModel: KakaoAuthViewModel, userViewModel: UserViewModel) {

    D101Theme {
        val navController = rememberNavController()
        // 현재 라우트 상태를 기반으로 하단 네비게이션 바의 표시 여부를 결정



        Scaffold(
            bottomBar = {
                // 현재 네비게이션 스택에서 가장 상위에 있는 라우트 정보
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                // Landing 페이지가 아닐 때 하단 네비게이션 바 표시
                if (currentRoute != null && showNavBar(currentRoute)) {
                    BottomNavigationBar(navController = navController)
                }
            }
        ) { innerPadding ->
            SetUpNavGraph(navController = navController, kakaoAuthViewModel = kakaoAuthViewModel, userViewModel = userViewModel)
        }
    }
}

fun showNavBar(route: String): Boolean {
    return route != Screens.Start.route &&
            route != Screens.SignUp.route &&
            route != Screens.SignUpComplete.route &&
            route != Screens.Height.route &&
            route != Screens.Weight.route &&
            route != Screens.ActivityLevel.route &&
            route != Screens.Loading.route
}

// 사용자 로그인 상태를 확인하는 함수 (예시 구현)
fun checkUserLoggedIn(): Boolean {
    // Todo: 사용자 로그인 상태 확인 로직 구현
    return true
}