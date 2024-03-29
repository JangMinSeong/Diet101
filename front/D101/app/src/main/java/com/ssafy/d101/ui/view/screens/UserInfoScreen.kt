package com.ssafy.d101.ui.view.screens

import android.annotation.SuppressLint
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.theme.White
import com.ssafy.d101.ui.view.components.BackHeader
import com.ssafy.d101.viewmodel.UserViewModel

@Composable
fun UserInfoScreen(navController: NavController) {
    val userViewModel: UserViewModel = hiltViewModel()
    Column( modifier = Modifier // 백그라운드
        .fillMaxSize()
        .background(Ivory)
    ) {
        BackHeader("마이페이지", navController)
        UserInfo(userViewModel)
    }
}

@SuppressLint("StateFlowValueCalledInComposition")
@Composable
fun UserInfo(userViewModel: UserViewModel) {
    val user by userViewModel.getUser().collectAsState(initial = null)
    val userInfo = user?.userInfo
    val userSubInfo = user?.userSubInfo
    var height = userSubInfo?.height.toString()
    var weight = userSubInfo?.weight.toString()
    var activity by remember { mutableIntStateOf(0) }
    var kcal = userSubInfo?.calorie.toString()
    val age = userInfo?.age ?: 0

    fun calculateCalories(height: Int, weight: Int): Int {
        val bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age)
        val multiplier = when(activity) {
            0 -> 1.2
            1 -> 1.375
            2 -> 1.55
            3 -> 1.725
            else -> 1.9
        }
        return (bmr * multiplier).toInt()
    }
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
                InfoText("이름", userInfo?.username ?: "name")
                InfoText(title = "성별", content = userInfo?.gender ?: "gender")
            }
            Column(modifier = Modifier.padding(0.dp,0.dp,20.dp,0.dp)) {
                AsyncImage(
                    model = userInfo?.image ?: "https://d101-bucket.s3.ap-northeast-2.amazonaws.com/diet/%EB%96%A1%EB%B3%B6%EC%9D%B4.jpg",
                    contentDescription = "profileImage",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier.size(90.dp)
                )
            }
        }
        InfoText(title = "나이", content = age.toString()+"세")
        InfoText("이메일", userInfo?.email ?: "null@naver.com")
        InfoInputField(title = "키", unit = "cm", value = height, onValueChange = { new -> height = new.filter { it.isDigit() } })
        InfoInputField(title = "몸무게", unit = "kg", value = weight, onValueChange = { new -> weight = new.filter { it.isDigit() } })
        InfoDropDownList(title = "활동량", activityIndex = activity, onItemClick = {activity = it})
        Row (modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)){// 목표 칼로리
            Box(modifier=Modifier.width(90.dp)){Text(text = "목표 칼로리", style = titleTextStyle)}
            InputField(kcal, onValueChange = { new -> kcal = new.filter { it.isDigit() } })
            Spacer(modifier = Modifier.width(5.dp))
            Button(
                onClick = {
                          kcal = calculateCalories(height.toInt(),weight.toInt()).toString()
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
fun InfoDropDownList(title: String, activityIndex: Int, onItemClick: (Int) -> Unit) {
    val items = listOf("운동을 거의 하지 않음", "주 1~3회 운동을 함", "주 3~5회 운동을 함", "주 6~7회 운동을 함","매일 운동을 함")
    Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)) {
        Box(modifier=Modifier.width(80.dp)){Text(text = title, style = titleTextStyle)}
        Spacer(modifier = Modifier.width(10.dp))
        DropdownList(itemList = items, selectedIndex = activityIndex, modifier = Modifier.width(200.dp), onItemClick = onItemClick)
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
fun InfoInputField(title: String, unit: String, value: String, onValueChange: (String) -> Unit) {
    Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)) {
        Box(modifier=Modifier.width(80.dp)){Text(text = title, style = titleTextStyle)}
        Spacer(modifier = Modifier.width(10.dp))
        InputField(value = value, onValueChange = onValueChange)
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = unit, style = titleTextStyle)
    }
}

@Composable
fun InputField(value: String, onValueChange: (String) -> Unit) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
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

@Preview(showBackground = true)
@Composable
fun UserInfoPreview() {
    val navController = rememberNavController()
    val userViewModel: UserViewModel = hiltViewModel()

    UserInfoScreen(navController = navController)
}

val titleTextStyle = TextStyle(
    fontWeight = FontWeight.Bold,
    fontSize = 18.sp,
    color = Color.Black // 색상도 설정할 수 있음
)