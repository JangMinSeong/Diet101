package com.ssafy.d101.ui.view.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.graphics.Color
import com.ssafy.d101.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.ui.draw.drawBehind
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssafy.d101.navigation.Screens

@Composable
fun SignUpCompleteScreen(navController: NavHostController) {
    Column (
        modifier = Modifier.fillMaxSize()
            .fillMaxSize()
            .background(Color(0xFFFFFCE8)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // 상단 바
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(70.dp)
                .padding(bottom = 16.dp)
                .background(Color.DarkGray),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "회원가입",
                color = Color.White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        // 회원가입 완료 멘트
        Text(
            text = "반갑습니다.\n회원이 되신 것을 환영합니다.",
            modifier = Modifier.padding(top = 250.dp, bottom = 250.dp),
            textAlign = TextAlign.Center,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
        )

        // 식단관리 시작하기 버튼
        Image(
            painter = painterResource(id = R.drawable.managebutton),
            contentDescription = "Manage Button",
            modifier = Modifier
                .padding(bottom = 30.dp)
                .width(300.dp)
                .height(50.dp)
                .clickable {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = true
                        }
                        launchSingleTop = true
                    }
                }
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun SignUpCompleteScreenPreview() {
    val navController = rememberNavController()
    SignUpCompleteScreen(navController = navController)
}