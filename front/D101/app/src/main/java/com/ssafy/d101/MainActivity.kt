package com.ssafy.d101

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.navigation.compose.rememberNavController
import com.ssafy.d101.model.BottomNavItem
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
    val navController = rememberNavController()
    val isLoggedIn = checkUserLoggedIn()

    val startDestination = if (isLoggedIn) "home" else "landing"

    NavHost(navController = navController, startDestination = startDestination) {
        composable("myPage") { MyPageScreen(navController) }
        composable("home") { HomeScreen(navController) }
    }
}

// 사용자 로그인 상태를 확인하는 함수 (예시 구현)
fun checkUserLoggedIn(): Boolean {
    // Todo: 사용자 로그인 상태 확인 로직 구현
    return true
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    D101Theme {
        Greeting("Android")
    }
}