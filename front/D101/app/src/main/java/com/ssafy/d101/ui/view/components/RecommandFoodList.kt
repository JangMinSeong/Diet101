package com.ssafy.d101.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.d101.ui.theme.Ivory

@Composable
fun FoodList(foodInfos: List<FoodInfo>) {
    Column(modifier = Modifier.background(Ivory)) {
        Row() {
            Spacer(modifier = Modifier.padding(15.dp))
            Text(
                text = "추천된 음식을 확인하고\n식단에 추가해 보세요",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 20.sp)
            ) // 글자 굵게
        }
        Spacer(modifier = Modifier.padding(10.dp))
        LazyColumn {
            items(foodInfos) { foodInfo ->
                FoodItemCard(foodInfo = foodInfo)
            }
        }
        Spacer(modifier = Modifier.padding(10.dp))
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            CustomButton() {

            }
        }
    }
}

@Composable
fun CustomButton(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        // Button의 배경색 설정
        colors = ButtonDefaults.buttonColors(Color(0xFFA8A077)),
        // Button의 모양 설정: 둥근 모서리
        shape = RoundedCornerShape(50), // 모서리의 둥근 정도 조절
        modifier = Modifier
            .width(100.dp) // 가로 크기 조절
            .height(50.dp) // 세로 크기 조절
    ) {
        Text("다음", color = Color.White) // 텍스트 색상 설정
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewFoodList() {
    FoodList(foodInfos = listOf(
        FoodInfo("등갈비 김치찜", 200, 25.5f, 32.0f,21.9f,427.1f),
        FoodInfo("공깃밥",1,31.7f,2.7f,0.3f,140.3f),
        FoodInfo("모코코",10,31.7f,2.7f,0.3f,140.3f)
        ))
}