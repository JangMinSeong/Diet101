package com.ssafy.d101.ui.view.screens

import android.util.Log
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssafy.d101.model.Dunchfast
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.view.components.DailyHorizontalBar
import com.ssafy.d101.viewmodel.DietViewModel


@Composable
fun DietAiAnalysisResultScreen(navController: NavHostController) {
//    val dietViewModel: DietViewModel = hiltViewModel()
//
//    val result by dietViewModel.getTakeReqs().collectAsState()
//    val dunchType by dietViewModel.getType().collectAsState()
//    Log.d("in diet screen","$result")

//    val isClicked = remember { mutableStateOf(false) }
//    LaunchedEffect(isClicked) {
//        if (isClicked.value) {
//            dietViewModel.saveMeal()
//        }
//    }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Ivory)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 80.dp)
                .background(Color(0xFFFFF8F8), shape = RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Spacer(modifier = Modifier.padding(30.dp))
                Row() {
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text("아침", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.padding(20.dp))
                DailyHorizontalBar(carbsPercentage = 20f, proteinPercentage = 40f, fatsPercentage = 40f)
                Spacer(modifier = Modifier.padding(20.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.LightGray)
                        .padding(20.dp)
                ) {
                    Column() {
                        Text("500 kcal",fontWeight=FontWeight.Bold)
                        Spacer(modifier = Modifier.padding(2.dp))
                        Divider(modifier = Modifier
                            .height(2.dp)
                            .fillMaxWidth(), color = Color.DarkGray)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Row(
                            modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("탄수화물")
                                Spacer(modifier = Modifier.padding(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color.White)
                                        .width(70.dp)
                                        .height(25.dp),
                                    contentAlignment = Alignment.Center

                                ) {
                                    Text("75g")
                                }
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("단백질")
                                Spacer(modifier = Modifier.padding(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color.White)
                                        .width(70.dp)
                                        .height(25.dp),
                                    contentAlignment = Alignment.Center

                                ) {
                                    Text("60g")
                                }
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("지방")
                                Spacer(modifier = Modifier.padding(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color.White)
                                        .width(70.dp)
                                        .height(25.dp)
                                        .clip(RoundedCornerShape(20.dp)),
                                    contentAlignment = Alignment.Center,


                                ) {
                                    Text("20g")
                                }
                            }
                        }

                    }
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Button(
                    onClick = {
                        //            isClicked.value = true
                        //            navController.navigate("home")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .width(160.dp)
                        .height(40.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "식사 기록하기",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

fun getDunchfastToString(dunchfast: Dunchfast): String {
    return when(dunchfast) {
        Dunchfast.BREAKFAST -> "아침"
        Dunchfast.BRUNCH -> "아점"
        Dunchfast.LUNCH -> "점심"
        Dunchfast.LINNER -> "점저"
        Dunchfast.DINNER -> "저녁"
        Dunchfast.MIDNIGHT -> "야식"
        Dunchfast.SNACK -> "간식"
        Dunchfast.DRINK -> "음료"
        Dunchfast.ALCOHOL -> "주류"
        else -> "아침" // 선택한 항목이 매핑되지 않는 경우 아침으로 고정
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDietAiAnalysisResultScreen() {
    DietAiAnalysisResultScreen(navController = rememberNavController())
}