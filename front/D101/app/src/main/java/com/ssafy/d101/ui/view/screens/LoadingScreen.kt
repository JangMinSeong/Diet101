package com.ssafy.d101.ui.view.screens

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ssafy.d101.navigation.Screens
import com.ssafy.d101.viewmodel.KakaoAuthViewModel
import com.ssafy.d101.viewmodel.UserViewModel

@Composable
fun LoadingScreen(navController: NavHostController) {
    val kakaoAuthViewModel: KakaoAuthViewModel = hiltViewModel()

    LaunchedEffect(Unit) {
        kakaoAuthViewModel.checkLogin()
    }
    val userViewModel: UserViewModel = hiltViewModel()

    val isLoggedIn by kakaoAuthViewModel.isLoggedIn.collectAsState(initial = false)
    val loginChecked by kakaoAuthViewModel.loginChecked.collectAsState(initial = false)

    // 로그인 상태가 변경될 때 마다 실행
    LaunchedEffect(loginChecked) {
        if (loginChecked) {
            if (isLoggedIn) {
                Log.i("LoadingScreen", "isLoggedIn: $isLoggedIn")
                // 로그인이 되었다면, 서버에 사용자 SubInfo 존재 여부를 확인
                val result = userViewModel.fetchUserSubInfo()
                Log.i("LoadingScreen", "result: $result")
                if (result.isSuccess) {
                    val userSubInfo = result.getOrNull()
                    if (userSubInfo?.height == 0 || userSubInfo?.weight == 0 || userSubInfo?.calorie == 0) {
                        // SubInfo가 없다면 입력 화면으로 이동
                        navController.navigate(Screens.Height.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }
                    } else {
                        // SubInfo가 있다면 홈 화면으로 이동
                        Log.i("LoadingScreen", "userSubInfo: $userSubInfo")
                        if (userSubInfo != null) {
                            userViewModel.setUserSubInfo(userSubInfo)
                        }
                        navController.navigate(Screens.Home.route) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                        }

                    }
                }
            }
            else {
                // 로그인이 되어있지 않다면, 로그인 화면으로 이동
                navController.navigate(Screens.Start.route) {
                    popUpTo(navController.graph.startDestinationId) {
                        inclusive = true
                    }
                }

            }
        }

    }
    Box(modifier = Modifier.fillMaxSize()) {
        Text(text = "Loading...")
    }
}