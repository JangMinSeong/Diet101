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
import com.ssafy.d101.navigation.Screens

@Preview(showBackground = true)
@Composable
fun LoginSuccessScreen(navController: NavHostController) {
    Column (
        modifier = Modifier.fillMaxSize()
            .fillMaxSize()
            .background(Color(0xFFFFFCE8)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // 로그인 완료 멘트
        Text(
            text = "민숭님,\n다시 만나서 반가워요 \uD83D\uDE00",
            modifier = Modifier.padding(top = 320.dp, bottom = 270.dp),
            textAlign = TextAlign.Center,
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
        )

        // 로그인 완료 버튼
        Image(
            painter = painterResource(id = R.drawable.loginsuccess),
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