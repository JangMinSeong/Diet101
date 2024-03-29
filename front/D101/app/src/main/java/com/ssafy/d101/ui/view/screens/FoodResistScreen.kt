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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.text.font.FontFamily
import androidx.navigation.NavHostController
import java.time.LocalDate

@Preview(showBackground = true)
@Composable
fun FoodResistScreen(navController: NavHostController) {
    val currentDate = remember { LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")) }
    // 선택된 항목을 추적하는 상태 변수
    var selectedMeal by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }    // 섭취한 음식 사진 첨부 상태 관리
    var showDialog2 by remember { mutableStateOf(false) }    // 성분표 첨부 상태 관리

    // 사용자가 항목을 선택하거나 선택을 취소하는 로직
    val onMealSelected: (String) -> Unit = { meal ->
        selectedMeal = if (selectedMeal == meal) null else meal
    }

    // 선택된 항목이 있는지 확인하는 함수
    val isItemSelected: (String) -> Boolean = { it == selectedMeal }

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
                .padding(top = 16.dp, bottom = 45.dp),
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
                    .clickable { navController.popBackStack() }
            )

            // 카테고리
            Text(
                text = "음식 등록",
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 10.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier
                .weight(1f)
                .padding(bottom = 10.dp))
        }

        // 스크롤 가능 영역
        LazyColumn(
            modifier = Modifier
                .padding(16.dp)
        ) {
            item {
                // 오늘은 어떤 음식을 드셨나요?
                Text(
                    text = "오늘은 어떤 음식을 드셨나요?",
                    modifier = Modifier
                        .weight(2f)
                        .padding(start = 10.dp),
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF004D40),
                )
            }

            item {
                // 사진 등록 배경
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .background(Color(0x3700897B), RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(16.dp)
                    ) {
                        // 날짜
                        Text(
                            text = currentDate,
                            color = Color.Black,
                            fontSize = 21.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 20.dp)
                        )

                        Row(
                            modifier = Modifier.padding(16.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                // 섭취한 음식 사진 타이틀
                                Text(
                                    text = "섭취한 음식 사진",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                )

                                // 섭취한 음식 사진 첨부 공간
                                Image(
                                    painter = painterResource(id = R.drawable.file),
                                    contentDescription = "섭취한 음식 사진",
                                    modifier = Modifier
                                        .size(150.dp)
                                        .padding(top = 8.dp)
                                        .clickable(onClick = { showDialog = true })
                                )
                            }
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {

                                // 영양정보표/원재료 등록 타이틀
                                Text(
                                    text = "영양정보표/원재료 등록",
                                    fontSize = 13.sp,
                                    fontWeight = FontWeight.Bold,
                                )

                                // 영양정보표/원재료 등록 첨부 공간
                                Image(
                                    painter = painterResource(id = R.drawable.file),
                                    contentDescription = "영양정보표/원재료 등록",
                                    modifier = Modifier
                                        .size(150.dp)
                                        .padding(top = 8.dp)
                                        .clickable(onClick = { showDialog2 = true })
                                )
                            }
                        }
                        Text(
                            "분류",
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .align(Alignment.Start)
                                .padding(start = 35.dp, top = 10.dp)
                        )
                        listOf("아침", "아점", "점심", "점저", "저녁", "야식", "간식", "음료", "주류").chunked(3)
                            .forEach { chunk ->
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 4.dp),
                                    horizontalArrangement = Arrangement.SpaceEvenly
                                ) {
                                    chunk.forEach { meal ->
                                        OutlinedButton(
                                            onClick = { onMealSelected(meal) },
                                            border = BorderStroke(
                                                if (isItemSelected(meal)) 4.dp else 1.dp,
                                                Color.Black
                                            ),
                                            modifier = Modifier
                                                .width(90.dp)
                                        ) {
                                            Text(
                                                meal,
                                                fontWeight = FontWeight.Bold,
                                                color = if (isItemSelected(meal)) Color.Black else Color.Black
                                            )
                                        }
                                    }
                                }
                            }

                        // "등록하기" 버튼 추가
                        Button(
                            onClick = { /* TODO: 버튼 클릭 이벤트 처리 */ },
                            colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                            modifier = Modifier
                                .width(130.dp)
                                .height(60.dp)
                                .padding(top = 20.dp),
                        ) {
                            Text(
                                text = "등록하기",
                                color = Color.White,
                                fontSize = 16.sp,
                            )
                        }
                    }
                }
            }
        }

        // 섭취한 음식 사진 첨부 AlertDialog 표시 로직
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        "첨부하기",
                        modifier = Modifier
                            .padding(top = 15.dp, start = 10.dp),
                        fontWeight = FontWeight.Bold,
                    )
                },
                text = {
                    Column {
                        Divider(thickness = 3.dp)
                        TextItem("카메라", R.drawable.camera) {
                            // 카메라 아이템 클릭 시 수행할 로직
                        }
                        TextItem("갤러리", R.drawable.gallery) {
                            // 갤러리 아이템 클릭 시 수행할 로직
                        }
                        TextItem("내가 추가한 음식", R.drawable.minefood) {
                            navController.navigate("foodAddition")
                        }
                    }
                },
                confirmButton = {

                },
            )
        }

        // 성분표 첨부 AlertDialog 표시 로직
        if (showDialog2) {
            AlertDialog(
                onDismissRequest = { showDialog2 = false },
                title = {
                    Text(
                        "첨부하기",
                        modifier = Modifier
                            .padding(top = 15.dp, start = 10.dp),
                        fontWeight = FontWeight.Bold,
                    )
                },
                text = {
                    Column {
                        Divider(thickness = 3.dp)
                        TextItem("카메라", R.drawable.camera) {
                            // 카메라 아이템 클릭 시 수행할 로직
                        }
                        TextItem("갤러리", R.drawable.gallery) {
                            // 갤러리 아이템 클릭 시 수행할 로직
                        }
                        TextItem("내가 추가한 음식", R.drawable.minefood) {
                            // 내가 추가한 음식 클릭 시 수행할 로직
                        }
                    }
                },
                confirmButton = {

                },
            )
        }
    }
}

@Composable
fun TextItem(text: String, iconRes: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "Icon for $text",
            modifier = Modifier
                .size(50.dp)
                .padding(start = 16.dp, top = 15.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 8.dp, top = 15.dp)
        )
    }
}
