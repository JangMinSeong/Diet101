package com.ssafy.d101.ui.view.screens
import android.util.Log
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ssafy.d101.model.Dunchfast
import com.ssafy.d101.model.FoodAddInfo
import com.ssafy.d101.model.IntakeReq
import com.ssafy.d101.model.YoloResponse
import com.ssafy.d101.viewmodel.DietViewModel
import com.ssafy.d101.viewmodel.FoodSearchViewModel
import com.ssafy.d101.viewmodel.ModelViewModel

@Composable
fun FoodListResultScreen(navController: NavHostController) {
    val foodViewModel : FoodSearchViewModel = hiltViewModel()
    val uploadedFoodItems by foodViewModel.uploadedPostItems.collectAsState()

    val modelViewModel : ModelViewModel = hiltViewModel()
    val yoloResult by  modelViewModel.getYoloResponse().collectAsState()

    val dietViewModel : DietViewModel = hiltViewModel()
    val intakeReqs = yoloResult?.let { createIntakeReqList(uploadedFoodItems, it) } ?: createIntakeReqList(uploadedFoodItems,
        emptyList()
    )

    var selectedMeal by remember { mutableStateOf<String?>(null) }
    var dunchfastType by remember {mutableStateOf<Dunchfast?>(Dunchfast.BREAKFAST)}

    // 사용자가 항목을 선택하거나 선택을 취소하는 로직
    val onMealSelected: (String) -> Unit = { meal ->
        dunchfastType = getDunchfastType(meal)
        selectedMeal = if (selectedMeal == meal) null else meal
    }
    // 선택된 항목이 있는지 확인하는 함수
    val isItemSelected: (String) -> Boolean = { it == selectedMeal }
    val scrollState = rememberScrollState()
    // 각 음식 아이템의 먹은 양을 저장하는 상태
//    val eatenAmounts = remember { mutableStateMapOf<Long, String>() }
    val eatenAmounts = remember { mutableStateMapOf<Long, Double>() }

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

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = 70.dp, bottom = 110.dp, start = 30.dp, end = 30.dp),
        ) {

            Text(
                text = "\uD83E\uDD63" + " " +"분석할 음식 리스트",
                fontWeight = FontWeight.Bold,
                fontSize = 25.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 25.dp, bottom = 15.dp, top = 30.dp),
                textAlign = TextAlign.Start,
            )

            // 실선
            Box(
                modifier = Modifier
                    .fillMaxWidth(0.9f)
                    .height(1.dp)
                    .background(Color.Gray)
                    .padding(vertical = 45.dp)
            )

            // 업로드된 음식 목록 표시
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(start = 8.dp, end = 8.dp, bottom = 20.dp)
            ) {
                items(intakeReqs) { foodItem ->
                    var eatenAmount by remember { mutableStateOf("1.0") }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clickable {}
                    ) {
                        Text(
                            text = foodItem.name,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 5.dp),
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.width(8.dp))
//                        Text(text = "${foodItem.kcal}kcal")

                        Column(
                            modifier = Modifier,
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {
                            Text(
                                text = "먹은 양",
                                modifier = Modifier.padding(top = 10.dp, bottom = 5.dp),
                                color = Color.Gray,
                                fontWeight = FontWeight.Bold
                            )

                            // 먹은 양 입력 상자
                            TextField(
                                value = eatenAmount,
                                onValueChange = { newValue ->
                                    eatenAmount = newValue
                                    eatenAmounts[foodItem.food_id] = newValue.toDoubleOrNull() ?: 1.0
                                    //여기야
                                },
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(65.dp)
                                    .padding(bottom = 15.dp, end = 5.dp),
                                singleLine = true,
                                placeholder = { Text(text = "", fontSize = 10.sp) },
                                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                            )
                        }
                    }
                    Divider(color = Color.Gray)
                }
            }

//            // 먹은 양 수정 완료
//            Button(
//                onClick = {
//                    intakeReqs.forEach { item ->
//                        val newEatenAmount = eatenAmounts[item.food_id]?.toDoubleOrNull() ?: 1.0
//                        val updatedItem = item.copy(amount = newEatenAmount)
//                    //    foodViewModel.updateEatenAmount(updatedItem)
//                    }
//                },
//                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFF12369)),
//                modifier = Modifier
//                    .width(200.dp)
//                    .height(40.dp)
//            ) {
//                Text(
//                    text = "먹은 양 수정 완료",
//                    color = Color.White,
//                    fontSize = 16.sp,
//                    fontWeight = FontWeight.Bold,
//                )
//            }
//            Spacer(modifier = Modifier.padding(top = 15.dp, bottom = 40.dp))


            Text("분류", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                .align(Alignment.Start)
                .padding(start = 35.dp, top = 10.dp))
            listOf("아침", "아점", "점심", "점저", "저녁", "야식", "간식", "음료", "주류").chunked(3).forEach { chunk ->
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                    chunk.forEach { meal ->
                        OutlinedButton(
                            onClick = { onMealSelected(meal) },
                            border = BorderStroke(if (isItemSelected(meal)) 4.dp else 1.dp, Color.Black),
                            modifier = Modifier
                                .width(90.dp)
                        ) {
                            Text(meal, fontWeight = FontWeight.Bold, color = if (isItemSelected(meal)) Color.Black else Color.Black)
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.padding(20.dp))

            // 식단 분석 하러 가기 버튼
            Button(
                onClick = {
                    if (intakeReqs != null) {
                        // intakeReqs 업데이트
                        val updatedIntakeReqs = intakeReqs.map { intakeReq ->
                            intakeReq.copy(
                                amount = eatenAmounts[intakeReq.food_id] ?: intakeReq.amount
                            )
                        }
                        dunchfastType?.let { dietViewModel.setDietType(it) }
                        dietViewModel.setTakeReqList(updatedIntakeReqs)
                        Log.d("in Result screen","$updatedIntakeReqs, $dunchfastType")

                        navController.navigate("dietAiAnalysisResult")
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .width(200.dp)
                    .height(40.dp)
            ) {
                Text(
                    text = "식단 분석 하러 가기",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }

            Spacer(modifier = Modifier.padding(10.dp))
            // 식사 추가 하러 가기 버튼
            Button(
                onClick = {
                    navController.navigate("foodAddition")
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF090552)),
                modifier = Modifier
                    .width(200.dp)
                    .height(40.dp)
            ) {
                Text(
                    text = "식사 추가 하러 가기",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
        }
    }
}


fun createIntakeReqList(uploadedFoodItems: List<FoodAddInfo>, yoloResult: List<YoloResponse>): List<IntakeReq> {
    val intakeReqs = mutableListOf<IntakeReq>()

    if(uploadedFoodItems.isNotEmpty()) {
        // uploadedFoodItems로부터 IntakeReq 리스트 생성
        uploadedFoodItems.forEach { foodItem ->
            intakeReqs.add(
                IntakeReq(
                    food_id = foodItem.id.toLong(),
                    amount = 1.0,
                    name = foodItem.name,
                    kcal = foodItem.calorie,
                    carbohydrate = foodItem.carbohydrate,
                    protein = foodItem.protein,
                    fat = foodItem.fat
                )
            )
            Log.d("createIntakeReqList작동","${foodItem.name}")
            // yoloResponse를 사용하는 로직도 여기에 추가
            // 예: yoloResponse.forEach { ... }
        }
    } else {
        Log.d("uploadedFoodItems 비었다!","ㅇㅇ")
    }
    if(yoloResult.isNotEmpty()) {
        yoloResult.forEach { foodItem ->
            intakeReqs.add(
                IntakeReq(
                    food_id = foodItem.yoloFoodDto.food_id,
                    amount = 1.0,
                    name = foodItem.tag,
                    kcal = foodItem.yoloFoodDto.calorie,
                    carbohydrate = foodItem.yoloFoodDto.carbohydrate,
                    protein = foodItem.yoloFoodDto.protein,
                    fat = foodItem.yoloFoodDto.fat
                )
            )
        }
    }

    return intakeReqs
}

fun getDunchfastType(selectedMeal: String): Dunchfast {
    return when(selectedMeal) {
        "아침" -> Dunchfast.BREAKFAST
        "아점" -> Dunchfast.BRUNCH
        "점심" -> Dunchfast.LUNCH
        "점저" -> Dunchfast.LINNER
        "저녁" -> Dunchfast.DINNER
        "야식" -> Dunchfast.NIGHT
        "간식" -> Dunchfast.SNACK
        "음료" -> Dunchfast.BEVERAGE
        "주류" -> Dunchfast.ALCOHOL
        else -> Dunchfast.BREAKFAST // 선택한 항목이 매핑되지 않는 경우 아침으로 고정
    }
}