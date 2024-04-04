package com.ssafy.d101.ui.view.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.ssafy.d101.model.AnalysisDiet
import com.ssafy.d101.model.CalAnnualNutrient
import com.ssafy.d101.model.DietInfo
import java.time.temporal.TemporalAdjusters

fun generateTitles(): Triple<String, String, String> {
    val today = LocalDate.now()
    val month = today.month.value

    val firstDayOfMonth = today.with(TemporalAdjusters.firstDayOfMonth())
    val weekOfMonth = if (today.dayOfMonth < 7 && firstDayOfMonth.dayOfWeek > today.dayOfWeek) 1
    else (today.dayOfMonth + firstDayOfMonth.dayOfWeek.value - today.dayOfWeek.value) / 7 + 1

    // 오늘 날짜에 식단분석
    val dateTitle = today.format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")) + " 식단분석"
    // 주간 식단분석
    val weekTitle = "${month}월 ${weekOfMonth}째주 식단분석"
    // 몇월 식단분석
    val monthTitle = "${month}월 식단분석"

    return Triple(dateTitle, weekTitle, monthTitle)
}

@Composable
fun DietAnalysis(navController: NavController) {
    val (dateTitle, weekTitle, monthTitle) = generateTitles()
    val userViewModel :UserViewModel = hiltViewModel()
    val dietViewModel :DietViewModel = hiltViewModel()

    val analysisDiet by dietViewModel.resultDiet.collectAsState()

    val userInfo by userViewModel.getUserInfo().collectAsState()
    val userSubInfo by userViewModel.getUserSubInfo().collectAsState()

    val scrollState = rememberScrollState()

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
                Column(modifier = Modifier.verticalScroll(scrollState)) {
                    Image(
                        modifier = Modifier
                            .align(Alignment.End)
                            .size(30.dp)
                            .clickable {
                                navController.popBackStack()
                            },
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
                            var userGender : Int
                            if(userInfo?.gender == "MALE") userGender = 1
                            else userGender = 2

                            val dailyCal = analysisDiet?.let{ calculateTotalCalories(it) }
                            if(userSubInfo != null && dailyCal != null)
                                CustomSemiCirclePieChart(consumedKcal = dailyCal, totalKcal = userSubInfo!!.calorie, gender = userGender)
                            Spacer(modifier = Modifier.size(15.dp))

                            val nutri = analysisDiet?.let { calculateDailyNutrientRatios(it) }
                            if (nutri != null) {
                                DailyHorizontalBar(carbsPercentage = nutri.first, proteinPercentage = nutri.second, fatsPercentage = nutri.third)
                            }

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
                                        text = if (selectedMonthOption == "diet") "내 식단 랭킹 보기" else "월간 식단 분석 보기",
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
                                        if(analysisDiet != null) {
                                            WeeklyNutritionChart(
                                                weeklyData = generateWeeklyData(analysisDiet!!.weeklyDiet),
                                                title = "WEEKLY"
                                            )
                                            Spacer(modifier = Modifier.size(7.dp))

                                            WeekLeaderboardScreen(
                                                data = generateWeekRankingItem(analysisDiet!!.weeklyDiet)
                                            )
                                            Spacer(modifier = Modifier.size(6.dp))
                                        }
                                    }

                                    "monthly" -> {
                                        when(selectedMonthOption) {
                                            "diet" -> {
                                                //월간 식단 분석
                                                if(analysisDiet != null) {
                                                    MonthlyNutritionChartHorizontal(
                                                        monthlyData = generateMonthlyNutritionData(analysisDiet!!.annualNutrients),
                                                        title = "MONTHLY"
                                                    )
                                                }
                                            }
                                            "ranking" -> {
                                                // 월간 랭킹
                                                if(analysisDiet != null) {
                                                    MonthLeaderboardScreen(
                                                        data = generateMonthRankingItem(analysisDiet!!.totalRank)
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(50.dp))
                }
            }
    }
}

fun generateWeekRankingItem(weeklyData: List<DietInfo>): WeekRankingItem {
    // 음식 이름별 출현 횟수를 계산
    val foodCountMap = weeklyData.flatMap { it.intake }
        .groupingBy { it.food.name }
        .eachCount()
        .toList()
        .sortedByDescending { it.second } // 출현 횟수 기준 내림차순 정렬
        .take(3) // 상위 3개 항목 선택

    // 상위 3개 음식의 이름 추출
    val topFoodNames = foodCountMap.map { it.first }

    // 상위 3개 음식의 순위 생성 (1부터 시작)
    val ranks = foodCountMap.indices.map { it + 1 }

    return WeekRankingItem(rank = ranks, name = topFoodNames)
}

fun generateMonthRankingItem(totalRank: List<String>): MonthRankingItem {
    // totalRank에서 최대 7개의 항목을 가져옴
    val names = totalRank.take(7)
    // 각 항목에 대한 순위를 생성
    val ranks = names.indices.map { it + 1 }

    return MonthRankingItem(rank = ranks, name = names)
}

fun generateWeeklyData(weeklyDiet: List<DietInfo>): StackedBarItem {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
    val weekData = MutableList(7) { mutableListOf(0f, 0f, 0f) } // 일주일 동안의 데이터 초기화
    val dayOfWeekIndex = mapOf(
        "MONDAY" to 0,
        "TUESDAY" to 1,
        "WEDNESDAY" to 2,
        "THURSDAY" to 3,
        "FRIDAY" to 4,
        "SATURDAY" to 5,
        "SUNDAY" to 6
    )

    weeklyDiet.forEach { dietInfo ->
        val date = LocalDate.parse(dietInfo.time, formatter)
        val dayIndex = dayOfWeekIndex[date.dayOfWeek.name] ?: return@forEach

        var totalCarbs = 0f
        var totalProtein = 0f
        var totalFat = 0f

        dietInfo.intake.forEach { intakeInfo ->
            val intakeAmount = intakeInfo.amount

            val carbsCalories = intakeInfo.food.carbohydrate * intakeAmount * 4f
            val proteinCalories = intakeInfo.food.protein * intakeAmount * 4f
            val fatCalories = intakeInfo.food.fat * intakeAmount * 9f

            weekData[dayIndex][0] += carbsCalories.toFloat()
            weekData[dayIndex][1] += proteinCalories.toFloat()
            weekData[dayIndex][2] += fatCalories.toFloat()
        }
    }
    Log.d("weekData", "$weekData")
    val calculatedData = weekData.map { list ->
        listOf(
            list[0]+ // 탄수화물
            list[1]+ // 단백질
            list[2]  // 지방
        )
    }
    Log.d("calResult","$calculatedData")
    val maxValue = calculatedData.flatten().maxOrNull() ?: 0f
    val roundedMaxValue = Math.ceil(maxValue / 100.0) * 100
    Log.d("roundMaxValue","$roundedMaxValue")
    return StackedBarItem(data = weekData, maxValue = roundedMaxValue.toFloat())
}

fun generateMonthlyNutritionData(annualNutrients: List<CalAnnualNutrient>): BarItem {
    // 1월부터 12월까지 모든 달의 데이터를 저장할 리스트 초기화
    val monthlyData = MutableList(12) { listOf(0f, 0f, 0f) }
    val monthlyCalories = MutableList(12) { 0f }

    // annualNutrients에서 각 달의 데이터를 처리
    annualNutrients.forEach { nutrient ->
        val monthIndex = nutrient.month - 1
        // 영양소 데이터를 리스트로 변환하여 해당 달에 저장
        val carbs = nutrient.totalCarbohydrate.toFloat()
        val protein = nutrient.totalProtein.toFloat()
        val fats = nutrient.totalFat.toFloat()
        // 각 영양소별 칼로리 계산
        val carbsCalories = carbs * 4
        val proteinCalories = protein * 4
        val fatsCalories = fats * 9
        // 칼로리 데이터 저장
        monthlyData[monthIndex] = listOf(carbsCalories, proteinCalories, fatsCalories)
        // 달별 총 칼로리 계산하여 저장
        monthlyCalories[monthIndex] = carbsCalories + proteinCalories + fatsCalories
    }

    // 모든 데이터 중 최대값을 계산
    val maxValue = monthlyCalories.maxOrNull() ?: 0f
    val roundedMaxValue = Math.ceil(maxValue / 100.0) * 100

    return BarItem(data = monthlyData, maxValue = roundedMaxValue.toFloat())
}

fun calculateDailyNutrientRatios(analysisDiet: AnalysisDiet): Triple<Float, Float, Float> {
    var totalCarbs = 0.0
    var totalProtein = 0.0
    var totalFat = 0.0

    analysisDiet.dailyDiet.forEach { dietInfo ->
        dietInfo.intake.forEach { intakeInfo ->
            val foodInfo = intakeInfo.food
            val intakeAmount = intakeInfo.amount / foodInfo.portionSize.toDouble()

            totalCarbs += foodInfo.carbohydrate * intakeAmount
            totalProtein += foodInfo.protein * intakeAmount
            totalFat += foodInfo.fat * intakeAmount
        }
    }

    val totalCaloriesFromCarbs = totalCarbs * 4
    val totalCaloriesFromProtein = totalProtein * 4
    val totalCaloriesFromFat = totalFat * 9

    val totalCalories = totalCaloriesFromCarbs + totalCaloriesFromProtein + totalCaloriesFromFat

    val carbsPercentage = (totalCaloriesFromCarbs / totalCalories * 100).toFloat()
    val proteinPercentage = (totalCaloriesFromProtein / totalCalories * 100).toFloat()
    val fatPercentage = (totalCaloriesFromFat / totalCalories * 100).toFloat()

    return Triple(carbsPercentage, proteinPercentage, fatPercentage)
}

fun calculateTotalCalories(analysisDiet: AnalysisDiet): Int {
//    return analysisDiet.dailyDiet.sumOf { dietInfo ->
//        dietInfo.intake.sumOf { intakeInfo ->
//            val foodInfo = intakeInfo.food
//            val intakeAmount = intakeInfo.amount
//
//            ((foodInfo.carbohydrate * 4 + foodInfo.protein * 4 + foodInfo.fat * 9) * intakeAmount).toInt()
//        }
    return analysisDiet.dailyDiet.sumOf { dietInfo ->
        dietInfo.kcal
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewDietAnalysis() {
    //dateTitle : 일별 날짜 제목, weekTitle : 주별 날짜 제목, monthTitle : 월별 날짜 제목
    DietAnalysis(navController = rememberNavController())
}