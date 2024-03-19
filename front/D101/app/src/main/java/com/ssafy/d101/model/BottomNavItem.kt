package com.ssafy.d101.model

import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem (
    val route: String,
    val icon: ImageVector,
    val title: String
)