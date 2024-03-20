package com.ssafy.d101.ui.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class BarItem(
    val days: List<String> = listOf("Jan", "Feb", "March", "April", "May", "June", "July", "Aug", "Sep", "Oct", "Nov", "Dec"),
    val data: List<List<Float>>, // Nested list where each list contains [achieved, goal, expense] for a day
    val colors: List<Color> = listOf(Color(0xFFA62D2D), Color(0xFFF28888), Color(0xFFF2C9C9)),
    val maxValue: Float = 2000f // Maximum value to scale the bars
)

@Composable
fun HorizontalBarChart(
    weeklyData: BarItem,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .padding(8.dp)
            .background(Color(0xFF547D75))
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val chartWidth = size.width - 90.dp.toPx() // 가로 길이 조정
            val barHeight = (size.height - 120.dp.toPx()) / weeklyData.days.size.toFloat() // 바의 높이 계산
            val spaceBetweenBars = 8.dp.toPx() // 바 사이 간격
            val guideLineColor = Color.LightGray // 가이드라인 및 축 색상

            val offsetBars = spaceBetweenBars * 2

            // y축 가이드라인 및 숫자 라벨 그리기
            val maxValue = weeklyData.maxValue
            val guideLineSteps = listOf((maxValue / 4).toInt(), (maxValue * 2 / 4).toInt(), (maxValue * 3 / 4).toInt(), (maxValue * 4 / 4).toInt())

            guideLineSteps.forEach { step ->
                val xPosition = (step / maxValue) * chartWidth
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x = xPosition + 60.dp.toPx(), y = offsetBars - 10.dp.toPx()),
                    end = Offset(x = xPosition + 60.dp.toPx(), y = size.height - 30.dp.toPx()),
                    strokeWidth = 1.dp.toPx()
                )

                // x축 숫자 라벨 그리기
                val textWidth = android.graphics.Paint().apply {
                    textSize = 20.sp.toPx()
                }.measureText(step.toString())
                drawContext.canvas.nativeCanvas.drawText(
                    step.toString(),
                    xPosition + 75.dp.toPx(),
                    size.height - 5.dp.toPx(),
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.WHITE
                        textSize = 15.sp.toPx()
                        textAlign = android.graphics.Paint.Align.RIGHT
                    }
                )
            }


            weeklyData.days.forEachIndexed { index, day ->
                val barY = barHeight * index + spaceBetweenBars * index
                var startX = 0.dp.toPx()
                weeklyData.data[index].forEachIndexed { valueIndex, value ->
                    val barWidth = (value / weeklyData.maxValue) * chartWidth

                    drawRoundRect(
                        color = weeklyData.colors[valueIndex],
                        topLeft = Offset(x = startX + 60.dp.toPx(), y = barY),
                        size = Size(barWidth, barHeight - spaceBetweenBars - 10),
                        cornerRadius = CornerRadius(4.dp.toPx())
                    )
                    startX += barWidth
                }

                // 바에 해당하는 라벨 그리기
                drawContext.canvas.nativeCanvas.drawText(
                    day,
                    45.dp.toPx(), // 텍스트 위치 조정
                    barY + barHeight / 2- 0.dp.toPx(),
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.WHITE
                        textSize = 20.sp.toPx()
                        textAlign = android.graphics.Paint.Align.RIGHT
                    }
                )
            }

            // x축과 y축 그리기
            drawLine(
                color = guideLineColor,
                start = Offset(x = 0.dp.toPx(), y = size.height - 80),
                end = Offset(x = size.width, y = size.height - 80),
                strokeWidth = 2.dp.toPx() // x축 선의 굵기
            )
            drawLine(
                color = Color.LightGray,
                start = Offset(x = 60.dp.toPx(), y = 0f),
                end = Offset(x = 60.dp.toPx(), y = size.height),
                strokeWidth = 2.dp.toPx()  //y축
            )
        }
    }
}

@Composable
fun MonthlyNutritionChartHorizontal(
    weeklyData: BarItem,
    title: String,
    colors: List<Color> = listOf(Color(0xFFA62D2D), Color(0xFFF28888), Color(0xFFF2C9C9)),
    nutritionNames: List<String> = listOf("탄수화물", "단백질", "지방")
) {
    Column(
        modifier = Modifier
            .padding(0.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF547D75))
            .fillMaxWidth()
            .fillMaxHeight()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Spacer(Modifier.weight(1f))
            Text(
                text = title,
                fontSize = 25.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .weight(2f)
                    .wrapContentWidth(Alignment.CenterHorizontally)
            )
            Spacer(Modifier.weight(0.4f))

            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.Start
            ) {
                colors.zip(nutritionNames).forEach { (color, name) ->
                    Row(modifier = Modifier.padding(end = 8.dp)) {
                        Box(
                            modifier = Modifier
                                .size(15.dp)
                                .background(color)
                        )
                        Text(
                            text = " $name",
                            color = Color.White,
                            fontSize = 14.sp
                        )
                    }
                }
            }
        }

        // 가로 바 차트 호출
        HorizontalBarChart(
            weeklyData = weeklyData,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(16.dp)
        )
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewMonthlyHorizontalBarChart() {
    MonthlyNutritionChartHorizontal(
        weeklyData = BarItem(
            data = listOf(
                listOf(600f, 400f, 200f),
                listOf(500f, 300f, 200f),
                listOf(700f, 300f, 300f),
                listOf(800f, 200f, 400f),
                listOf(450f, 550f, 250f),
                listOf(400f, 600f, 400f),
                listOf(500f, 500f, 500f),
                listOf(700f, 300f, 300f),
                listOf(800f, 200f, 400f),
                listOf(450f, 550f, 250f),
                listOf(400f, 600f, 400f),
                listOf(500f, 500f, 500f)
            ),
            maxValue = 2000f
        ),
        title = "MONTHLY"
    )
}
