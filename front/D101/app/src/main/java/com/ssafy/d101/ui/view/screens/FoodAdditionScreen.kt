package com.ssafy.d101.ui.view.screens
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import com.ssafy.d101.model.FoodAddInfo
import com.ssafy.d101.model.FoodItem
import com.ssafy.d101.viewmodel.FoodSearchViewModel
import kotlinx.coroutines.launch


@Preview(showBackground = true)
@Composable
fun FoodAdditionScreen(navController: NavHostController) {
    var showDialog by remember { mutableStateOf(false) }
    var searchText by remember { mutableStateOf("") }
    var expanded by remember { mutableStateOf(false) }
    val viewModel: FoodSearchViewModel = hiltViewModel()
    val userAddedFoodItems by viewModel.userAddedFoodItems.collectAsState()
    var selectedFoodItem by remember { mutableStateOf<FoodAddInfo?>(null)}
    val selectedFoodPostItem = remember { mutableStateListOf<FoodAddInfo>() }  // 선택한 음식 저장할 리스트

    LaunchedEffect(selectedFoodItem) {

    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCE8))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 뒤로 가기 버튼 & 타이틀
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 60.dp),
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

            // 타이틀 : 음식 추가
            Text(
                text = "음식 추가",
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
                    .weight(1f)
                    .padding(end = 8.dp),
                placeholder = { Text("음식명 입력", fontSize = 14.sp) },
                singleLine = true,
            )

            Button(
                onClick = {
//                    viewModel.fetchFoodItems(searchText)
                    // searchText를 파라미터로 넘기면서 FoodSearchScreen으로 이동하는 로직
                    navController.navigate("foodSearch/${searchText}")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .height(50.dp)
                    .padding(start = 5.dp), // 버튼과 검색창 사이의 간격
            ) {
                Text(
                    text = "✓",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }


        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp, bottom = 10.dp, start = 25.dp, end = 20.dp)
        ) {
            Text(
                text = "내가 추가한 음식",
                modifier = Modifier
                    .weight(1f), // Text를 Row의 나머지 공간을 차지하도록 설정
                textAlign = TextAlign.Start,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            )

            if (userAddedFoodItems.isNotEmpty()) {
                Button(
                    onClick = {
                        viewModel.uploadSelectedItems(selectedFoodPostItem)
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .height(50.dp) // 버튼 높이 설정
                        .padding(start = 5.dp), // 텍스트와 버튼 사이의 간격
                ) {
                    Text(
                        text = "업로드",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }

        // 실선
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(1.dp)
                .background(Color.Gray)
                .padding(vertical = 45.dp)
        )

        if (userAddedFoodItems.isNotEmpty()) {
            // 추가한 음식
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, end = 20.dp, bottom = 100.dp)
                    .background(Color.White),
            ) {
                items(userAddedFoodItems) { foodItem ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clickable {
                                selectedFoodItem  = foodItem
                                showDialog = true
                            }
                    ) {
                        Text(
                            text = foodItem.name,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .weight(1f)
                        )
                        // 선택 유무
                        SwitchWithIconExample(
                            checked = selectedFoodPostItem.contains(foodItem),
                            foodItem = foodItem,
                            onCheckedChange = { isChecked ->
                                if (isChecked) {
                                    selectedFoodPostItem.add(foodItem)
                                } else {
                                    selectedFoodPostItem.remove(foodItem)
                                }
                            }
                        )

//                        // 삭제 버튼
                        CancelButtonExample(onClick = {
                            viewModel.deleteFoodItem(foodItem)
                        })
                    }
                    Divider(color = Color.Gray)
                }
            }

            // AlertDialog 로직
            selectedFoodItem?.let { item ->
                var eatenAmountText by remember { mutableStateOf(item.eatenAmount.toString()) }
                val eatenAmount = eatenAmountText.toDoubleOrNull() ?: item.eatenAmount

                // 원본 아이템 정보를 기준으로 재계산
                val originalCarbohydrate = item.carbohydrate / item.eatenAmount
                val originalProtein = item.protein / item.eatenAmount
                val originalFat = item.fat / item.eatenAmount

                // 영양소(탄단지) 조절
                val adjustedCarbohydrate = originalCarbohydrate * eatenAmount
                val adjustedProtein = originalProtein * eatenAmount
                val adjustedFat = originalFat * eatenAmount

                // 총 칼로리 계산
                val totalCalories = ((adjustedCarbohydrate * 4) + (adjustedProtein * 4) + (adjustedFat * 9)).toInt()

                if (showDialog) {
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
                                        eatenAmountText = newValue
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
                                                .fillMaxWidth(0.8f)
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
                                            NutritionInfoFieldRead("탄수화물", "${String.format("%.2f", adjustedCarbohydrate)}g")
                                            NutritionInfoFieldRead("단백질", "${String.format("%.2f", adjustedProtein)}g")
                                            NutritionInfoFieldRead("지방", "${String.format("%.2f", adjustedFat)}g")
                                        }
                                    }
                                }
                            }
                        },
                        confirmButton = {
                            Button(
                                onClick = {
                                    val updatedFoodItem = item.copy(
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
                                    showDialog = false
                                    viewModel.updateFoodItem(updatedFoodItem)
                                },
                                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                                modifier = Modifier
                                    .width(80.dp)
                                    .height(60.dp)
                                    .padding(top = 20.dp),
                            ) {
                                Text(
                                    text = "수정",
                                    color = Color.White,
                                    fontSize = 16.sp,
                                )
                            }
                        },
                        dismissButton = {
                            Button(
                                onClick = { showDialog = false },
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

        // dummyData가 비어있을 때만 표시
        if (userAddedFoodItems.isEmpty()) {
            // 수저 포크 사진
            Image(
                painter = painterResource(id = R.drawable.fork),
                contentDescription = "Signature Image",
                modifier = Modifier
                    .padding(top = 60.dp, bottom = 40.dp)
                    .width(120.dp)
                    .height(120.dp)
            )

            // 최근 추가한 음식이 없어요
            Text(
                text = "최근 추가한 음식이 없어요",
                modifier = Modifier.padding(bottom = 10.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            // 최근 식사하신 음식을 보여주세요
            Text(
                text = "최근 식사하신 음식을 보여주세요",
                modifier = Modifier,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
            )
        }
    }
}

@Composable
// 음식 선택 유무
fun SwitchWithIconExample(
    checked: Boolean,
    foodItem: FoodAddInfo,
    onCheckedChange: (Boolean) -> Unit
) {
    Switch(
        checked = checked,
        onCheckedChange = {
            onCheckedChange(it)
            Log.d("SwitchWithIconExample", "Item: ${foodItem.name}, Checked: $it")
        },
        thumbContent = if (checked) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize)
                )
            }
        } else {
            null
        }
    )
}

@Composable
// 취소 버튼
fun CancelButtonExample(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(70.dp)
            .height(33.dp)
            .padding(start = 20.dp, end = 15.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text("X", fontSize = 20.sp)
    }
}

@Composable
// 추가 버튼
fun AdditionButtonExample(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(50.dp)
            .height(33.dp)
            .padding(start = 5.dp, end = 5.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text("X", fontSize = 20.sp)
    }
}

@Composable
fun NutritionInfoFieldRead(labelText: String, text: String) {
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



