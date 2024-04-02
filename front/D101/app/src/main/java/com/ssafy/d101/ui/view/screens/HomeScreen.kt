package com.ssafy.d101.ui.view.screens

import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.view.components.CalendarApp
import com.ssafy.d101.viewmodel.CalendarViewModel
import com.ssafy.d101.viewmodel.DietViewModel
import java.time.LocalDate


@Composable
fun HomeScreen (navController: NavHostController) {

    val dietViewModel: DietViewModel = hiltViewModel()
    val calendarViewModel: CalendarViewModel = hiltViewModel()
    val selectedDate by calendarViewModel.selectedDate.collectAsState()
    LaunchedEffect(selectedDate) {
        Log.i("HomeScreen", "selectedDate: $selectedDate")
    }

    LaunchedEffect(Unit) {
        dietViewModel.loadDayDiet(date = "2021-10-10")
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Ivory)
            .padding(16.dp)
    ) {
        Column {
            CalendarApp(modifier = Modifier.padding(16.dp), calendarViewModel)
            Spacer(modifier = Modifier.padding(16.dp))
            if (!selectedDate.isAfter(LocalDate.now())) {
                MainContents()
            } else {
                Text(text = "Back to the future")
            }
        }
    }
}

@Composable
fun MainContents() {
    val animatedValue = remember { Animatable(0f) }

    // 특정 값으로 색을 채우는 Animation
    LaunchedEffect(Unit) {
        animatedValue.animateTo(
            targetValue = 200F,
            animationSpec = tween(durationMillis = 2000, easing = LinearEasing),
        )
    }

    Column {
        Text(text = "통계")
        Divider(Modifier.padding(top = 16.dp, bottom = 16.dp))
    }

    Card(Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(containerColor = Color(0xffFFFFD1))
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
                                Color(0xffF2D179), Color(0xffCCF279)
                            ),
                            start = Offset.Zero,
                            end = Offset.Infinite,
                        ),
                        startAngle = -90F,
                        sweepAngle = animatedValue.value,
                        useCenter = false,
                        topLeft = Offset((size.width - sizeArc.width) / 2f, (size.height - sizeArc.height) / 2f),
                        size = sizeArc,
                        style = Stroke(width = 70F, cap = StrokeCap.Round)
                    )
                }
            }
        }
    }

    Column {
        Text(text = "식단")
        Divider(Modifier.padding(top = 16.dp, bottom = 16.dp))
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun NutritionGrid() {
    Column(Modifier.width(300.dp)) {
        Row() {
            NutritionCell("")
            NutritionCell("목표량")
            NutritionCell("섭취량")
        }
        Row() {
            NutritionCell("탄수화물")
            NutritionCell("150g")
            NutritionCell("100g")
        }
        Row() {
            NutritionCell("단백질")
            NutritionCell("90g")
            NutritionCell("40g")
        }
        Row() {
            NutritionCell("지방")
            NutritionCell("24g")
            NutritionCell("12g")
        }
        Row() {
            NutritionCell("열량")
            NutritionCell("2000kcal")
            NutritionCell("1408kcal")
        }
    }
}

@Composable
fun NutritionCell(text: String) {
    Box(modifier = Modifier
        .width(60.dp)
        .height(30.dp)) {
        Text(text = text, modifier = Modifier.align(Alignment.Center))
    }
}