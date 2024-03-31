package com.ssafy.d101.ui.view.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.ssafy.d101.R

data class MonthRankingItem (
    val rank : List<Int>,
    val name : List<String>
)

@Composable
fun MonthLeaderboardScreen(data : MonthRankingItem) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFFF2DCDC))
            .padding(16.dp)
            .fillMaxWidth() ,
        contentAlignment = Alignment.Center // 가운데 정렬을 위해 추가
    ) {
        Column(
            Modifier.width(150.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(R.drawable.trophy),
                contentDescription = "트로피" // 접근성을 위한 이미지 설명
            )
            Spacer(modifier = Modifier.size(8.dp))
            Text("나의 선호 음식 랭킹", style = MaterialTheme.typography.bodySmall)
            Spacer(modifier = Modifier.size(15.dp))
            Column {
                data.rank.zip(data.name).forEach { (rank, name) ->
                    MonthLeaderboardItem(rank = rank, name = name)
                }
            }
        }
    }
}

@Composable
fun MonthLeaderboardItem(rank: Int, name: String) {
    Column (modifier = Modifier.height(30.dp)){
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth() // Row의 너비를 최대로 설정
        ) {
            val medalImage = when(rank) {
                1 -> R.drawable.medal1 // 1등일 때 medal1 이미지 사용
                2 -> R.drawable.medal2 // 2등일 때 medal2 이미지 사용
                3 -> R.drawable.medal3 // 3등일 때 medal3 이미지 사용
                else -> R.drawable.medal4
            }

            if (medalImage != 0) { // 유효한 이미지 리소스 ID가 있는 경우에만 Image 컴포저블을 렌더링
                Image(
                    modifier = Modifier.size(20.dp),
                    painter = painterResource(id = medalImage),
                    contentDescription = "메달" // 접근성을 위한 이미지 설명
                )
            }
            Spacer(Modifier.weight(0.5f)) // Text를 중앙으로 밀기 위해 추가
            Text(
                text = name,
                modifier = Modifier.align(Alignment.CenterVertically) // 텍스트를 수직 중앙 정렬
            )
            Spacer(Modifier.weight(1f)) // 오른쪽 정렬을 위한 Spacer
        }
        Divider(
            color = Color.Gray,
            thickness = 1.dp,
            modifier = Modifier
                .padding(horizontal = 16.dp) // 가로선 양쪽 여백 추가
        )
    }
}


@Preview(showBackground = true)
@Composable
fun MonthRankingPreview() {
    MonthLeaderboardScreen(data = MonthRankingItem(
        rank = listOf(1,2,3,4,5,6,7),
        name = listOf("로제 떡볶이","치킨","김치찌개","된장찌개","피자","돌솥밥","감자탕")
    ))
}