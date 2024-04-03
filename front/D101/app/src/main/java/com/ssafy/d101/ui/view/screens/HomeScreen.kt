package com.ssafy.d101.ui.view.screens

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ssafy.d101.model.DailyNutrient
import com.ssafy.d101.model.DietInfo
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.view.components.CalendarApp
import com.ssafy.d101.ui.view.components.FoodDetailScreen
import com.ssafy.d101.ui.view.components.FoodItemCard
import com.ssafy.d101.viewmodel.CalendarViewModel
import com.ssafy.d101.viewmodel.DietViewModel
import com.ssafy.d101.viewmodel.UserViewModel
import java.time.LocalDate


@Composable
fun HomeScreen (navController: NavHostController) {

    val dietViewModel: DietViewModel = hiltViewModel()
    val calendarViewModel: CalendarViewModel = hiltViewModel()
    val selectedDate by calendarViewModel.selectedDate.collectAsState()
    val dayDiet by dietViewModel.dayDiet.collectAsState()

    val dailyNutrient by dietViewModel.dailyNutrient.collectAsState()

    LaunchedEffect(selectedDate) {
        dietViewModel.loadDayDiet(date = selectedDate.toString())
        Log.i("HomeScreen", "selectedDate: $selectedDate")
    }

    LaunchedEffect(Unit) {
        dietViewModel.loadDayDiet(date = LocalDate.now().toString())
    }

    LaunchedEffect(dayDiet) {
        dietViewModel.refreshDailyNutrient()
        Log.i("HomeScreen", "dayDiet: $dayDiet")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Ivory)
            .padding(16.dp)
    ) {
        Column {
            CalendarApp(modifier = Modifier, calendarViewModel)
            Spacer(modifier = Modifier.padding(top = 16.dp))
            if (!selectedDate.isAfter(LocalDate.now())) {
                MainContents(dailyNutrient, selectedDate, dayDiet)
            } else {
                Text(text = "Back to the future")
            }
        }
    }
}

@Composable
fun MainContents(dailyNutrient: DailyNutrient?, selectedDate: LocalDate, dayDiet: List<DietInfo>?) {
    val animatedValue = remember { Animatable(0f) }

    val userViewModel: UserViewModel = hiltViewModel()

    val recommendedCalorie by userViewModel.calorie.collectAsState()
    val recommendedCarbohydrate by remember {
        derivedStateOf { recommendedCalorie?.div(10) }
    }
    val recommendedProtein by remember {
        derivedStateOf { recommendedCalorie?.div(10) }
    }
    val recommendedFat by remember {
        derivedStateOf { recommendedCalorie?.div(45) }
    }

    // 특정 값으로 색을 채우는 Animation
    LaunchedEffect(Unit) {
        animatedValue.animateTo(
            targetValue = 1F,
            animationSpec = tween(durationMillis = 2000, easing = EaseInOutQuad),
        )
    }

    LaunchedEffect(selectedDate) {
        animatedValue.snapTo(0F)
        animatedValue.animateTo(
            targetValue = 1F, // 애니메이션의 최종값을 1로 설정
            animationSpec = tween(
                durationMillis = 2000, // 애니메이션 지속 시간을 2000ms로 설정
                easing = EaseInOutQuad // EaseInOutQuad 이징 함수 사용
            )
        )
    }

    LazyColumn(
        contentPadding = PaddingValues(bottom = 60.dp),
    ) {
        item {
            Text(text = "통계")
            Divider(Modifier.padding(top = 16.dp, bottom = 16.dp))
        }
        item {
            Card(Modifier.fillMaxWidth(),
                colors = CardDefaults.cardColors(containerColor = Color(0xffd9d8b7))
            ) {
                Column(
                    Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                ) {
                    Text(text = "하루 통계")
                    Box(
                        Modifier
                            .width(200.dp)
                            .height(200.dp)
                    ) {
                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val sizeArc = size / 1.3F
                            drawArc(
                                color = Ivory,
                                startAngle = 0F,
                                sweepAngle = 360F,
                                useCenter = false,
                                topLeft = Offset((size.width - sizeArc.width) / 2f, (size.height - sizeArc.height) / 2f),
                                size = sizeArc,
                                style = Stroke(width = 70F)
                            )
                            drawArc(
                                brush = Brush.linearGradient(
                                    colors = listOf(
                                        Color(0xffde9f3d), Color(0xff7fd1ae)
                                    ),
                                    start = Offset.Zero,
                                    end = Offset.Infinite,
                                ),
                                startAngle = -90F,
                                sweepAngle = animatedValue.value * 360F * (dailyNutrient?.totalCalorie?.toFloat() ?: 0F) / recommendedCalorie!!,
                                useCenter = false,
                                topLeft = Offset((size.width - sizeArc.width) / 2f, (size.height - sizeArc.height) / 2f),
                                size = sizeArc,
                                style = Stroke(width = 70F, cap = StrokeCap.Round)
                            )
                        }
                        Text(text = "${dailyNutrient?.totalCalorie}   /\n$recommendedCalorie kcal", modifier = Modifier.align(Alignment.Center))
                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                    Row {
                        Text(text = "탄수화물", modifier = Modifier.width(60.dp), textAlign = TextAlign.Right)
                        Box {
                            Canvas(modifier = Modifier.fillMaxWidth()) {
                                val barHeight = 70F
                                drawLine(
                                    cap = StrokeCap.Round,
                                    color = Ivory,
                                    start = Offset(100F, 23F),
                                    end = Offset(size.width - 80F, 23F),
                                    strokeWidth = barHeight
                                )
                            }
                            Canvas(modifier = Modifier.fillMaxWidth()) {
                                val barHeight = 70F
                                val factor = ((dailyNutrient?.totalCarbohydrate ?: 0.0) / (recommendedCarbohydrate?.toDouble() ?: 1.0)).coerceAtMost(1.0)
                                drawLine(
                                    color = Color(0xffde9f3d),
                                    cap = StrokeCap.Round,
                                    start = Offset(100F, 23F),
                                    end = Offset(100F + (size.width - 180F) * animatedValue.value * factor.toFloat(), 23F),
                                    strokeWidth = barHeight
                                )
                            }
                            Text(text = "${dailyNutrient?.totalCarbohydrate?.toInt()}g / ${recommendedCarbohydrate}g", modifier = Modifier.align(Alignment.Center))
                        }

                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                    Row {
                        Text(text = "단백질", modifier = Modifier.width(60.dp), textAlign = TextAlign.Right)
                        Box {
                            Canvas(modifier = Modifier.fillMaxWidth()) {
                                val barHeight = 70F
                                drawLine(
                                    cap = StrokeCap.Round,
                                    color = Ivory,
                                    start = Offset(100F, 23F),
                                    end = Offset(size.width - 80F, 23F),
                                    strokeWidth = barHeight
                                )
                            }
                            Canvas(modifier = Modifier.fillMaxWidth()) {
                                val barHeight = 70F
                                val factor = ((dailyNutrient?.totalProtein ?: 0.0) / (recommendedProtein?.toDouble() ?: 1.0)).coerceAtMost(1.0)
                                drawLine(
                                    cap = StrokeCap.Round,
                                    color = Color(0xffde9f3d),
                                    start = Offset(100F, 23F),
                                    end = Offset(100F + (size.width - 180F) * animatedValue.value * factor.toFloat(), 23F),
                                    strokeWidth = barHeight
                                )
                            }
                            Text(text = "${dailyNutrient?.totalProtein?.toInt()}g / ${recommendedProtein}g", modifier = Modifier.align(Alignment.Center))
                        }

                    }
                    Spacer(modifier = Modifier.padding(16.dp))
                    Row {
                        Text(text = "지방", modifier = Modifier.width(60.dp), textAlign = TextAlign.Right)
                        Box {
                            Canvas(modifier = Modifier.fillMaxWidth()) {
                                val barHeight = 70F
                                drawLine(
                                    cap = StrokeCap.Round,
                                    color = Ivory,
                                    start = Offset(100F, 23F),
                                    end = Offset(100F + (size.width - 180F), 23F),
                                    strokeWidth = barHeight
                                )
                            }
                            Canvas(modifier = Modifier.fillMaxWidth()) {
                                val barHeight = 70F
                                val factor = ((dailyNutrient?.totalFat ?: 0.0) / (recommendedFat?.toDouble() ?: 1.0)).coerceAtMost(1.0)

                                drawLine(
                                    cap = StrokeCap.Round,
                                    color = Color(0xffde9f3d),
                                    start = Offset(100F, 23F),
                                    end = Offset(100F + (size.width - 180F) * animatedValue.value * factor.toFloat(), 23F),
                                    strokeWidth = barHeight
                                )
                            }
                            Text(text = "${dailyNutrient?.totalFat?.toInt()}g / ${recommendedFat}g", modifier = Modifier.align(Alignment.Center))
                        }

                    }

                }
            }
        }
        item {
            Spacer(modifier = Modifier.padding(16.dp))
            Text(text = "식단")
            Divider(Modifier.padding(top = 16.dp, bottom = 16.dp))
            dayDiet?.forEach() {
                FoodDetailScreen(dietInfo = it)
                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}