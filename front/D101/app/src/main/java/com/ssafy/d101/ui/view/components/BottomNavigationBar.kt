package com.ssafy.d101.ui.view.components

import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Divider
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.ssafy.d101.R
import com.ssafy.d101.navigation.Screens
import com.ssafy.d101.ui.view.screens.TextItem

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    var items = listOf(
        BottomNavItem(Screens.Home.route, Icons.Default.Home, "Home"),
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
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = {
                Text(
                    "등록하기",
                    modifier = Modifier
                        .padding(top = 15.dp, start = 10.dp),
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )
            },
            text = {
                Column {
                    Divider(thickness = 3.dp)
                    TextItem("일반 음식 등록하기", R.drawable.generalfood) {
                        showDialog = false
                        navController.navigate("foodResist")
                    }
                    TextItem("가공 음식 등록하기", R.drawable.processedfood) {
                        showDialog = false
                    }
                }
            },
            confirmButton = {

            },
        )
    }

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) }, // 이 부분은 각 항목에 맞는 아이콘으로 교체해야 할 수 있습니다.
                selected = selectedItem == index,
                onClick = {
                    // 'addFood' 아이템이 클릭되었을 때의 로직 처리
                    if (item.route == "addFood") {
                       showDialog = true

                        // 'foodResist' 경로로 네비게이션
//                        navController.navigate("foodResist") {
//                            // 현재 네비게이션 스택의 시작 위치로 popUp하여 중복된 네비게이션을 방지
//                            popUpTo(navController.graph.startDestinationId) {
//                                saveState = true
//                            }
//                            // 상태 복원을 위해 launchSingleTop 설정
//                            launchSingleTop = true
//                            restoreState = true
//                        }
                    } else {
                        println("Navigate to ${item.route}")
                        selectedItem = index
                        // 다른 아이템 클릭 시 해당 경로로 네비게이션
                        navController.navigate(item.route) {
                            // 현재 네비게이션 스택의 시작 위치로 popUp하여 중복된 네비게이션을 방지
                            popUpTo(navController.graph.startDestinationId) {
                                saveState = true
                            }
                            // 상태 복원을 위해 launchSingleTop 설정
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}