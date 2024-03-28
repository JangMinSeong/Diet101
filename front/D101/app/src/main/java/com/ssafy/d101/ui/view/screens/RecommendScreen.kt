package com.ssafy.d101.ui.view.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.theme.White
import com.ssafy.d101.ui.view.components.BackHeader

@Composable
fun RecommendScreen(navController: NavHostController) {
    val textStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = Color.Black)
    val goal_cal=2316;
    val rest_cal=1620;

    Column( modifier = Modifier // 백그라운드
        .fillMaxSize()
        .background(Ivory)
    ) {
        BackHeader("음식 추천", navController)
        RecommendSteps(step = 0)
        Column(modifier = Modifier.padding(40.dp)) {
            Text(text = "남은 끼니 수를 알려 주시면", style = textStyle)
            Text(text = "적당한 한 끼를 추천해 드릴게요.", style = textStyle)
        }
        Column ( modifier = Modifier
            .padding(30.dp, 0.dp, 30.dp, 30.dp)
            .shadow(15.dp, RoundedCornerShape(12.dp))
            .background(White, shape = RoundedCornerShape(12.dp))
        ) {
            Row (modifier = Modifier.padding(20.dp,20.dp,20.dp,0.dp)){
                Text(text = "목표 칼로리", style = textStyle)
                Spacer(Modifier.weight(1f))
                Text(text = "${goal_cal} kcal", style = textStyle)
            }
            Row (modifier = Modifier.padding(20.dp,10.dp,20.dp,15.dp)){
                Text(text = "남은 칼로리", style = textStyle)
                Spacer(Modifier.weight(1f))
                Text(text = "${rest_cal} kcal", style = textStyle)
            }
            Row (modifier = Modifier
                .padding(20.dp, 10.dp, 20.dp, 15.dp)
                .fillMaxWidth(),
                horizontalArrangement = Arrangement.Center){
                Text(text = "남은 끼니", style = textStyle, modifier = Modifier.align(Alignment.CenterVertically))
            }

//                Text(text = "1234")
        }
        Column(Modifier.fillMaxSize().padding(30.dp,0.dp,30.dp,100.dp)) {
            Spacer(modifier = Modifier.weight(1f).fillMaxSize())
            Button(
                onClick = {/*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB6B284), // 버튼 배경색
                    contentColor = Color.White // 버튼 텍스트색
                ),
                // 버튼의 크기를 설정
                modifier = Modifier.size(width = 80.dp, height = 35.dp).align(Alignment.End)
            ) {
                Text(
                    text = "다음", style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White // 색상도 설정할 수 있음
                    )
                )
            }
        }

    }

}

@Composable
fun RecommendSteps(step: Int) {
    Row(modifier = Modifier.padding(horizontal = 30.dp)) {
        StepsProgressBar(modifier = Modifier.fillMaxWidth(), numberOfSteps = 1, currentStep = step)
    }
    Row(modifier = Modifier.padding(20.dp,0.dp,0.dp,0.dp)) {
        Text(text = "남은 끼니")
        Spacer(modifier = Modifier.size(90.dp,0.dp))
        Text(text = "칼로리 조절")
        Spacer(modifier = Modifier.size(87.dp,0.dp))
        Text(text = "추천 결과")
    }
}

@Composable
fun StepsProgressBar(modifier: Modifier = Modifier, numberOfSteps: Int, currentStep: Int) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Circle
        Canvas(modifier = Modifier
            .size(20.dp),
            onDraw = {
                drawCircle(color = Color(0xFFDFDA93))
            }
        )
        for (step in 0..numberOfSteps) {
            Step(
                modifier = Modifier.weight(1F),
                isCompete = step < currentStep,
                isCurrent = step < currentStep
            )
        }
    }
}

@Composable
fun Step(modifier: Modifier = Modifier, isCompete: Boolean, isCurrent: Boolean) {
    val color = if (isCompete || isCurrent) Color(0xFFDFDA93) else Color.Gray
    val innerCircleColor = if (isCompete) Color(0xFFDFDA93) else Color.Gray
    Box(modifier = modifier) {
        //Line
        Divider(
            modifier = Modifier.align(Alignment.CenterStart),
            color = color,
            thickness = 2.dp
        )
        //Circle
        Canvas(modifier = Modifier
            .size(20.dp)
            .align(Alignment.CenterEnd),
            onDraw = {
                drawCircle(color = innerCircleColor)
            }
        )
    }
}

@Preview
@Composable
fun StepsProgressBarPreview() {
    val currentStep = remember { mutableStateOf(1) }
    StepsProgressBar(modifier = Modifier.fillMaxWidth(), numberOfSteps = 1, currentStep = currentStep.value)
}

@Preview
@Composable
fun RecommendScreenPreview() {
    val navController = rememberNavController()
    RecommendScreen(navController = navController)
}