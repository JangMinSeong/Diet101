package com.ssafy.d101.ui.view.components

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
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
    var showResults by remember { mutableStateOf(false) }

    val foodLists by foodSearchViewModel.foodItems.collectAsState()

    if (showDialog && currentItem != null) {
        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text("음식 수정하기") },
            text = {
                Column {
                    TextField(
                        value = searchQuery,
                        onValueChange = { searchQuery = it },
                        label = { Text("음식 검색") },
                        trailingIcon = {
                            IconButton(onClick = {
                                foodSearchViewModel.fetchFoodItems(searchQuery)
                                showResults = true
                            }) {
                                Icon(Icons.Default.Search, contentDescription = "검색")
                            }
                        }
                    )
                    if (showResults) {
                        LazyColumn(modifier = Modifier.fillMaxHeight(0.5f).padding(top = 8.dp)) {
                            items(foodLists) { food ->
                                Text(
                                    text = food.name, // 'name'은 검색 결과 항목의 이름 필드를 가정합니다.
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clickable {
                                            // 아이템 클릭 시 필드 업데이트
                                            searchQuery = food.name // 실제 음식 이름으로 검색 쿼리 업데이트
                                            foodName = food.name
                                            carbs = food.carbohydrate.toString()
                                            protein = food.protein.toString()
                                            fat = food.fat.toString()
                                            calorie = food.calorie.toString()
                                            showResults = false // 검색 결과 숨김
                                        }
                                        .padding(8.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(8.dp))

                    // 검색 결과 기반으로 필드 업데이트
                    Text("음식 이름: ${foodName}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("칼로리(kcal): ${calorie}")
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(
                    ) {
                        Column() {
                            Text("탄수화물(g)")
                            Text("${carbs}")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Column() {
                            Text("단백질(g)")
                            Text("${protein}")
                        }
                        Spacer(modifier = Modifier.height(8.dp))
                        Column() {
                            Text("지방(g)")
                            Text("${fat}")
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