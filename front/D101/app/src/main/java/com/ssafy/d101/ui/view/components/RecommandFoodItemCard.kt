package com.ssafy.d101.ui.view.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.d101.model.FoodInfo


@Composable
fun FoodItemCard(foodInfo: FoodInfo) {
    Card(modifier = Modifier.run { padding(10.dp).shadow(6.dp, RoundedCornerShape(12.dp)) }) {
        Column(
            modifier = Modifier.padding(0.dp).background(Color(0xFFEBE9D5)),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.padding(top = 5.dp)) {
                Text(text = "${foodInfo.name}, ${foodInfo.totalSize} g")
                Spacer(modifier = Modifier.width(40.dp))
                Text(text = "칼로리: ${foodInfo.calorie} kcal")
            }
            var carbsPer = ((foodInfo.carbohydrate * 4) / foodInfo.calorie) * 100
            var protePer = ((foodInfo.protein * 4) / foodInfo.calorie) * 100
            var fatPer = ((foodInfo.fat * 9) / foodInfo.calorie) * 100

            val formattedCarbsPer = String.format("%.1f", carbsPer)
            val formattedProtePer = String.format("%.1f", protePer)
            val formattedFatPer = String.format("%.1f", fatPer)

            DailyHorizontalBar(
                carbsPercentage = formattedCarbsPer.toFloat(),
                proteinPercentage = formattedProtePer.toFloat(),
                fatsPercentage = formattedFatPer.toFloat()
            )

            Row(modifier = Modifier.padding(top = 8.dp, bottom = 8.dp).border(1.dp, Color.Gray, RoundedCornerShape(4.dp))) {
                Column(modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally ){
                    Text(text = "탄수화물")
                    Text(text = "${foodInfo.carbohydrate} g")
                    Text(text = "${String.format("%.1f",foodInfo.carbohydrate*4)} kcal")
                }
                VerticalDivider()
                Column(modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally ){
                    Text(text = "단백질")
                    Text(text = "${foodInfo.protein} g")
                    Text(text = "${String.format("%.1f",foodInfo.protein*4)} kcal")
                }
                VerticalDivider()
                Column(modifier = Modifier.padding(8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally ){
                    Text(text = "지방")
                    Text(text = "${foodInfo.fat} g")
                    Text(text = "${String.format("%.1f",foodInfo.fat*9)} kcal")
                }
            }
        }
    }
}

@Composable
fun VerticalDivider() {
    Spacer(
        modifier = Modifier
            .width(1.dp)
            .height(75.dp)
            .background(Color.LightGray)
    )
}

@Composable
@Preview(showBackground = true)
fun PreviewFoodItemCard() {
//    FoodItemCard(foodInfo = FoodInfo("등갈비 김치찜", 200, 25.5f, 32.0f,21.9f,427.1f))
}