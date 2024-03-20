package com.ssafy.d101.ui.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NutritionLegend(carbsPercentage: Int, proteinPercentage: Int, fatsPercentage: Int) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // 탄수화물 레전드
        LegendItem(percentage = carbsPercentage, color = Color(0xFFF27272), label = "탄수화물")
        Spacer(modifier = Modifier.width(8.dp))
        // 단백질 레전드
        LegendItem(percentage = proteinPercentage, color = Color(0xFFC3B6F2), label = "단백질")
        Spacer(modifier = Modifier.width(8.dp))
        // 지방 레전드
        LegendItem(percentage = fatsPercentage, color = Color(0xFFF2C46D), label = "지방")
    }
}

@Composable
fun LegendItem(percentage: Int, color: Color, label: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        // 색상 원
        Canvas(modifier = Modifier
            .size(10.dp),
            onDraw = {
                drawOval(color, size = Size(this.size.width, this.size.height))
            })
        Spacer(modifier = Modifier.width(4.dp))
        // 레이블 및 비율
        Text(text = "$label $percentage%", fontSize = 12.sp)
    }
}

@Composable
fun NutritionBar(percentage: Float, color: Color) {
    Box(modifier = Modifier
        .height(20.dp)
        .width((percentage * 2).dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawRoundRect(
                color = color,
                size = Rect(Offset.Zero, size).size,
                cornerRadius = CornerRadius(4.dp.toPx())
            )
        }
    }
}

@Composable
fun DailyHorizontalBar(carbsPercentage: Float, proteinPercentage: Float, fatsPercentage: Float) {
    Column(modifier = Modifier.padding(10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
        ) {
        NutritionLegend(carbsPercentage.toInt(), proteinPercentage.toInt(), fatsPercentage.toInt())
        Spacer(modifier = Modifier.height(8.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            NutritionBar(percentage = carbsPercentage, color = Color(0xFFF27272))
            Spacer(modifier = Modifier.width(4.dp))
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .height(20.dp)
                    .width(3.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            NutritionBar(percentage = proteinPercentage, color = Color(0xFFC3B6F2))
            Spacer(modifier = Modifier.width(4.dp))
            Divider(
                color = Color.Black,
                modifier = Modifier
                    .height(20.dp)
                    .width(3.dp)
            )
            Spacer(modifier = Modifier.width(4.dp))
            NutritionBar(percentage = fatsPercentage, color = Color(0xFFF2C46D))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDailyHorizontalBar() {
    DailyHorizontalBar(carbsPercentage = 20f, proteinPercentage = 40f, fatsPercentage = 40f)
}
