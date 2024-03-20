package com.ssafy.d101.ui.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.theme.White
import com.ssafy.d101.ui.view.components.BackHeader

@Composable
fun UserInfoScreen(navController: NavController) {
    Column( modifier = Modifier // 백그라운드
        .fillMaxSize()
        .background(Ivory)
    ) {
        BackHeader("마이페이지", navController)
        UserInfo()
    }
}

@Composable
fun UserInfo() {
    var height by remember { mutableStateOf("") }
    var weight by remember { mutableStateOf("") }
    var activity by remember { mutableIntStateOf(0) }
    var kcal by remember { mutableStateOf("") }
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .padding(30.dp, 30.dp, 30.dp, 30.dp)
            .shadow(15.dp, RoundedCornerShape(12.dp))
            .background(White, shape = RoundedCornerShape(12.dp))
    ){
        Row (modifier = Modifier
            .fillMaxWidth()
            .padding(20.dp, 20.dp, 20.dp, 0.dp)){
            Box(modifier=Modifier.weight(1f)) {
                Text(
                    text = "내 정보", style = TextStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 23.sp,
                        shadow = Shadow(
                            color = Color.Gray,
                            offset = Offset(10f, 10f),
                            blurRadius = 8f
                        )
                    )
                )
            }
            Button(
                onClick = {/*TODO*/ },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFB6B284), // 버튼 배경색
                    contentColor = Color.White // 버튼 텍스트색
                ),
                // 버튼의 크기를 설정
                modifier = Modifier.size(width = 80.dp, height = 35.dp)
            ) {
                Text(
                    text = "저장", style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 15.sp,
                        color = Color.White // 색상도 설정할 수 있음
                    )
                )
            }

        }
        Divider(modifier = Modifier.padding(20.dp,10.dp,20.dp,0.dp), color = Color.Gray, thickness = 1.dp)

        Row(){
            Column(modifier= Modifier
                .weight(1f)
                .padding(0.dp, 20.dp, 0.dp, 0.dp)) {
                InfoText("이름", "장민숭")
                InfoText("이메일", "jang@naver.com")
            }
            Column(modifier = Modifier.padding(0.dp,0.dp,20.dp,0.dp)) {
                AsyncImage(
                    model = "https://d101-bucket.s3.ap-northeast-2.amazonaws.com/diet/%EB%96%A1%EB%B3%B6%EC%9D%B4.jpg",
                    contentDescription = "profileImage",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(90.dp)
                )
            }
        }
        InfoText(title = "성별", content = "남")
        InfoText(title = "나이", content = "30세")
        InfoInputField(title = "키", unit = "cm")
        InfoInputField(title = "몸무게", unit = "kg")
        InfoDropDownList(title = "활동량")
        Row (modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)){// 목표 칼로리
            Box(modifier=Modifier.width(90.dp)){Text(text = "목표 칼로리", style = titleTextStyle)}
            InputField()
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                onClick = {
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black, // 버튼 배경색
                    contentColor = Color.White // 버튼 텍스트색
                ),
                // 버튼의 크기를 설정
                modifier = Modifier.size(width = 100.dp, height = 30.dp)
            ) {
                Text(text = "추천 칼로리",style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 10.sp,
                    color = Color.White))
            }
        }
        Spacer(modifier = Modifier.size(20.dp))
    }
}
@Composable
fun InfoDropDownList(title: String) {
    val items = listOf("운동을 거의 하지 않음", "주 1~3회 운동을 함", "주 3~5회 운동을 함", "주 6~7회 운동을 함","매일 운동을 함")
    var selectedIndex by remember { mutableIntStateOf(0) }
    Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)) {
        Box(modifier=Modifier.width(80.dp)){Text(text = title, style = titleTextStyle)}
        Spacer(modifier = Modifier.width(10.dp))
        DropdownList(itemList = items, selectedIndex = selectedIndex, modifier = Modifier.width(200.dp), onItemClick = {selectedIndex = it})
    }

}

@Composable
fun InfoText(title: String, content: String) {
    Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)) {
        Box(modifier=Modifier.width(80.dp)){Text(text = title, style = titleTextStyle)}
        Spacer(modifier = Modifier.width(10.dp))
        Text(text = content)
    }
}

@Composable
fun InfoInputField(title: String, unit: String) {
    Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)) {
        Box(modifier=Modifier.width(80.dp)){Text(text = title, style = titleTextStyle)}
        Spacer(modifier = Modifier.width(10.dp))
        InputField()
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = unit, style = titleTextStyle)
    }
}

@Composable
fun InputField() {
    var textState by remember { mutableStateOf(TextFieldValue("183")) }
    BasicTextField(
        value = textState,
        onValueChange = { textState = it },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .width(70.dp)
                    .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.padding(horizontal = 5.dp)){innerTextField()}
            }
        }
    )
}

@Preview
@Composable
fun UserInfoPreview() {
    val navController = rememberNavController()
    UserInfoScreen(navController = navController)
}

val titleTextStyle = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    color = Color.Black // 색상도 설정할 수 있음
)