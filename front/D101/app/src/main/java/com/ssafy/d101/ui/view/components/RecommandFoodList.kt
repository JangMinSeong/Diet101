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
    LazyColumn {
        items(foodInfos) { foodInfo ->
            FoodItemCard(foodInfo = foodInfo)
        }
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