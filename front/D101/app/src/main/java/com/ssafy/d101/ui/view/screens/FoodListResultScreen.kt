package com.ssafy.d101.ui.view.screens
import android.util.Log
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.graphicsLayer
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssafy.d101.model.FoodAddInfo
import com.ssafy.d101.model.IntakeReq
import com.ssafy.d101.model.YoloFood
import com.ssafy.d101.model.YoloResponse
import com.ssafy.d101.ui.view.components.CroppedImagesDisplay
import com.ssafy.d101.ui.view.components.DailyHorizontalBar
import com.ssafy.d101.viewmodel.DietViewModel
import com.ssafy.d101.viewmodel.FoodSearchViewModel
import com.ssafy.d101.viewmodel.ModelViewModel

@Composable
fun FoodListResultScreen(navController: NavHostController) {
    val foodViewModel : FoodSearchViewModel = hiltViewModel()
    val uploadedFoodItems by foodViewModel.userAddedFoodItems.collectAsState()

    val modelViewModel : ModelViewModel = hiltViewModel()
    val yoloResult by  modelViewModel.getYoloResponse().collectAsState()

    val dietViewModel : DietViewModel = hiltViewModel()
    val intakeReqs = yoloResult?.let { createIntakeReqList(uploadedFoodItems, it) } ?: emptyList()

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
            // 업로드된 음식 목록 표시
            LazyColumn {
                items(intakeReqs) { foodItem ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = foodItem.name,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.weight(1f)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(text = "${foodItem.kcal}kcal")
                    }
                }
            }

            // 식단 분석 하러 가기 버튼
            Button(
                onClick = {
                    if (intakeReqs != null) {
                        dietViewModel.setTakeReqList(intakeReqs)
                        //TODO : 화면이동
                        //navController.navigate("")
                    }
                          },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .width(160.dp)
                    .height(40.dp)
            ) {
                Text(
                    text = "식단 분석 하러 가기",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            
            Spacer(modifier = Modifier.padding(20.dp))
            // 식사 추가 하러 가기 버튼
            Button(
                onClick = { /* TODO: 버튼 클릭 이벤트 처리 */ },
                colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                modifier = Modifier
                    .width(160.dp)
                    .height(40.dp)
            ) {
                Text(
                    text = "식사 추가 하러 가기",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                )
            }
            // 페이지 번호
//            if(foodNames.isNotEmpty()) {
//                Text(
//                    text = "${imageIndex + 1}/${foodNames.size}",
//                    fontSize = 25.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Gray,
//                )
//
//                Spacer(modifier = Modifier.height(50.dp))
//
//                // 음식 이름
//                Text(
//                    text = foodNames[imageIndex],
//                    fontSize = 30.sp,
//                    fontWeight = FontWeight.Bold,
//                    color = Color.Red,
//                )
//                Spacer(modifier = Modifier.height(16.dp))
//
//                Row(
//                    verticalAlignment = Alignment.CenterVertically,
//                    modifier = Modifier.fillMaxWidth(),
//                    horizontalArrangement = Arrangement.SpaceEvenly
//                ) {
//                    if (imageIndex > 0) {
//                        // 이전 버튼
//                        Image(
//                            painter = painterResource(id = R.drawable.nextbutton),
//                            contentDescription = "이전 버튼",
//                            modifier = Modifier
//                                .size(40.dp)
//                                .graphicsLayer { scaleX = -1f }    // 수평 반전
//                                .clickable { if (imageIndex > 0) imageIndex-- }
//                        )
//                    } else {
//                        Spacer(modifier = Modifier.size(40.dp))
//                    }
//
//                    analysisResult?.let { results ->
//                        if (results.isNotEmpty() && imageIndex in results.indices) {
//                            val currentItem = results[imageIndex]
//
//                            // 현재 선택된 항목으로부터 imageInfo 맵 생성
//                            val imageInfo = mapOf(
//                                "tag" to currentItem.tag,
//                                "left_top" to currentItem.left_top,
//                                "width" to currentItem.width,
//                                "height" to currentItem.height
//                            )
//
//                            // CroppedImagesDisplay 컴포넌트에 imageInfo와 imageUri 넘기기
//                            imageUri?.let { uri ->
//                                CroppedImagesDisplay(
//                                    imageInfo = imageInfo,
//                                    imageUri!!,
//                                    modifier = Modifier.size(300.dp, 300.dp)
//                                )
//                            }
//                        }
//                    }
//
//                    if (imageIndex < foodNames.size - 1) {
//                        // 다음 버튼
//                        Image(
//                            painter = painterResource(id = R.drawable.nextbutton),
//                            contentDescription = "다음 버튼",
//                            modifier = Modifier
//                                .size(40.dp)
//                                .clickable { if (imageIndex < foodNames.size - 1) imageIndex++ }
//                        )
//                    } else {
//                        Spacer(modifier = Modifier.size(40.dp))
//                    }
//                }
//
//                Spacer(modifier = Modifier.height(40.dp))
//
//                analysisResult?.let { results ->
//                    if (results.isNotEmpty() && imageIndex in results.indices) {
//                        val currentItem = results[imageIndex]
//                        if (currentItem.yoloFoodDto != null) {
//                            Text(
//                                text = currentItem.yoloFoodDto.calorie.toString() + " Kcal",
//                                fontSize = 25.sp,
//                                fontWeight = FontWeight.Bold
//                            )
//                            val nutri = calNutri2(currentItem.yoloFoodDto)
//                            DailyHorizontalBar(
//                                carbsPercentage = nutri.first.toFloat(),
//                                proteinPercentage = nutri.second.toFloat(),
//                                fatsPercentage = nutri.third.toFloat()
//                            )
//                        }
//                    }
//                }
//                Spacer(modifier = Modifier.height(20.dp))
//            }
//            else {
//                Text(text="음식이 확인 되지 않습니다",fontSize=20.sp,fontWeight = FontWeight.Bold)
//                Spacer(modifier = Modifier.height(20.dp))
//            }
//
        }
    }
}

//fun calNutri2(foodInfo : YoloFood) : Triple<Double,Double,Double> {
//    val carb : Double = foodInfo.carbohydrate*4
//    val prot : Double = foodInfo.protein*4
//    val fat : Double = foodInfo.fat*9
//
//    return Triple(carb*100/foodInfo.calorie, prot*100/foodInfo.calorie, fat*100/foodInfo.calorie)
//}

//@Preview(showBackground = true)
//@Composable
//fun PreviewAnalysisResultScreen2() {
//    FoodListResultScreen(navController = rememberNavController())
//}


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
            // yoloResponse를 사용하는 로직도 여기에 추가
            // 예: yoloResponse.forEach { ... }
        }
    }
    if(yoloResult.isNotEmpty()) {
        yoloResult.forEach { foodItem ->
            intakeReqs.add(
                IntakeReq(
                    food_id = foodItem.yoloFoodDto.id.toLong(),
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