package com.ssafy.d101.ui.view.components

import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.ssafy.d101.model.BottomNavItem

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<BottomNavItem> // BottomNavItem은 아래에서 설명
) {
    BottomAppBar {
        items.forEach { item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.title) },
                selected = item.route == navController.currentDestination?.route,
                onClick = {
                    navController.navigate(item.route)
                }
            )
        }
    }
}