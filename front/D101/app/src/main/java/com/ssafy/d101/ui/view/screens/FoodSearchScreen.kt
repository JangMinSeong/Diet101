package com.ssafy.d101.ui.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.MaterialTheme
import androidx.navigation.NavController
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.viewModelScope
import androidx.compose.runtime.collectAsState
import android.util.Log
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.d101.model.FoodAddInfo
import com.ssafy.d101.viewmodel.FoodSearchViewModel
import kotlinx.coroutines.launch


@Preview(showBackground = true)
@Composable
fun FoodSearchScreen(
    navController: NavController,
    foodName: String
) {
    var showDialog by remember { mutableStateOf(false) }  // 모달 카드 보여줄지 여부
    var selectedFoodItemName by remember { mutableStateOf<String?>(null) }
    val viewModel: FoodSearchViewModel = hiltViewModel()
    val foodItems by viewModel.foodItems.collectAsState()    // viewModel 사용하여 음식 목록 불러오기

    // 초기 데이터 로드
    LaunchedEffect(foodName) {
        viewModel.fetchFoodItems(foodName)
        viewModel.viewModelScope.launch {
            viewModel.foodItems.collect { foodItems ->
                Log.d("FoodSearchScreen", "Food items: $foodItems")
            }
        }
    }

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

        // 다시 검색하러가기
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 20.dp)
                .clickable {
                    navController.navigate("foodAddition")
                },
            verticalAlignment = Alignment.CenterVertically,
        ) {
            // 돋보기 이미지
            Image(
                painter = painterResource(id = R.drawable.searchimage),
                contentDescription = "Search Image",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 8.dp)
            )

            // 다시 검색하러가기
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(textDecoration = TextDecoration.Underline)) {
                        append("다시 검색하러가기")
                    }
                },
                modifier = Modifier.padding(start = 8.dp),
                textAlign = TextAlign.Left,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(foodItems) {foodItem ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .padding(vertical = 2.dp)
                        .background(Color.White, shape = RoundedCornerShape(10.dp))
                        .border(3.dp, Color.Gray, shape = RoundedCornerShape(10.dp))
                        .clickable {
                            selectedFoodItemName = foodItem.name
                            showDialog = true
                        },
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
                                text = foodItem.name,
                                fontSize = 20.sp,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier
                                    .weight(8f)
                                    .padding(end = 8.dp),
                            )

                            Spacer(modifier = Modifier.weight(1f)) // Spacer 추가하여 버튼을 오른쪽으로 밀어냅니다.
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
                                text = foodItem.manufacturer,
                                fontSize = 17.sp
                            )

                            // 1인분 당 그램 수
                            Text(
                                text = "${foodItem.portionSize}g",
                                color = Color.Gray,
                                fontSize = 14.sp
                            )

                            // 칼로리
                            Text(
                                text = "${foodItem.calorie}kcal",
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
    selectedFoodItemName?.let { selectedName ->
        val selectedItem = foodItems.firstOrNull { it.name == selectedName }

        selectedItem?.let { item ->
            var eatenAmountText by remember { mutableStateOf("1.0") }
            val eatenAmount = eatenAmountText.toDoubleOrNull() ?: 1.0

            // 영양소(탄단지) 조절
            val adjustedCarbohydrate = item.carbohydrate * eatenAmount
            val adjustedProtein = item.protein * eatenAmount
            val adjustedFat = item.fat * eatenAmount

            // 총 칼로리 계산
            val totalCalories = (adjustedCarbohydrate * 4 + adjustedProtein * 4 + adjustedFat * 9).toInt()

            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        // 음식명
                        text = item.name,
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        modifier = Modifier
                            .padding(top = 10.dp)
                    )
                },

                text = {
                    Column {
                        Text(
                            text = "제조사 : ${item.manufacturer}"
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "식품종류 : ${item.majorCategory}"
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "식품대분류 : ${item.minorCategory}"
                        )
                        Spacer(modifier = Modifier.height(3.dp))
                        Text(
                            text = "식품상세분류 : ${item.dbGroup}"
                        )

                        Text(
                            text = "1회 제공량 ${item.portionSize}${item.unit}",
                            modifier = Modifier.padding(top = 10.dp),
                            color = Color.Gray,
                        )
                        Text(
                            text = "먹은 양",
                            modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
                            color = Color.Gray,
                        )

                        // 먹은 양 입력 상자
                        TextField(
                            value = eatenAmountText,
                            onValueChange = { newValue ->
                                eatenAmountText= newValue
                            },
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
                                    "${totalCalories}kcal",
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
                                    NutritionInfoFieldReadOnly("탄수화물", "${String.format("%.2f", adjustedCarbohydrate)}g")
                                    NutritionInfoFieldReadOnly("단백질", "${String.format("%.2f", adjustedProtein)}g")
                                    NutritionInfoFieldReadOnly("지방", "${String.format("%.2f", adjustedFat)}g")
                                }
                            }
                        }
                    }
                },

                confirmButton = {
                    Button(
                        onClick = {
                            val foodAddInfo = FoodAddInfo(
                                id = item.id,
                                name = item.name,
                                manufacturer = item.manufacturer,
                                majorCategory = item.majorCategory,
                                minorCategory = item.minorCategory,
                                dbGroup = item.dbGroup,
                                portionSize = item.portionSize,
                                totalSize = item.totalSize,
                                unit = item.unit,
                                eatenAmount = eatenAmount.toDouble(),
                                calorie = totalCalories,
                                carbohydrate = adjustedCarbohydrate,
                                protein = adjustedProtein,
                                fat = adjustedFat
                            )
                            viewModel.addUserAddedFoodItem(foodAddInfo)
                            showDialog = false
                            selectedFoodItemName = null
                        },
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

