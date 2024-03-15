package com.ssafy.d101.ui.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.theme.White
import com.ssafy.d101.ui.view.components.BackHeader

@Composable
fun BMIScreen(navController: NavController) {
    Column( modifier = Modifier // 백그라운드
        .fillMaxSize()
        .background(Ivory)
    ) {
         BackHeader("BMI 측정", navController)
        CheckBMI()
    }
}

@Composable
fun CheckBMI() {

    var selectedGender by remember { mutableStateOf("male") }

    Column ( modifier = Modifier
        .fillMaxWidth()
        .padding(30.dp, 30.dp, 30.dp, 30.dp)
        .shadow(15.dp, RoundedCornerShape(12.dp))
        .background(White, shape = RoundedCornerShape(12.dp))
    ){
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) { // 키
            Box(modifier = Modifier.padding(30.dp,10.dp,0.dp,0.dp).size(100.dp,30.dp)) {
                Text(text = "키",style = textStyle
                )}
            OutlinedTextField(
                modifier = Modifier.size(130.dp,30.dp),
                value = "",
                onValueChange = {},
                shape = MaterialTheme.shapes.small.copy(
                    CornerSize(percent = 50),
                )
            )
            Box(modifier = Modifier.padding(20.dp,0.dp,0.dp,0.dp)){Text(text = "cm",style = textStyle)}
        }
        Row(modifier = Modifier.padding(10.dp), verticalAlignment = Alignment.CenterVertically) { // 키
            Box(modifier = Modifier.padding(30.dp,10.dp,0.dp,0.dp).size(100.dp,30.dp)){Text(text = "몸무게",style = textStyle)}
            OutlinedTextField(
                modifier = Modifier.size(130.dp,30.dp),
                value = "",
                onValueChange = {},
                shape = MaterialTheme.shapes.small.copy(
                    CornerSize(percent = 50),
                )
            )
            Box(modifier = Modifier.padding(20.dp,0.dp,0.dp,0.dp)){Text(text = "kg",style = textStyle)}
        }
        Row(modifier = Modifier.padding(10.dp,0.dp), verticalAlignment = Alignment.CenterVertically) { // 성별
            Box(modifier = Modifier.padding(30.dp,8.dp,0.dp,0.dp).size(100.dp,30.dp)){Text(text = "성별",style = textStyle)}
            Text(text = "남",style = textStyle)
            RadioButton(selected = selectedGender =="male", onClick = { /*TODO*/ })
            Text(text = "여",style = textStyle)
            RadioButton(selected = selectedGender =="female", onClick = { /*TODO*/ })
        }
        Row(
            modifier = Modifier
                .fillMaxWidth().padding(0.dp,0.dp,0.dp,20.dp), // Row가 부모의 최대 너비를 채우도록 설정
            horizontalArrangement = Arrangement.Center // 버튼을 중앙에 배치
        ) {
            Button(
                onClick = { /* TODO: BMI 계산 및 결과 처리 */ },
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
    }
}

val textStyle = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 15.sp,
    color = Color.Black // 색상도 설정할 수 있음
)