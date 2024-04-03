package com.ssafy.d101.ui.view.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.ssafy.d101.navigation.Screens
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.theme.White
import com.ssafy.d101.viewmodel.UserViewModel

@Composable

fun MyPageScreen(navController: NavHostController) {
    Column( modifier = Modifier // 백그라운드
        .fillMaxSize()
        .background(Ivory)
    ) {
        MyPageHeader(navController)
        MyProfile()
        MyMenu(navController)
    }

}
@Composable
fun MyPageHeader(navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) { // 헤더 박스
        Spacer(modifier = Modifier.width(48.dp))
        Text(
            text = "마이페이지",
            modifier = Modifier
                .weight(1f)
                .align(Alignment.CenterVertically),
            style = MaterialTheme.typography.titleLarge.copy(fontSize = 20.sp),
            textAlign = TextAlign.Center // 텍스트를 가운데 정렬함
        )
        IconButton(onClick = { navController.navigate("userinfo") }) {
            Icon(
                imageVector = Icons.Default.Person,
                contentDescription = "MyPage Button"
            )
        }
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun MyProfile() {
    val userViewModel: UserViewModel = hiltViewModel()

    val userInfo by userViewModel.getUserInfo().collectAsState(initial = null)

    Row( modifier = Modifier
        .fillMaxWidth()
        .padding(30.dp, 0.dp, 30.dp, 30.dp)
        .shadow(15.dp, RoundedCornerShape(12.dp))
        .background(White, shape = RoundedCornerShape(12.dp))
    ) { // 프로필 칸 @!
        Column () {
            Row ( modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically) {// 첫번째 칸
                Box (modifier = Modifier
                    .weight(1f)
                    .padding(20.dp, 0.dp, 0.dp, 0.dp),
                    contentAlignment = Alignment.CenterStart
                ){ // 닉네임 @!
                    Text(
                        text = userInfo?.username ?: "사용자 이름",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
                Box (
                    modifier = Modifier.padding(0.dp,0.dp,20.dp,0.dp),
                    contentAlignment = Alignment.CenterEnd
                ){ // 프로필 이미지 @!
                    AsyncImage(
                        model = userInfo?.image,
                        contentDescription = "profileImage",
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.size(100.dp),
                    )
                }
            }
            Row ( modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically) { // 주간 기록 현황
                Column {
                    Text(text = "주간 기록 현황")
                }
                Column {
                    Slider(
                        value = 0.5f,
                        onValueChange = { /* 슬라이더 값 변화에 대한 핸들러 */ },
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFFFFA500),
                            activeTrackColor = Color(0xFFFFA500)
                        )
                    )
                }
            }
            Row ( modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp, 0.dp),
                verticalAlignment = Alignment.CenterVertically) { // 주간 기록 현황
                Column {
                    Text(text = "월간 기록 현황")
                }
                Column {
                    Slider(
                        value = 0.3f,
                        onValueChange = { /* 슬라이더 값 변화에 대한 핸들러 */ },
                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF64AEF2),
                            activeTrackColor = Color(0xFF64AEF2)
                        )
                    )
                }
            }
        }

    }
    
}

@Composable
fun MyMenu(navController: NavController) {
    // 메뉴 박스
    Row( modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 30.dp, vertical = 5.dp)
        .shadow(15.dp, RoundedCornerShape(12.dp))
        .background(White, shape = RoundedCornerShape(12.dp))
    ) {
        Column() {
            Text(text = "정보 등록",
                modifier = Modifier.padding(20.dp,10.dp,0.dp,10.dp),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    shadow = Shadow(
                        color = Color.Gray,
                        offset = Offset(10f,10f),
                        blurRadius = 8f
                    )
                )
            )
            SettingItem("BMI 측정하기", navController, "bmi")
            SettingItem("알레르기 등록", navController, "allergy")
            Divider(modifier = Modifier.padding(20.dp,10.dp,20.dp,0.dp), color = Color.Gray, thickness = 1.dp)
            Text("식단 관리", modifier = Modifier.padding(20.dp,10.dp,0.dp,10.dp),
                style = TextStyle(
                    color = Color.Black,
                    fontSize = 15.sp,
                    shadow = Shadow(
                        color = Color.Gray,
                        offset = Offset(10f,10f),
                        blurRadius = 8f
                    )
                )
            )
            SettingItem("음식 추천", navController, "recommend")
            SettingItem("식단 분석", navController, Screens.AnalysisDiet.route)
        }
    }

}

@Composable
fun SettingItem(text: String, navController: NavController, path: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 8.dp, 0.dp, 5.dp)
            .clickable { navController.navigate(path) },
        verticalAlignment = Alignment.CenterVertically
    ) { // > 아이콘
        Text(text)
        Spacer(modifier = Modifier.weight(1f))
        Icon(Icons.Default.KeyboardArrowRight, contentDescription = null, Modifier.padding(20.dp,0.dp))
    }
}
