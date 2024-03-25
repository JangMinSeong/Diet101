package com.ssafy.d101.ui.view.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Color
import com.ssafy.d101.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text

@Preview(showBackground = true)
@Composable
fun AnalysisResultScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCE8)),
    ) {
        Image(
            painter = painterResource(id = R.drawable.previous),
            contentDescription = "Previous Button",
            modifier = Modifier
                .size(60.dp)
                .padding(start = 16.dp, top = 16.dp)
                .align(Alignment.TopStart)
        )

        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "월남쌈",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
            )
            Spacer(modifier = Modifier.height(40.dp))

            // 분석 결과 이미지
            Image(
                painter = painterResource(id = R.drawable.fakefoodimage),
                contentDescription = "잘못 인식된 월남쌈 사진",
                modifier = Modifier
                    .size(220.dp)
                    .padding(bottom = 70.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { /* TODO: 버튼 클릭 이벤트 처리 */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .width(160.dp)
                    .height(40.dp)
            ) {
                Text(
                    text = "음식 직접 등록",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}

