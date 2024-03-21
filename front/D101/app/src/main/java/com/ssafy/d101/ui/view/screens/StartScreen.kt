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
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssafy.d101.R
import com.ssafy.d101.navigation.Screens
import com.ssafy.d101.ui.theme.D101Theme

@Composable
fun StartScreen(navController: NavHostController) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .fillMaxSize()
            .background(Color(0xFFFFFCE8)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.appname),
            contentDescription = "Centered Image",
            modifier = Modifier
                .width(300.dp)
                .height(300.dp)
        )
        Text(
            text = "모두가 선택한 1위 식단관리 앱 D101",
            modifier = Modifier.padding(top = 45.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
        )
        Text(
            text = "AI 기술 도입\n식단관리 서비스",
            modifier = Modifier.padding(top = 25.dp),
            textAlign = TextAlign.Center,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )
        Image(
            painter = painterResource(id = R.drawable.startbutton),
            contentDescription = "Start Button",
            modifier = Modifier
                .padding(top = 45.dp)
                .width(300.dp)
                .height(50.dp)
                .clickable(onClick = { navController.navigate(Screens.SignUp.route) })
        )
    }
}

@Preview(showSystemUi = true)
@Composable
fun StartScreenPreview() {
    val navController = rememberNavController()
    StartScreen(navController = navController)
}