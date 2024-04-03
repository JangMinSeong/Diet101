package com.ssafy.d101.ui.view.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.d101.R

@Composable
fun CustomSemiCirclePieChart(consumedKcal: Int, totalKcal: Int, gender : Int) {
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.size(200.dp)) {
            val canvasWidth = size.width * 2
            val canvasHeight = size.height

            // Clip the drawing area to half circle
            clipRect(
                top = 0.dp.toPx(),
                left = -30.dp.toPx(),
                right = canvasWidth,
                bottom = canvasHeight
            ) {
                val strokeWidth = 130f
                val radius = size.minDimension / 2
                val center = Offset(x = size.width / 2, y = size.height)

                // Draw total arc
                drawArc(
                    color = Color.LightGray,
                    startAngle = 180f,
                    sweepAngle = 180f,
                    useCenter = false,
                    topLeft = Offset(x = center.x - radius, y = center.y - radius - 100f),
                    size = size,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth)
                )

                // Draw consumed arc
                var consumedPercentage = (consumedKcal.toFloat() / totalKcal) * 180f
                if(totalKcal < consumedKcal.toFloat()) consumedPercentage = 180f
                drawArc(
                    color = Color.Cyan,
                    startAngle = 180f,
                    sweepAngle = consumedPercentage,
                    useCenter = false,
                    topLeft = Offset(x = center.x - radius, y = center.y - radius - 100f),
                    size = size,
                    style = androidx.compose.ui.graphics.drawscope.Stroke(strokeWidth)
                )
            }
        }

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Row() {
                Text(
                    text = "${consumedKcal} /",
                    fontSize = 20.sp,
                    color = if (consumedKcal > totalKcal) Color.Red else Color.Black
                )
                Text(" ${totalKcal} kcal", fontSize = 20.sp, color = Color.Blue)
            }

            val charImage = when (gender) {
                1 -> R.drawable.male // 남자
                2 -> R.drawable.female // 여자
                else -> R.drawable.male // 기본값
            }
            Spacer(modifier = Modifier.height(30.dp))

            Image(
                modifier = Modifier.size(110.dp),
                painter = painterResource(id = charImage),
                contentDescription = "캐릭터" // 접근성을 위한 이미지 설명
            )
            Spacer(modifier = Modifier.height(10.dp))
            Row() {
                val remainingKcal = totalKcal - consumedKcal
                Row() {
                    Text(text = if(remainingKcal < 0) "${remainingKcal*(-1)} kcal " else "${remainingKcal} kcal ", fontSize = 14.sp, color=Color.Magenta)
                    Text(text = if(remainingKcal < 0) "더 먹었어요" else "남았어요", fontSize = 14.sp, color = Color.Black)
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun PreviewCustomSemiCirclePieChart() {
    CustomSemiCirclePieChart(consumedKcal = 1800, totalKcal = 1500, gender = 1)
}
