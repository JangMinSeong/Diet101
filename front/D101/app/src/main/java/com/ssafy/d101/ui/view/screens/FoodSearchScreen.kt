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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Preview(showBackground = true)
@Composable
fun FoodSearchScreen() {
    val dummyData = listOf("Apple", "Applemango", "Banana", "Cherry", "Date", "Elderberry")
    var searchText by remember { mutableStateOf("") }
    val filteredItems = if (searchText.isEmpty()) {
        dummyData // 검색 텍스트가 비어있다면 모든 데이터를 표시
    } else {
        dummyData.filter { it.startsWith(searchText, ignoreCase = true) }
    }
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }  // 모달 카드 보여줄지 여부
    var selectedFoodItemName by remember { mutableStateOf<String?>(null) }

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

        // 음식명 검색
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            // 돋보기 이미지
            Image(
                painter = painterResource(id = R.drawable.searchimage),
                contentDescription = "Search Image",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 10.dp)
            )

            // 검색창, 검색 로직
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    expanded = it.isNotEmpty()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                placeholder = { Text("음식명 입력", fontSize = 14.sp) },
                singleLine = true,
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(filteredItems) {foodItem ->
                Box(
                    modifier = Modifier
                        .clickable { selectedFoodItemName = foodItem }
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(vertical = 2.dp)
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                        .border(3.dp, Color.Gray, shape = RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
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
                                text = foodItem,
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
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
    selectedFoodItemName?.let { foodName ->
        var text by remember { mutableStateOf("") }
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    // 음식명
                    text = foodName,
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
                            .fillMaxWidth()
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
                                modifier = Modifier.align(Alignment.Start).padding(start = 35.dp)
                            )
                            Spacer(modifier = Modifier.height(5.dp))  // 텍스트와 선 사이 공간

                            // 실선
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth(0.95f)
                                    .height(3.dp)
                                    .background(Color.Gray)
                            )
                            Spacer(modifier = Modifier.height(20.dp))
                            // 영양소 정보 입력 필드
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                NutritionInfoFieldReadOnly("탄수화물", "100g")
                                NutritionInfoFieldReadOnly("단백질", "20g")
                                NutritionInfoFieldReadOnly("지방", "10g")
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = { },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .width(80.dp)
                        .height(60.dp)
                        .padding(top = 20.dp),
                ) {
                    Text(
                        text = "추가",
                        color = Color.White,
                        fontSize = 16.sp,
                    )
                }
            },
            dismissButton = {
                Button(
                    onClick = { selectedFoodItemName = null },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .width(80.dp)
                        .height(60.dp)
                        .padding(top = 20.dp),
                ) {
                    Text(
                        text = "취소",
                        color = Color.White,
                        fontSize = 16.sp,
                    )
                }
            }
        )
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

@Composable
fun NutritionInfoFieldReadOnly(labelText: String, text: String) {
    Column(modifier = Modifier.padding(start = 15.dp, end = 15.dp)) {
        Text(
            text = labelText,
            style = MaterialTheme.typography.bodySmall,
            color = Color.Gray
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Bold
        )
    }
}

