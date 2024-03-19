package com.ssafy.d101.ui.view.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.d101.R
import com.ssafy.d101.ui.view.screens.FilledButtonExample

@Preview(showBackground = true)
@Composable
// 모달 카드
fun OutlineCardExample() {
    var text by remember { mutableStateOf("") } // 사용자 입력 관리 상태 변수

    OutlinedCard(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        border = BorderStroke(3.dp, Color.Black),
        modifier = Modifier
            .padding(16.dp)
            .size(width = 310.dp, height = 600.dp)
    ) {
        Box {
            Column(
                modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp),
            ) {
                // 음식명
                Text(
                    text = "피자",
                    textAlign = TextAlign.Center,
                    color = Color.Black,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                )

                // 1회제공량
                Text(
                    text = "1회제공량 50(g, ml)",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .padding(bottom = 10.dp)
                )

                // 먹은양
                Text(
                    text = "먹은양",
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )

                // 먹은 양 입력 상자
                TextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .width(100.dp)
                        .height(50.dp)
                        .border(BorderStroke(2.dp, Color.Gray)),
                    singleLine = true,
                    placeholder = { Text(text = "", fontSize = 10.sp) },
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                )

                // 영양정보 박스
                Box(
                    modifier = Modifier
                        .width(450.dp)
                        .height(160.dp)
                        .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Top
                    ) {
                        Text(
                            "500kcal",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 35.dp)
                        )

                        Spacer(modifier = Modifier.height(5.dp))  // 텍스트와 선 사이 공간

                        // 실선
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.8f)
                                .height(3.dp)
                                .background(Color.Gray)
                                .padding(vertical = 45.dp)
                        )

                        // 탄단지 명칭
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 20.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // 탄수화물
                            Text(
                                text = "탄수화물",
                                color = Color.Gray,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(end = 16.dp)
                            )
                            // 단백질
                            Text(
                                text = "단백질",
                                color = Color.Gray,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(end = 25.dp)
                            )
                            // 지방
                            Text(
                                text = "지방",
                                color = Color.Gray,
                                fontSize = 18.sp,
                            )
                        }

                        // 탄단지 그램 수
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 12.dp),
                            horizontalArrangement = Arrangement.Center,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            // 탄수화물 그램
                            Text(
                                text = "100g",
                                color = Color.Black,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(start = 15.dp, end = 40.dp)
                            )
                            // 단백질 그램
                            Text(
                                text = "100g",
                                color = Color.Black,
                                fontSize = 15.sp,
                                modifier = Modifier.padding(end = 33.dp)
                            )
                            // 지방 그램
                            Text(
                                text = "200g",
                                color = Color.Black,
                                fontSize = 15.sp,
                            )
                        }
                    }
                }

//                // 추가하기 버튼
//                FilledButtonExample(onClick = {
//
//                })
            }
            // 나가기 버튼
            Image(
                painter = painterResource(id = R.drawable.xbutton),
                contentDescription = "exit Button",
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(top = 12.dp, end = 12.dp)
                    .size(25.dp)
            )
        }
    }
}