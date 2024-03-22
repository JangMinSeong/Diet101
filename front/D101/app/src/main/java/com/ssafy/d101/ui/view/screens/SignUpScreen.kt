package com.ssafy.d101.ui.view.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.ssafy.d101.R
import com.ssafy.d101.viewmodel.KakaoAuthViewModel


@Composable
fun SignUpScreen(navController: NavHostController, kakaoAuthViewModel: KakaoAuthViewModel) {

    Column (
        modifier = Modifier
            .fillMaxSize()
            .fillMaxSize()
            .background(Color(0xFFFFFCE8)),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // Signature 사진
        Image(
            painter = painterResource(id = R.drawable.signature),
            contentDescription = "Signature Image",
            modifier = Modifier
                .padding(top = 160.dp, bottom = 110.dp)
                .width(250.dp)
                .height(250.dp)
        )

//        // Spacer : 첫 번째 이미지와 두 번째 컨텐츠 사이의 공간 확보
//        Spacer(modifier = Modifier.weight(1f))

        // 카카오로 시작하기 버튼
        Image(
            painter = painterResource(id = R.drawable.kakaologin),
            contentDescription = "Kakao Login Button",
            modifier = Modifier
                .padding(bottom = 30.dp)
                .width(300.dp)
                .height(50.dp)
                .clickable(onClick = { kakaoAuthViewModel.handleKakaoLogin() })
        )

        // 가입 관련 멘트
        Text(
            text = "가입하면 더 많은 기능을 사용할 수 있어요",
            modifier = Modifier.padding(top = 45.dp, bottom = 10.dp),
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
        )

        // 실선
        Box(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .height(1.dp)
                .background(Color.Gray)
                .padding(vertical = 45.dp)
        )

        // 이용약관 동의 관련 멘트
        Text(
            text = "회원가입 시 개인정보 수집 이용 및 이용약관에\n동의하는 것으로 간주합니다.",
            modifier = Modifier.padding(top = 10.dp),
            textAlign = TextAlign.Center,
            fontSize = 12.sp,
        )
    }
}