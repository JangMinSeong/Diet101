package com.ssafy.d101.ui.view.screens

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerDefaults
import androidx.compose.foundation.pager.PagerSnapDistance
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssafy.d101.model.FoodInfo
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.theme.White
import com.ssafy.d101.ui.view.components.BackHeader
import com.ssafy.d101.ui.view.components.FoodList
import com.ssafy.d101.viewmodel.DietViewModel
import com.ssafy.d101.viewmodel.FoodViewModel
import com.ssafy.d101.viewmodel.UserViewModel
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@Composable
fun RecommendScreen(navController: NavHostController) {

    val dietViewModel: DietViewModel = hiltViewModel()
    val userViewModel: UserViewModel = hiltViewModel()
    val foodViewModel: FoodViewModel = hiltViewModel()
    val diets by dietViewModel.dayDiet.collectAsState()
    val foods by foodViewModel.recommendFood.collectAsState()
    var rest by remember {mutableIntStateOf(0)}
    var ggini by remember {mutableIntStateOf(1)}

    val goal=2316
    LaunchedEffect(Unit) {
        dietViewModel.loadDayDiet(dietViewModel.getCurrentDate())
//        foodViewModel.loadRecommendFoods(rest.toString())
    }
    Log.i("diets", diets.toString())
    LaunchedEffect(diets) {
        var sum = 0
        diets?.forEach { i -> sum+=i.kcal }
        rest = maxOf(goal - sum, 0)
    }

    val textStyle = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        color = Color.Black)

    Column( modifier = Modifier // 백그라운드
        .fillMaxSize()
        .background(Ivory)
    ) {
        BackHeader("음식 추천", navController)
        var step by remember {mutableIntStateOf(0)};
        RecommendSteps(step)
        when (step) {
            0 -> RecommendStepOne(goal_cal = goal, rest_cal = rest,  onNumberSelected = {
                    selectedNumber -> ggini = selectedNumber
            })
            1 -> RecommendStepTwo(rest = rest, onNumberSelected = {
                    selectedNumber -> rest = selectedNumber
            })
            2 -> RecommendStepThree(foods = foods ?: emptyList())
            else -> { }
        }

        if(step<=1) {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(30.dp, 0.dp, 30.dp, 100.dp)) {
                Spacer(modifier = Modifier
                    .weight(1f)
                    .fillMaxSize())
                Button(
                    onClick = {
                        if(step == 0){
                            rest/=ggini
                        } else if(step==1) {
                            foodViewModel.loadRecommendFoods((rest*10).toString())
                        }
                        step+=1
                              },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFB6B284), // 버튼 배경색
                        contentColor = Color.White // 버튼 텍스트색
                    ),
                    // 버튼의 크기를 설정
                    modifier = Modifier
                        .size(width = 80.dp, height = 35.dp)
                        .align(Alignment.End)
                ) {
                    Text(
                        text = "다음", style = TextStyle(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            color = Color.White // 색상도 설정할 수 있음
                        )
                    )
                }
            }
        }

    }

}
@Composable
fun RecommendStepThree(foods: List<FoodInfo>) {
    Column(modifier = Modifier.padding(40.dp,10.dp,40.dp,100.dp)) {
        Text(text = "추천된 음식을 확인하고", style = textStyle)
        Text(text = "식단에 추가해 보세요", style = textStyle)
        FoodList(foodInfos = foods)
    }
}

@Composable
fun RecommendStepTwo(rest: Int, onNumberSelected: (Int) -> Unit) {
    Column(modifier = Modifier.padding(40.dp)) {
        Text(text = "한 끼에 적당한 칼로리를 계산했어요", style = textStyle)
        Text(text = "가벼운 한 끼를 위해서 줄이거나", style = textStyle)
        Text(text = "맛있는 한 끼를 위해서 늘려보세요", style = textStyle)
    }
    Column ( modifier = Modifier
        .padding(30.dp, 0.dp, 30.dp, 30.dp)
        .shadow(15.dp, RoundedCornerShape(12.dp))
        .background(White, shape = RoundedCornerShape(12.dp))
    ) {
        Row(
            modifier = Modifier
                .padding(20.dp, 10.dp, 20.dp, 0.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "칼로리",
                style = textStyle,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Row(modifier= Modifier.padding(horizontal = 30.dp)) {
            NumberPicker(current = (rest -1)/10, end = 1100, step = 10, onNumberSelected = onNumberSelected)
        }
    }
}

@Composable
fun RecommendSteps(step: Int) {
    Row(modifier = Modifier.padding(horizontal = 30.dp)) {
        StepsProgressBar(modifier = Modifier.fillMaxWidth(), numberOfSteps = 1, currentStep = step)
    }
    Row(modifier = Modifier.padding(20.dp,0.dp,0.dp,0.dp)) {
        Text(text = "남은 끼니")
        Spacer(modifier = Modifier.size(90.dp,0.dp))
        Text(text = "칼로리 조절")
        Spacer(modifier = Modifier.size(87.dp,0.dp))
        Text(text = "추천 결과")
    }
}

@Composable
fun StepsProgressBar(modifier: Modifier = Modifier, numberOfSteps: Int, currentStep: Int) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        //Circle
        Canvas(modifier = Modifier
            .size(20.dp),
            onDraw = {
                drawCircle(color = Color(0xFFDFDA93))
            }
        )
        for (step in 0..numberOfSteps) {
            Step(
                modifier = Modifier.weight(1F),
                isCompete = step < currentStep,
                isCurrent = step < currentStep
            )
        }
    }
}

@Composable
fun Step(modifier: Modifier = Modifier, isCompete: Boolean, isCurrent: Boolean) {
    val color = if (isCompete || isCurrent) Color(0xFFDFDA93) else Color.Gray
    val innerCircleColor = if (isCompete) Color(0xFFDFDA93) else Color.Gray
    Box(modifier = modifier) {
        //Line
        Divider(
            modifier = Modifier.align(Alignment.CenterStart),
            color = color,
            thickness = 2.dp
        )
        //Circle
        Canvas(modifier = Modifier
            .size(20.dp)
            .align(Alignment.CenterEnd),
            onDraw = {
                drawCircle(color = innerCircleColor)
            }
        )
    }
}

@Composable
fun RecommendStepOne(goal_cal: Int, rest_cal: Int, onNumberSelected: (Int) -> Unit) {
    Column(modifier = Modifier.padding(40.dp)) {
        Text(text = "남은 끼니 수를 알려 주시면", style = textStyle)
        Text(text = "적당한 한 끼를 추천해 드릴게요.", style = textStyle)
    }
    Column ( modifier = Modifier
        .padding(30.dp, 0.dp, 30.dp, 30.dp)
        .shadow(15.dp, RoundedCornerShape(12.dp))
        .background(White, shape = RoundedCornerShape(12.dp))
    ) {
        Row(modifier = Modifier.padding(20.dp, 20.dp, 20.dp, 0.dp)) {
            Text(text = "목표 칼로리", style = textStyle)
            Spacer(Modifier.weight(1f))
            Text(text = "${goal_cal} kcal", style = textStyle)
        }
        Row(modifier = Modifier.padding(20.dp, 10.dp, 20.dp, 15.dp)) {
            Text(text = "남은 칼로리", style = textStyle)
            Spacer(Modifier.weight(1f))
            Text(text = "${rest_cal} kcal", style = textStyle)
        }
        Row(
            modifier = Modifier
                .padding(20.dp, 10.dp, 20.dp, 0.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = "남은 끼니",
                style = textStyle,
                modifier = Modifier.align(Alignment.CenterVertically)
            )
        }
        Row(modifier= Modifier.padding(horizontal = 30.dp)) {
            NumberPicker(current = (1-1)/1, end = 5, step = 1, onNumberSelected = onNumberSelected)
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NumberPicker(modifier: Modifier = Modifier, current: Int, end: Int, step: Int, onNumberSelected: (Int) -> Unit) {
    BoxWithConstraints(modifier = Modifier.fillMaxWidth()) {
        val contentPadding = (maxWidth - 50.dp) / 2
        val offSet = maxWidth / 5
        val itemSpacing = offSet - 50.dp
        val pagerState = rememberPagerState(initialPage = current, pageCount = {
            end
        })

        val scope = rememberCoroutineScope()

        val mutableInteractionSource = remember {
            MutableInteractionSource()
        }
        /** 이벤트 로직 **/
        LaunchedEffect(pagerState.currentPage) {
            val selectedNumber = pagerState.currentPage + 1
            onNumberSelected(selectedNumber)
        }

        HorizontalPager(
            modifier = modifier,
            state = pagerState,
            flingBehavior = PagerDefaults.flingBehavior(
                state = pagerState,
                pagerSnapDistance = PagerSnapDistance.atMost(0)
            ),
            contentPadding = PaddingValues(horizontal = contentPadding),
            pageSpacing = itemSpacing,
        ) { page ->
            Box(
                modifier = Modifier
                    .size(50.dp)
                    .graphicsLayer {
                        val pageOffset = ((pagerState.currentPage - page) + pagerState
                            .currentPageOffsetFraction).absoluteValue
                        val percentFromCenter = 1.0f - (pageOffset / (5f / 2f))
                        val opacity = 0.25f + (percentFromCenter * 0.75f).coerceIn(0f, 1f)

                        alpha = opacity
                        clip = true
                    }
                    .clickable(
                        interactionSource = mutableInteractionSource,
                        indication = null,
                        enabled = true,
                    ) {
                        scope.launch {
                            pagerState.animateScrollToPage(page)
                        }
                    }) {
                Text(
                    text = "${(page+1)*step}",
                    color = if (pagerState.currentPage==page) Color(0xFFDA9000) else Color.Black,
                    modifier = Modifier
                        .size(50.dp)
                        .wrapContentHeight(),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Preview
@Composable
fun StepsProgressBarPreview() {
    val currentStep = remember { mutableStateOf(1) }
    StepsProgressBar(modifier = Modifier.fillMaxWidth(), numberOfSteps = 1, currentStep = currentStep.value)
}

@Preview
@Composable
fun RecommendScreenPreview() {
    val navController = rememberNavController()
    RecommendScreen(navController = navController)
}
