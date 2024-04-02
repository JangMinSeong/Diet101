package com.ssafy.d101.ui.view.components

import android.util.Log
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ssafy.d101.model.FoodInfo
import com.ssafy.d101.viewmodel.FoodSearchViewModel
import com.ssafy.d101.viewmodel.ModelViewModel

@Composable
fun EditFoodDialog(showDialog: Boolean, onDismiss: () -> Unit, modelViewModel: ModelViewModel, imageIndex: Int) {
    val foodList by modelViewModel.getYoloResponse().collectAsState()
    val foodSearchViewModel : FoodSearchViewModel = hiltViewModel()

    Log.d("in dialog","$foodList")

    // 현재 선택된 항목의 정보 로드
    val currentItem = foodList?.getOrNull(imageIndex)
    var foodName by remember { mutableStateOf(currentItem?.tag ?: "") }
    var carbs by remember { mutableStateOf(currentItem?.yoloFoodDto?.carbohydrate?.toString() ?: "") }
    var protein by remember { mutableStateOf(currentItem?.yoloFoodDto?.protein?.toString() ?: "") }
    var fat by remember { mutableStateOf(currentItem?.yoloFoodDto?.fat?.toString() ?: "") }
    var calorie by remember { mutableStateOf(currentItem?.yoloFoodDto?.calorie?.toString() ?: "") }

    // 검색을 위한 상태 변수
    var searchQuery by remember { mutableStateOf("") }
    var searchResult by remember { mutableStateOf(currentItem) }
    var showDropdown by remember { mutableStateOf(false) }

    val foodLists by foodSearchViewModel.foodItems.collectAsState()

    if (showDialog && currentItem != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("음식 수정하기") },
            text = {
                Column {

                    Box() {
                        TextField(
                            modifier = Modifier.width(300.dp),
                            value = searchQuery,
                            onValueChange = { searchQuery = it },
                            label = { Text("음식 검색") },
                            trailingIcon = {
                                IconButton(onClick = {
                                    foodSearchViewModel.fetchFoodItems(searchQuery)
                                    if (foodLists.isEmpty())
                                        showDropdown = false
                                    else
                                        showDropdown = true
                                }) {
                                    Icon(Icons.Default.Search, contentDescription = "검색")
                                }
                            }
                        )
                        if (showDropdown && foodLists.isNotEmpty()) {
                            // 검색 결과가 있고, 드롭다운을 보여줘야 할 경우

                            ShowDropdownMenu(foodLists, onItemSelected = { food ->
                                // 선택한 음식으로 필드 업데이트
                                // 아이템 클릭 시 필드 업데이트
                                searchQuery = food.name // 실제 음식 이름으로 검색 쿼리 업데이트
                                foodName = food.name
                                carbs = food.carbohydrate.toString()
                                protein = food.protein.toString()
                                fat = food.fat.toString()
                                calorie = food.calorie.toString()
                                showDropdown = false // 검색 결과 숨김
                            }
                            )

                        }
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    // 검색 결과 기반으로 필드 업데이트
                    Text(
                        "${foodName}",
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally),
                        textAlign = TextAlign.Center,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        "칼로리 : ${calorie} kcal",
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.CenterHorizontally)
                            .padding(end = 8.dp),
                        textAlign = TextAlign.End,
                    )
                    Spacer(modifier = Modifier.height(5.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .border(2.dp, Color.Gray, RoundedCornerShape(8.dp))
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.align(Alignment.Center)
                        ) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("탄수화물", fontSize = 15.sp)
                                Spacer(modifier = Modifier.padding(5.dp))
                                Text("${carbs} g", fontSize = 15.sp)
                            }
                            // 구분선
                            Divider(
                                color = Color.LightGray,
                                modifier = Modifier
                                    .height(75.dp)
                                    .width(1.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("단백질", fontSize = 15.sp)
                                Spacer(modifier = Modifier.padding(5.dp))
                                Text("${protein} g", fontSize = 15.sp)
                            }
                            // 구분선
                            Divider(
                                color = Color.LightGray,
                                modifier = Modifier
                                    .height(75.dp)
                                    .width(1.dp)
                                    .align(Alignment.CenterVertically)
                            )
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.weight(1f)
                            ) {
                                Text("지방(g)", fontSize = 15.sp)
                                Spacer(modifier = Modifier.padding(5.dp))
                                Text("${fat} g", fontSize = 15.sp)
                            }
                        }
                    }
                }
            },
            confirmButton = {
                Button(onClick = {
                    modelViewModel.updateFoodItem(imageIndex, foodName, carbs.toDouble(), protein.toDouble(), fat.toDouble(), calorie.toInt())
                    onDismiss()
                }) {
                    Text("확인")
                }
            },
            dismissButton = {
                Button(onClick = onDismiss) {
                    Text("취소")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDropdownMenu(foodLists: List<FoodInfo>, onItemSelected: (FoodInfo) -> Unit) {
    DropdownMenu(
        expanded = true,
        onDismissRequest = { /* 드롭다운 메뉴 닫기 처리 */ },
        modifier = Modifier.width(270.dp).heightIn(max = 200.dp)
    ) {
        foodLists.forEach { food ->
            DropdownMenuItem(
                text = { Text(food.name) },
                onClick = { onItemSelected(food) }
            )
        }
    }
}