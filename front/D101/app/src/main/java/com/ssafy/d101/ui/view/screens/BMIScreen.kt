package com.ssafy.d101.ui.view.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.ssafy.d101.ui.theme.Green
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.theme.White
import com.ssafy.d101.ui.view.components.BackHeader
import com.ssafy.d101.viewmodel.UserViewModel


@Composable
fun BMIScreen(navController: NavHostController) {
    val viewModel: UserViewModel = hiltViewModel()
    val username by viewModel.username.observeAsState("")
    Column( modifier = Modifier // 백그라운드
        .fillMaxSize()
        .background(Ivory)
    ) {
         BackHeader("BMI 측정", navController)
        CheckBMI(username = username)
    }
}

@Preview(showBackground = true)
@Composable
fun CheckBMI(username: String) {

    var gender by remember { mutableStateOf("male") }
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var bmiResult by remember { mutableStateOf<String?>(null) }
    var bmiCategory by remember { mutableStateOf<String?>(null) }

    fun calculateBMI(): Pair<Double, String> {
        val heightInMeters = height.toDouble() / 100.0
        val weightInKg = weight.toDouble()
        if(heightInMeters<=0.0||weightInKg<=0.0) {
            // 예외처리
            return Pair(0.0, "유효한 값이 아님")
        }
        val bmi = weightInKg / (heightInMeters * heightInMeters)
        val category = when {
            bmi < 18.5 -> "저체중"
            bmi < 25 -> "정상"
            bmi < 30 -> "과체중"
            else -> "비만"
        }
        return Pair(bmi, category)
    }

    Column ( modifier = Modifier
        .fillMaxWidth()
        .padding(30.dp, 30.dp, 30.dp, 30.dp)
        .shadow(15.dp, RoundedCornerShape(12.dp))
        .background(White, shape = RoundedCornerShape(12.dp))
    ){
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) { // 키
            Box(modifier = Modifier
                .padding(30.dp, 10.dp, 0.dp, 0.dp)
                .size(100.dp, 30.dp)) {
                Text(text = "키",style = textStyle
                )}
            InputField(value = height, onValueChange = { height = it })
            Box(modifier = Modifier.padding(20.dp,0.dp,0.dp,0.dp)){Text(text = "cm",style = textStyle)}
        }
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) { // 키
            Box(modifier = Modifier
                .padding(30.dp, 10.dp, 0.dp, 0.dp)
                .size(100.dp, 30.dp)){Text(text = "몸무게",style = textStyle)}
            InputField(value = weight, onValueChange = { weight = it })
            Box(modifier = Modifier.padding(20.dp,0.dp,0.dp,0.dp)){Text(text = "kg",style = textStyle)}
        }
        Row(modifier = Modifier.padding(10.dp,0.dp), verticalAlignment = Alignment.CenterVertically) { // 성별
            Box(modifier = Modifier
                .padding(30.dp, 8.dp, 0.dp, 0.dp)
                .size(100.dp, 30.dp)){Text(text = "성별",style = textStyle)}
            Text(text = "남",style = textStyle)
            RadioButton(selected = gender =="male", onClick = { gender = "male" })
            Text(text = "여",style = textStyle)
            RadioButton(selected = gender =="female", onClick = { gender = "female"  })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(0.dp, 0.dp, 0.dp, 20.dp), // Row가 부모의 최대 너비를 채우도록 설정
            horizontalArrangement = Arrangement.Center // 버튼을 중앙에 배치
        ) {
            Button(
                onClick = {
                    val (bmi, category) = calculateBMI()
                    bmiResult = if (bmi > 0) String.format("%.1f", bmi) else null
                    bmiCategory = category
                    Log.d("bmi",bmiResult.toString())
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black, // 버튼 배경색
                    contentColor = Color.White // 버튼 텍스트색
                ),
                // 버튼의 크기를 설정
                modifier = Modifier.size(width = 100.dp, height = 35.dp)
            ) {
                Text(text = "측정하기")
            }
        }
        if (bmiResult != null && bmiCategory != null) {
            Row(modifier = Modifier.padding(20.dp, 0.dp, 20.dp, 20.dp)) {// 측정 결과
                Box(
                    modifier = Modifier
                        .background(Green, shape = RoundedCornerShape(12.dp))
                        .padding(10.dp, 20.dp)
                ) {
                    var text = buildAnnotatedString {
                        append("${username}님의 신체 질량 지수(BMI)는")
                        withStyle(style = SpanStyle(color = Color.Red, fontSize = 18.sp)) {
                            append(bmiResult!!)
                        }
                        append(" (으)로 ")
                        withStyle(style = SpanStyle(color = Color(0xFFEEFF29), fontSize = 18.sp)) {
                            append(bmiCategory)
                        }
                        append("입니다.")
                    }
                    Text(
                        text = text,
                        fontSize = 16.sp,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}

val textStyle = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 15.sp,
    color = Color.Black // 색상도 설정할 수 있음
)