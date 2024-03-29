package com.ssafy.d101.ui.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.d101.R
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.view.components.BarItem
import com.ssafy.d101.ui.view.components.CustomSemiCirclePieChart
import com.ssafy.d101.ui.view.components.DailyHorizontalBar
import com.ssafy.d101.ui.view.components.MonthLeaderboardScreen
import com.ssafy.d101.ui.view.components.MonthRankingItem
import com.ssafy.d101.ui.view.components.MonthlyNutritionChartHorizontal
import com.ssafy.d101.ui.view.components.StackedBarItem
import com.ssafy.d101.ui.view.components.WeekLeaderboardScreen
import com.ssafy.d101.ui.view.components.WeekRankingItem
import com.ssafy.d101.ui.view.components.WeeklyNutritionChart
import com.ssafy.d101.viewmodel.DietViewModel
import com.ssafy.d101.viewmodel.UserViewModel

@Composable
fun DietAnalysis(
    dateTitle : String,
    weekTitle : String,
    monthTitle : String,
) {

    val userViewModel :UserViewModel = viewModel()
    val dietViewModel :DietViewModel = viewModel()

    val user by userViewModel.getUser().collectAsState(initial = null)
    val analysisDiet by dietViewModel.resultDiet.observeAsState()

    LaunchedEffect(Unit) {
        dietViewModel.analysisDiet()
    }

    // 선택된 분석 타입 (오늘의 분석, 과거 분석)을 추적하는 상태
    var selectedAnalysis by remember { mutableStateOf("today") }
    // 과거 분석시 선택된 타임라인 (주간, 월간)을 추적하는 상태
    var selectedTimeline by remember { mutableStateOf("weekly") }
    // 월간 랭킹 보기 + 식단 보기 클릭 상태
    var selectedMonthOption by remember { mutableStateOf("diet") }

    // 선택된 분석 타입과 타임라인에 따라 동적으로 제목을 결정
    val title = when {
        selectedAnalysis == "today" -> dateTitle
        selectedAnalysis == "past" && selectedTimeline == "weekly" -> weekTitle
        selectedAnalysis == "past" && selectedTimeline == "monthly" -> monthTitle
        else -> {dateTitle}
    }

    Box(modifier = Modifier
        .fillMaxWidth()
        .fillMaxHeight()
        .background(Ivory)
        .padding(16.dp),

        contentAlignment = Alignment.Center // 가운데 정렬을 위해 추가
    ) {
        Box(
            modifier = Modifier
                .background(Color(0xFFF2F0F0))
                .padding(16.dp)
                .clip(RoundedCornerShape(20.dp))
                .fillMaxWidth()
                .fillMaxHeight(),
            contentAlignment = Alignment.Center // 가운데 정렬을 위해 추가
        )
        {
            Column {
                Image(
                    modifier = Modifier
                        .align(Alignment.End)
                        .size(30.dp),
                    painter = painterResource(R.drawable.xbutton),
                    contentDescription = "xBtn"
                )
                Text(
                    title,
                    color = Color(0xFF416C50),
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.align(Alignment.CenterHorizontally)
                )
                Spacer(modifier = Modifier.size(10.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    // 오늘의 내 식단 분석 버튼
                    TextButton(onClick = { selectedAnalysis = "today" }) {
                        Text(
                            "오늘의 내 식단 분석",
                            color = if (selectedAnalysis == "today") Color.Black else Color.Gray
                        )
                    }

                    // 세로선
                    Divider(
                        color = Color(0xFF416C50),
                        modifier = Modifier
                            .height(40.dp)
                            .width(3.dp)
                            .padding(vertical = 10.dp)
                    )

                    // 과거의 내 식단 분석 버튼
                    TextButton(onClick = { selectedAnalysis = "past" }) {
                        Text(
                            "과거의 내 식단 분석",
                            color = if (selectedAnalysis == "past") Color.Black else Color.Gray
                        )
                    }
                }
                
                when(selectedAnalysis) {
                    "today" -> {
                        Spacer(modifier = Modifier.size(15.dp))
                        var userGender : Int = 5
                        if(user?.userInfo?.gender == "MALE") userGender = 1
                        else userGender = 2
                        ////////////////TODO: API 요청  user, 일일 식단 기록 필요
                        user?.userSubInfo?.calorie?.let { CustomSemiCirclePieChart(consumedKcal = 1200, totalKcal = it, gender = userGender) }
                        Spacer(modifier = Modifier.size(15.dp))
                        ////////////////TODO: API 요청   일일 식단 기록 필요
                        DailyHorizontalBar(carbsPercentage = 30f, proteinPercentage = 30f, fatsPercentage = 40f) //합 100% 주의

                    }
                }
                
                if (selectedAnalysis == "past") {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        // 오늘의 내 식단 분석 버튼
                        TextButton(onClick = { selectedTimeline = "weekly" }) {
                            Text("주간", color = if (selectedTimeline == "weekly") Color.Black else Color.Gray)
                        }

                        // 세로선
                        Divider(
                            color = Color(0xFF416C50),
                            modifier = Modifier
                                .height(40.dp)
                                .width(3.dp)
                                .padding(vertical = 10.dp)
                        )

                        // 과거의 내 식단 분석 버튼
                        TextButton(onClick = {
                            selectedTimeline = "monthly"
                        }) {
                            Text("월간", color = if (selectedTimeline == "monthly") Color.Black else Color.Gray)
                        }

                        // 월간 선택 시 "내 월간 랭킹 보기" 버튼 추가
                        if (selectedTimeline == "monthly") {
                            Spacer(Modifier.width(50.dp)) // 버튼 사이의 간격

                            TextButton(onClick = {
                                // 클릭 시 selectedMonthOption을 토글
                                selectedMonthOption = if (selectedMonthOption == "diet") "ranking" else "diet"
                            }) {
                                Text(
                                    text = if (selectedMonthOption == "diet") "내 월간 랭킹 보기" else "월간 식단 분석 보기",
                                    color = Color.Red
                                )
                            }
                        }
                    }

                    when (selectedAnalysis) {
                        "past" -> {
                            when (selectedTimeline) {
                                "weekly" -> {
                                    //주간 식단 분석
                                    /////////TODO : api 필요    주간 식단 필요
                                    WeeklyNutritionChart(
                                        weeklyData = StackedBarItem(
                                            data = listOf(
                                                listOf(600f, 400f, 200f), // MON
                                                listOf(500f, 300f, 200f), // TUE
                                                listOf(700f, 300f, 300f), // WED
                                                listOf(800f, 200f, 400f), // THU
                                                listOf(450f, 550f, 250f), // FRI
                                                listOf(400f, 600f, 400f), // SAT
                                                listOf(500f, 500f, 500f)  // SUN
                                            ),
                                            maxValue = 2000f  // 총합 계산해서 넣어야 비율로 나옴
                                        ),
                                        title = "WEEKLY"
                                    )
                                    Spacer(modifier = Modifier.size(7.dp))

                                    ///////TODO : api 필요    사용자 랭킹 상위 3개 필요
                                    WeekLeaderboardScreen(
                                        data = WeekRankingItem(
                                            rank = listOf(1, 2, 3),
                                            name = listOf("로제 떡볶이", "치킨", "김치찌개")
                                        )
                                    )
                                    Spacer(modifier = Modifier.size(6.dp))
                                }

                                "monthly" -> {
                                    when(selectedMonthOption) {
                                        "diet" -> {
                                            //월간 식단 분석
                                            //////////TODO : api 필요  월간 식단 필요
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
                                        "ranking" -> {
                                            // 월간 랭킹
                                            ////// TODO : api 필요    월간 랭킹 상위 7개 필요
                                            MonthLeaderboardScreen(
                                                data = MonthRankingItem(
                                                    rank = listOf(1,2,3,4,5,6,7),
                                                    name = listOf("로제 떡볶이","치킨","김치찌개","된장찌개","피자","돌솥밥","감자탕")
                                                )
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDietAnalysis() {
    //dateTitle : 일별 날짜 제목, weekTitle : 주별 날짜 제목, monthTitle : 월별 날짜 제목
    DietAnalysis(dateTitle = "2024 03 06 식단 분석",weekTitle = "3월 2째주 식단 분석", monthTitle = "3월 식단 분석")
}