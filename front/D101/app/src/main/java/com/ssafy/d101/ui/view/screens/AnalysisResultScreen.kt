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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.graphicsLayer
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssafy.d101.model.YoloFood
import com.ssafy.d101.ui.view.components.CroppedImagesDisplay
import com.ssafy.d101.ui.view.components.DailyHorizontalBar
import com.ssafy.d101.ui.view.components.EditFoodDialog
import com.ssafy.d101.viewmodel.ModelViewModel

@Composable
fun AnalysisResultScreen(navController: NavHostController) {
    var imageIndex by remember { mutableStateOf(0) }     // 현재 이미지 인덱스

    var showDialog by remember { mutableStateOf(false) } // 수정을 위한 모달

    // 자른 이미지 리스트 (임시 데이터)
    //val imageResources = listOf(R.drawable.fakefoodimage, R.drawable.image2, R.drawable.image3)

    val modelViewModel : ModelViewModel = hiltViewModel()
    val analysisResult by modelViewModel.getYoloResponse().collectAsState()

    val imageUri by modelViewModel.getImageUri().collectAsState()

    // YoloResponse에서 tag 리스트로 변환
    val foodNames = analysisResult?.map { it.tag } ?: listOf()

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
            // 페이지 번호
            if(foodNames.isNotEmpty()) {
                Text(
                    text = "${imageIndex + 1}/${foodNames.size}",
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                )

                Spacer(modifier = Modifier.height(50.dp))

                // 음식 이름
                Text(
                    text = foodNames[imageIndex],
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Red,
                )
                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    if (imageIndex > 0) {
                        // 이전 버튼
                        Image(
                            painter = painterResource(id = R.drawable.nextbutton),
                            contentDescription = "이전 버튼",
                            modifier = Modifier
                                .size(40.dp)
                                .graphicsLayer { scaleX = -1f }    // 수평 반전
                                .clickable { if (imageIndex > 0) imageIndex-- }
                        )
                    } else {
                        Spacer(modifier = Modifier.size(40.dp))
                    }

                    analysisResult?.let { results ->
                        if (results.isNotEmpty() && imageIndex in results.indices) {
                            val currentItem = results[imageIndex]

                            // 현재 선택된 항목으로부터 imageInfo 맵 생성
                            val imageInfo = mapOf(
                                "tag" to currentItem.tag,
                                "left_top" to currentItem.left_top,
                                "width" to currentItem.width,
                                "height" to currentItem.height
                            )

                            // CroppedImagesDisplay 컴포넌트에 imageInfo와 imageUri 넘기기
                            imageUri?.let { uri ->
                                CroppedImagesDisplay(
                                    imageInfo = imageInfo,
                                    imageUri!!,
                                    modifier = Modifier.size(300.dp, 300.dp)
                                )
                            }
                        }
                    }

                    if (imageIndex < foodNames.size - 1) {
                        // 다음 버튼
                        Image(
                            painter = painterResource(id = R.drawable.nextbutton),
                            contentDescription = "다음 버튼",
                            modifier = Modifier
                                .size(40.dp)
                                .clickable { if (imageIndex < foodNames.size - 1) imageIndex++ }
                        )
                    } else {
                        Spacer(modifier = Modifier.size(40.dp))
                    }
                }

                Spacer(modifier = Modifier.height(40.dp))

                analysisResult?.let { results ->
                    if (results.isNotEmpty() && imageIndex in results.indices) {
                        val currentItem = results[imageIndex]
                        if (currentItem.yoloFoodDto != null) {
                            Text(
                                text = currentItem.yoloFoodDto.calorie.toString() + " Kcal",
                                fontSize = 25.sp,
                                fontWeight = FontWeight.Bold
                            )
                            val nutri = calNutri(currentItem.yoloFoodDto)
                            DailyHorizontalBar(
                                carbsPercentage = nutri.first.toFloat(),
                                proteinPercentage = nutri.second.toFloat(),
                                fatsPercentage = nutri.third.toFloat()
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = { showDialog = true },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .width(160.dp)
                        .height(40.dp)
                ) {
                    Text(
                        text = "음식 수정하기",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                // 모달 창 표시
                if (showDialog) {
                    EditFoodDialog(showDialog = showDialog, onDismiss = { showDialog = false }, modelViewModel = modelViewModel, imageIndex = imageIndex)
                }

                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        modelViewModel.deleteYoloResponseItem(imageIndex)
                        if(imageIndex != 0)
                            imageIndex--
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Red),
                    modifier = Modifier
                        .width(160.dp)
                        .height(40.dp)
                ) {
                    Text(
                        text = "음식 삭제하기",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                       navController.navigate("foodResistList")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .width(160.dp)
                        .height(40.dp)
                ) {
                    Text(
                        text = "음식 등록하기",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
            else {
                Text(text="음식이 확인 되지 않습니다",fontSize=20.sp,fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = { navController.navigate("foodAddition") },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .width(160.dp)
                        .height(40.dp)
                ) {
                    Text(
                        text = "음식 직접 등록하기",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}



fun calNutri(foodInfo : YoloFood) : Triple<Double,Double,Double> {
    val carb : Double = foodInfo.carbohydrate*4
    val prot : Double = foodInfo.protein*4
    val fat : Double = foodInfo.fat*9

    return Triple(carb*100/foodInfo.calorie, prot*100/foodInfo.calorie, fat*100/foodInfo.calorie)
}

@Preview(showBackground = true)
@Composable
fun PreviewAnalysisResultScreen() {
    AnalysisResultScreen(navController = rememberNavController())
}
