package com.ssafy.d101.ui.view.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.view.components.CalendarApp


@Composable
fun HomeScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize().background(Ivory)
    ) {
        CalendarApp(modifier = Modifier.padding(16.dp))
    }

}