package com.ssafy.d101.ui.view.screens
import android.app.Dialog
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.material3.*
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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@Preview(showBackground = true)
@Composable
fun FoodSearchScreen() {
    val dummyData = listOf("Apple", "Banana", "Cherry", "Date", "Elderberry")
    var searchText by remember { mutableStateOf("") }
    val filteredItems = dummyData.filter { it.contains(searchText, ignoreCase = true) }
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }  // 모달 카드 보여줄지 여부


    // 화면 전체
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCE8))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        // 화면 상단 - 뒤로 가기 버튼 & 타이틀 화면
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // 뒤로 가기 버튼
            Image(
                painter = painterResource(id = R.drawable.previous),
                contentDescription = "Previous Button",
                modifier = Modifier
                    .size(40.dp)
                    .weight(1f)
                    .padding(end = 16.dp)
            )

            // 카테고리
            Text(
                text = "음식 검색",
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 10.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.weight(1f))
        }


        // 음식 세부사항 BOX
        Box(
            modifier = Modifier
                .clickable { showDialog = true }
                .width(330.dp)
                .height(120.dp)
                .background(Color.White, shape = RoundedCornerShape(10.dp))
                .border(3.dp, Color.Gray, shape = RoundedCornerShape(10.dp)),
        ) {
            // Box 전체 화면 칼럼
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 16.dp),
            ) {
                // 음식명, 추가 버튼 칼럼
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 2.dp, bottom = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    // 음식명
                    Text(
                        text = "마가렛트",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .weight(8f)
                            .padding(end = 8.dp),
                    )

                    Spacer(modifier = Modifier.weight(1f)) // Spacer 추가하여 버튼을 오른쪽으로 밀어냅니다.

                    // 추가 버튼 - 오른쪽 정렬
                    Image(
                        painter = painterResource(id = R.drawable.plusbutton),
                        contentDescription = "Add Button",
                        modifier = Modifier.size(40.dp)
                    )
                }

                // 회사명, 1인분 당 그램 수, 칼로리
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 5.dp, end = 10.dp, top = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    // 회사명
                    Text(
                        text = "오리온",
                        fontSize = 17.sp
                    )

                    // 1인분 당 그램 수
                    Text(
                        text = "1인분(48g)",
                        color = Color.Gray,
                        fontSize = 14.sp
                    )

                    // 칼로리
                    Text(
                        text = "192kcal",
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                }
            }
        }

        if (showDialog) {
            var text by remember { mutableStateOf("") } // 사용자 입력 관리 상태 변수
            // AlertDialog 표시
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        text = "마가렛트",
                        fontWeight = FontWeight.Bold,
                    )
                },
                text = {
                    Column {
                        Text(text = "제조사 : 오리온\n식품종류 : 가공식품\n식품대분류 : 과자\n식품상세분류 : 쿠키")
                        Text(
                            text = "1회 제공량 48(g, ml)",
                            modifier = Modifier.padding(top = 10.dp),
                            color = Color.Gray,
                        )
                        Text(
                            text = "먹은 양",
                            modifier = Modifier.padding(top = 10.dp),
                            color = Color.Gray,
                        )

                        // 먹은 양 입력 상자
                        TextField(
                            value = text,
                            onValueChange = { text = it },
                            modifier = Modifier
                                .width(100.dp)
                                .height(65.dp)
                                .padding(bottom = 15.dp),
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
                                    "192kcal",
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
                    }
                },

                // 취소 버튼
                dismissButton = {
                    Button(
                        onClick = { showDialog = false }
                    ) {
                        Text("취소")
                    }
                },

                // 추가 버튼
                confirmButton = {
                    Button(
                        onClick = { /* 추가 버튼 클릭 시 로직 */ }
                    ) {
                        Text("추가")
                    }
                },
            )
        }
    }
}

@Composable
// 버튼
fun FilledButtonExample(onClick: () -> Unit) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { onClick() },
            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
            modifier = Modifier
                .padding(top = 6.dp)
        ) {
            Text("추가하기", color = Color.White)
        }
    }
}


