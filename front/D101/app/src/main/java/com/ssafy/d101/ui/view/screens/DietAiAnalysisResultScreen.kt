package com.ssafy.d101.ui.view.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ssafy.d101.ui.theme.Ivory


@Composable
fun DietAiAnalysisResultScreen() {
//    val dietViewModel: DietViewModel = hiltViewModel()
//
//    val isClicked = remember { mutableStateOf(false) }
//    LaunchedEffect(isClicked) {
//        if (isClicked.value) {
//            dietViewModel.saveMeal()
//        }
//    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Ivory)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 30.dp, vertical = 80.dp)
                .background(Color(0xFFFFF8F8), shape = RoundedCornerShape(12.dp))
        ) {
            Row {
                Spacer(modifier = Modifier.width(48.dp))
            }
        }
        Button(
            onClick = {
//            isClicked.value = true
//            navController.navigate("home")
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
}

@Preview(showBackground = true)
@Composable
fun PreviewDietAiAnalysisResultScreen() {
    DietAiAnalysisResultScreen()
}