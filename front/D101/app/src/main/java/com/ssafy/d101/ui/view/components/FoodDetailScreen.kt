package com.ssafy.d101.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.d101.R
import com.ssafy.d101.model.AnalysisDiet

@Composable
fun FoodDetailScreen(carbsRatio: Float, proteinRatio: Float, fatRatio: Float) {

    Row(
        modifier = Modifier
            .width(500.dp)
            .height(330.dp)
    ) {
        // 왼쪽 이미지
        Image(
            painter = painterResource(id = R.drawable.image3),
            contentDescription = "식단 사진",
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
                .clip(RoundedCornerShape(8.dp))
        )

        Column(
            modifier = Modifier
                .weight(2f)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "아침",
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = "등갈비 김치찜 200g",
                fontSize = 16.sp
            )

            // DailyHorizontalBar 그래프
            DailyHorizontalBar2(
                carbsPercentage = carbsRatio,
                proteinPercentage = proteinRatio,
                fatsPercentage = fatRatio
            )

            // 탄단지 정보표
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                color = MaterialTheme.colorScheme.surfaceVariant
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    // 탄수화물
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "탄수화물",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            "25.5g",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            "102kcal",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Divider(color = Color.Black, modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp))

                    // 단백질
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "단백질",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            "20g",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            "128cal",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                    Divider(color = Color.Black, modifier = Modifier
                        .fillMaxHeight()
                        .width(1.dp))

                    // 지방
                    Column(
                        modifier = Modifier.weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            "지방",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            "10g",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            "197kcal",
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun DailyHorizontalBar2(carbsPercentage: Float, proteinPercentage: Float, fatsPercentage: Float) {
    Column(modifier = Modifier.padding(10.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        NutritionLegend(carbsPercentage.toInt(), proteinPercentage.toInt(), fatsPercentage.toInt())
        Spacer(modifier = Modifier.height(8.dp))
        Row (
            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Center
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


@Preview(showBackground = true, widthDp = 500, heightDp = 200)
@Composable
fun FoodDetailScreenPreview() {
    FoodDetailScreen(
        carbsRatio = 30f,
        proteinRatio = 30f,
        fatRatio = 40f
    )
}