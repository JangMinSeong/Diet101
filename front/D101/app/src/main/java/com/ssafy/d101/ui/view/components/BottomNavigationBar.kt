package com.ssafy.d101.ui.view.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material.icons.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavHostController
import com.ssafy.d101.model.BottomNavItem
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

@Composable
fun BottomNavigationBar(
    navController: NavHostController,
    items: List<BottomNavItem> // BottomNavItem은 아래에서 설명
) {
    var selectedItem by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    if (showDialog) {
        Dialog(onDismissRequest = { showDialog = false }) {
            Surface (
                modifier = Modifier.padding(8.dp),
                shape = MaterialTheme.shapes.medium
            ) {
                Text(text = "AddFood", modifier = Modifier.padding(16.dp))
            }
        }
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) }, // 이 부분은 각 항목에 맞는 아이콘으로 교체해야 할 수 있습니다.
                selected = selectedItem == index,
                onClick = {
                    if (item.route == "addFood") {
                        println("AddFood")
                        showDialog = true
                    } else {
                        println("Other")
                        selectedItem = index
                        navController.navigate(item.route)
                    }
                }
            )
        }
    }
}