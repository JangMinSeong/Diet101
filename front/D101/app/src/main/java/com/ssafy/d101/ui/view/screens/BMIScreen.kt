package com.ssafy.d101.ui.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.view.components.BackHeader

@Composable
fun BMIScreen() {
    Column( modifier = Modifier // 백그라운드
        .fillMaxSize()
        .background(Ivory)
    ) {
         BackHeader("BMI 측정")
    }
}
