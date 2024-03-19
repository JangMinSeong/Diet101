package com.ssafy.d101.ui.view.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.material.icons.*
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
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
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    var items = listOf(
        BottomNavItem("home", Icons.Default.Home, "Home"),
        BottomNavItem("addFood", Icons.Default.AddCircle, "AddFood"),
        BottomNavItem("myPage", Icons.Default.Person, "MyPage")
    )
//    var selectedItem by remember { mutableIntStateOf(0) }
    var showDialog by remember { mutableStateOf(false) }

    // NavController의 현재 목적지를 관찰
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    // 현재 라우트에 따라 selectedItem 업데이트
    var selectedItem by remember { mutableIntStateOf(0) }
    selectedItem = items.indexOfFirst { it.route == currentRoute }.coerceAtLeast(0)

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
                        println(item.route)
                        selectedItem = index
                        navController.navigate(item.route)
                    }
                }
            )
        }
    }
}