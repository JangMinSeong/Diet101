package com.ssafy.d101.ui.view.screens

import android.app.DatePickerDialog
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssafy.d101.model.Dunchfast
import com.ssafy.d101.model.IntakeReq
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.view.components.DailyHorizontalBar
import com.ssafy.d101.viewmodel.DietViewModel
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date


@Composable
fun DietAiAnalysisResultScreen(navController: NavHostController) {
    val dietViewModel: DietViewModel = hiltViewModel()

    val result by dietViewModel.getTakeReqs().collectAsState()
    val dunchType by dietViewModel.getType().collectAsState()
    Log.d("in diet screen","$result")

    val scope = rememberCoroutineScope()

    val totalNutrients = result?.let { calculateTotalNutrients(it) }

    var selectedDate by remember { mutableStateOf(LocalDate.now()) }
    val context = LocalContext.current

    fun showDatePicker() {
        val calendar = Calendar.getInstance()
        calendar.time = Date.from(selectedDate.atStartOfDay(ZoneId.systemDefault()).toInstant())

        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                selectedDate = LocalDate.of(year, month + 1, dayOfMonth)
            },
            year,
            month,
            day
        )
        datePickerDialog.show()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Ivory)
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 80.dp)
                .background(Color(0xFFFFF8F8), shape = RoundedCornerShape(12.dp))
        ) {
            Column(modifier = Modifier.padding(20.dp)) {
                Spacer(modifier = Modifier.padding(30.dp))
                Row() {
                    Spacer(modifier = Modifier.padding(20.dp))
                    val dunch = dunchType?.let { getDunchfastToString(it) }
                    Text("$dunch", fontSize = 30.sp)
                }
                Spacer(modifier = Modifier.padding(20.dp))

                Row(horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically) {
                    Spacer(modifier = Modifier.padding(20.dp))
                    Text("식사일 ", fontSize = 15.sp)
                    Spacer(modifier = Modifier.padding(10.dp))
                    Button(
                        onClick = { showDatePicker() },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.LightGray),
                        modifier = Modifier
                            .width(150.dp)
                            .height(40.dp)
                    ) {
                        Text(
                            text = "${selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))}",
                            color = Color.Black,
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Bold,
                        )
                    }
                    Spacer(modifier = Modifier.padding(20.dp))
                }
                Spacer(modifier = Modifier.padding(10.dp))
                DailyHorizontalBar(carbsPercentage = 20f, proteinPercentage = 40f, fatsPercentage = 40f)
                Spacer(modifier = Modifier.padding(20.dp))
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .background(Color.LightGray)
                        .padding(20.dp)
                ) {
                    Column() {
                        if (totalNutrients != null) {
                            Text("${totalNutrients.totalCalories} kcal", fontWeight = FontWeight.Bold)
                        }
                        Spacer(modifier = Modifier.padding(2.dp))
                        Divider(modifier = Modifier
                            .height(2.dp)
                            .fillMaxWidth(), color = Color.DarkGray)
                        Spacer(modifier = Modifier.padding(5.dp))
                        Row(
                            modifier = Modifier
                            .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("탄수화물")
                                Spacer(modifier = Modifier.padding(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color.White)
                                        .width(70.dp)
                                        .height(25.dp),
                                    contentAlignment = Alignment.Center

                                ) {
                                    if (totalNutrients != null) {
                                        Text("${totalNutrients.totalCarbohydrates.toInt()}g")
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("단백질")
                                Spacer(modifier = Modifier.padding(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color.White)
                                        .width(70.dp)
                                        .height(25.dp),
                                    contentAlignment = Alignment.Center

                                ) {
                                    if (totalNutrients != null) {
                                        Text("${totalNutrients.totalProteins.toInt()}g")
                                    }
                                }
                            }
                            Spacer(modifier = Modifier.padding(10.dp))
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text("지방")
                                Spacer(modifier = Modifier.padding(6.dp))
                                Box(
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color.White)
                                        .width(70.dp)
                                        .height(25.dp)
                                        .clip(RoundedCornerShape(20.dp)),
                                    contentAlignment = Alignment.Center,


                                ) {
                                    if (totalNutrients != null) {
                                        Text("${totalNutrients.totalFats.toInt()}g")
                                    }
                                }
                            }
                        }

                    }
                }
                Spacer(modifier = Modifier.padding(20.dp))
                Button(
                    onClick = {

                        //            isClicked.value = true
                        //            navController.navigate("home")
                        dietViewModel.setDietDate(selectedDate)

                        scope.launch {
                            dietViewModel.saveMeal()
                        }
                        navController.navigate("home")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                    modifier = Modifier
                        .width(160.dp)
                        .height(40.dp)
                        .align(Alignment.CenterHorizontally)
                ) {
                    Text(
                        text = "식사 기록하기",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

fun getDunchfastToString(dunchfast: Dunchfast): String {
    return when(dunchfast) {
        Dunchfast.BREAKFAST -> "아침"
        Dunchfast.BRUNCH -> "아점"
        Dunchfast.LUNCH -> "점심"
        Dunchfast.LINNER -> "점저"
        Dunchfast.DINNER -> "저녁"
        Dunchfast.MIDNIGHT -> "야식"
        Dunchfast.SNACK -> "간식"
        Dunchfast.DRINK -> "음료"
        Dunchfast.ALCOHOL -> "주류"
        else -> "아침" // 선택한 항목이 매핑되지 않는 경우 아침으로 고정
    }
}

fun calculateTotalNutrients(intakes: List<IntakeReq>): TotalNutrients {
    val totalCalories = intakes.sumOf { (it.amount * it.kcal).toInt() }
    val totalCarbs = intakes.sumOf { it.amount * it.carbohydrate }
    val totalProteins = intakes.sumOf { it.amount * it.protein }
    val totalFats = intakes.sumOf { it.amount * it.fat }

    return TotalNutrients(
        totalCalories = totalCalories,
        totalCarbohydrates = totalCarbs,
        totalProteins = totalProteins,
        totalFats = totalFats
    )
}

data class TotalNutrients(
    val totalCalories: Int,
    val totalCarbohydrates: Double,
    val totalProteins: Double,
    val totalFats: Double
)

@Preview(showBackground = true)
@Composable
fun PreviewDietAiAnalysisResultScreen() {
    DietAiAnalysisResultScreen(navController = rememberNavController())
}