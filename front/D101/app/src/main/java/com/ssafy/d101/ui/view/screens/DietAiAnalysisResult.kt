package com.ssafy.d101.ui.view.screens

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ssafy.d101.model.Dunchfast
import com.ssafy.d101.viewmodel.DietViewModel

@Composable
fun DietAiAnalysisResult(navController: NavController) {
    val dietViewModel: DietViewModel = hiltViewModel()

    val isClicked = remember { mutableStateOf(false) }
    LaunchedEffect(isClicked) {
        if (isClicked.value) {
            dietViewModel.saveMeal()
        }
    }

    Button(
        onClick = {
            isClicked.value = true
            navController.navigate("home")
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
        modifier = Modifier
            .width(160.dp)
            .height(40.dp)
    ) {
        Text(
            text = "식사 기록하기",
            color = Color.White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}