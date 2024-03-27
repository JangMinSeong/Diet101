package com.ssafy.d101.ui.view.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.graphics.graphicsLayer

@Preview(showBackground = true)
@Composable
fun AnalysisResultScreen() {
    var imageIndex by remember { mutableStateOf(0) }     // 현재 이미지 인덱스
    // 자른 이미지 리스트 (임시 데이터)
    val imageResources = listOf(R.drawable.fakefoodimage, R.drawable.image2, R.drawable.image3)
    val foodNames = listOf("월남쌈", "숙주무침", "잡채")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCE8)),
        contentAlignment = Alignment.Center
    ) {
        Image(
            // 상단 뒤로 가기 버튼
            painter = painterResource(id = R.drawable.previous),
            contentDescription = "Previous Button",
            modifier = Modifier
                .size(60.dp)
                .padding(start = 25.dp, top = 25.dp)
                .align(Alignment.TopStart)
                .clickable {}
        )

        // 페이지 번호, 음식 이름, 이전 버튼, 다음 버튼, 현재 이미지, 음식 직접 등록 버튼 컬럼
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(32.dp)
        ) {
            // 페이지 번호
            Text(
                text = "${imageIndex + 1}/${imageResources.size}",
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
            )

            Spacer(modifier = Modifier.height(50.dp))

            // 음식 이름
            Text(
                text = foodNames[imageIndex],
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Red,
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                if (imageIndex > 0) {
                    // 이전 버튼
                    Image(
                        painter = painterResource(id = R.drawable.nextbutton),
                        contentDescription = "이전 버튼",
                        modifier = Modifier
                            .size(40.dp)
                            .graphicsLayer { scaleX = -1f }    // 수평 반전
                            .clickable { if (imageIndex > 0) imageIndex-- }
                    )
                } else {
                    Spacer(modifier = Modifier.size(40.dp))
                }

                // 분석 결과 이미지
                Image(
                    painter = painterResource(id = imageResources[imageIndex]),
                    contentDescription = "잘못 인식된 월남쌈 사진",
                    modifier = Modifier.size(200.dp)
                )

                if (imageIndex < imageResources.size - 1) {
                    // 다음 버튼
                    Image(
                        painter = painterResource(id = R.drawable.nextbutton),
                        contentDescription = "다음 버튼",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable { if (imageIndex < imageResources.size - 1) imageIndex++ }
                    )
                } else {
                    Spacer(modifier = Modifier.size(40.dp))
                }
            }

            Spacer(modifier = Modifier.height(100.dp))

            // 음식 직접 등록 버튼
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
