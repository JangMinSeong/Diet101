package com.ssafy.d101.ui.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

data class StackedBarItem(
    val days: List<String> = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN"),
    val data: List<List<Float>>, // Nested list where each list contains [achieved, goal, expense] for a day
    val colors: List<Color> = listOf(Color(0xFFA62D2D), Color(0xFFF28888), Color(0xFFF2C9C9)),
    val maxValue: Float = 2000f // Maximum value to scale the bars
)

@Composable
fun VerticalBarChartWithGuidelineNumbers(
    weeklyData: StackedBarItem,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val chartWidth = size.width
            val chartHeight = size.height - 32.dp.toPx() // x축 라벨을 위한 공간 확보
            val barWidth = chartWidth / (weeklyData.days.size * 2f) // 바 간격
            val spaceBetweenBars = barWidth / 1.2f // 바 사이 간격
            val offset = barWidth / 2f // 바를 오른쪽으로 이동시킬 오프셋
            val guideLineColor = Color.LightGray // 가이드라인 및 축 색상
            val offsetBars = spaceBetweenBars * 2 // 첫 번째 바와 y축 사이의 간격 조정

            // y축 가이드라인 및 숫자 라벨 그리기
            val maxValue = weeklyData.maxValue
            val guideLineSteps = listOf(500, 1000, 1500, 2000)

            guideLineSteps.forEach { step ->
                val yPosition = chartHeight - (step / maxValue) * chartHeight
                drawLine(
                    color = Color.LightGray,
                    start = Offset(x = offsetBars - 50f, y = yPosition), // y축 위치에서 시작
                    end = Offset(x = chartWidth, y = yPosition),
                    strokeWidth = 1.dp.toPx()
                )

                // y축 숫자 라벨 그리기
                val textWidth = android.graphics.Paint().apply {
                    textSize = 12.sp.toPx()
                }.measureText(step.toString())
                drawContext.canvas.nativeCanvas.drawText(
                    step.toString(),
                    textWidth - 15.dp.toPx(), // 왼쪽으로 이동시켜 겹치지 않게 함
                    yPosition + 12.sp.toPx() / 2, // y축 가이드라인에 맞춤
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.WHITE
                        textSize = 12.sp.toPx()
                        textAlign = android.graphics.Paint.Align.RIGHT
                    }
                )
            }

            // x축과 y축 그리기
            drawLine(
                color = guideLineColor,
                start = Offset(x = -50f, y = chartHeight),
                end = Offset(x = chartWidth, y = chartHeight),
                strokeWidth = 2.dp.toPx() // x축 선의 굵기
            )
            drawLine(
                color = Color.LightGray,
                start = Offset(x = offsetBars - 50f, y = 0f),
                end = Offset(x = offsetBars - 50f, y = chartHeight + 80f),
                strokeWidth = 2.dp.toPx()
            )

            // 바 및 x축 날짜 라벨 그리기
            weeklyData.days.forEachIndexed { index, day ->
                val startX = (spaceBetweenBars + (barWidth + spaceBetweenBars) * index) + offset
                val barData = weeklyData.data[index]
                var startY = chartHeight

                barData.forEachIndexed { valueIndex, value ->
                    val barHeight = (value / maxValue) * chartHeight
                    drawRoundRect(
                        color = weeklyData.colors[valueIndex],
                        topLeft = Offset(x = startX, y = startY - barHeight),
                        size = Size(width = barWidth, height = barHeight),
                        cornerRadius = CornerRadius(4.dp.toPx())
                    )
                    startY -= barHeight
                }

                // x축 날짜 라벨 그리기
                drawContext.canvas.nativeCanvas.drawText(
                    day,
                    startX + barWidth / 2,
                    chartHeight + 16.dp.toPx(), // x축 라벨을 위한 공간
                    android.graphics.Paint().apply {
                        color = android.graphics.Color.WHITE
                        textSize = 12.sp.toPx()
                        textAlign = android.graphics.Paint.Align.CENTER
                    }
                )
            }
        }
    }
}

@Composable
fun WeeklyNutritionChart(
    weeklyData: StackedBarItem,
    title: String,
    colors: List<Color> = listOf(Color(0xFFA62D2D), Color(0xFFF28888), Color(0xFFF2C9C9)),
    nutritionNames: List<String> = listOf("지", "당", "탄")

) {
    Column(modifier = Modifier
        .padding(0.dp)
        .background(Color(0xFF547D75))) {

        Row(
            modifier = Modifier
                .fillMaxWidth() // Row를 최대 너비로 확장
                .padding(top=8.dp),
            horizontalArrangement = Arrangement.SpaceBetween, // 내용을 양 끝으로 분산
            verticalAlignment = Alignment.CenterVertically // 세로 중앙 정렬
        ) {
            // 제목 (가운데 정렬을 위한 Spacer 사용)
            Spacer(Modifier.weight(1.3f))
            Text(
                text = title,
                fontSize = 25.sp, // 제목 크기 조정
                fontWeight = FontWeight.Bold,
                color = Color.White,
                modifier = Modifier
                    .weight(1f) // 가용 공간에서 1의 비율을 차지
                    .wrapContentWidth(Alignment.CenterHorizontally) // 텍스트를 가운데 정렬
            )
            Spacer(Modifier.weight(1f))

            // 색상 레전드 (우측 정렬을 위해 가중치 사용하지 않음)
            Column(
                modifier = Modifier,
                horizontalAlignment = Alignment.End // Column 내용을 우측 정렬
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
        // 차트
        VerticalBarChartWithGuidelineNumbers(
            weeklyData = weeklyData,
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
                .padding(18.dp)
                .background(Color(0xFF547D75))
        )
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewWeeklyStackedBarChart() {
    WeeklyNutritionChart(
        weeklyData = StackedBarItem(
            data = listOf(
                listOf(600f, 400f, 200f),
                listOf(500f, 300f, 200f),
                listOf(700f, 300f, 300f), // WED
                listOf(800f, 200f, 400f), // THU
                listOf(450f, 550f, 250f), // FRI
                listOf(400f, 600f, 400f), // SAT
                listOf(500f, 500f, 500f)  // SUN
            )
        ),
        title = "WEEKLY"
    )
}