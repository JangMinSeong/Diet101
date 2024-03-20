package com.ssafy.d101.ui.view.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.layout.*
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.material3.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.ui.graphics.Color
import com.ssafy.d101.R
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.LocalSoftwareKeyboardController

@Preview(showBackground = true)
@Composable
fun FoodAdditionScreen() {
    val dummyData = remember { mutableStateListOf("Apple", "Banana") }
    val searchData = remember { mutableStateListOf("Apple", "Banana", "Grape", "Tomato", "Strawberry") }
    var searchText by remember { mutableStateOf("") }
    val filteredItems = searchData.filter { it.startsWith(searchText, ignoreCase = true) }
    var expanded by remember { mutableStateOf(false) }
    var showDialog by remember { mutableStateOf(false) }
    var selectedFood by remember { mutableStateOf("") }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCE8))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 뒤로 가기 버튼 & 타이틀
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 60.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            // 뒤로 가기 버튼
            Image(
                painter = painterResource(id = R.drawable.previous),
                contentDescription = "Previous Button",
                modifier = Modifier
                    .size(40.dp)
                    .weight(1f)
                    .padding(end = 16.dp)
            )

            // 타이틀 : 음식 추가
            Text(
                text = "음식 추가",
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 10.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            Spacer(modifier = Modifier.weight(1f))
        }

        // 검색
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
        ) {
            // 돋보기 이미지
            Image(
                painter = painterResource(id = R.drawable.searchimage),
                contentDescription = "Search Image",
                modifier = Modifier
                    .size(40.dp)
                    .padding(end = 10.dp)
            )

            // 검색창, 검색 로직
            TextField(
                value = searchText,
                onValueChange = {
                    searchText = it
                    expanded = it.isNotEmpty()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                placeholder = { Text("음식명 입력", fontSize = 14.sp) },
                singleLine = true,
            )

            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth()
            ) {
                filteredItems.forEach { label ->
                    DropdownMenuItem(
                        text = { Text(label) },
                        onClick = {
                            searchText = label
                            expanded = false
                        }
                    )
                }
            }
        }

        // 내가 추가한 음식
        Text(
            text = "내가 추가한 음식",
            modifier = Modifier
                .padding(top = 40.dp, bottom = 10.dp, start = 30.dp)
                .fillMaxWidth(),
            textAlign = TextAlign.Start,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
        )

        // 실선
        Box(
            modifier = Modifier
                .fillMaxWidth(0.9f)
                .height(1.dp)
                .background(Color.Gray)
                .padding(vertical = 45.dp)
        )

        if (dummyData.isNotEmpty()) {
            // 추가한 음식
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .background(Color.White),
            ) {
                items(dummyData) { food ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(vertical = 8.dp)
                            .clickable {
                                selectedFood = food
                                showDialog = true
                            }
                    ) {
                        Text(
                            text = food,
                            fontSize = 16.sp,
                            modifier = Modifier
                                .weight(1f)
                        )
                        // 선택 유무
                        SwitchWithIconExample()

                        // 삭제 버튼
                        CancelButtonExample(onClick = {
                            dummyData.remove(food)
                        })
                    }
                    Divider(color = Color.Gray)
                }
            }
            // AlertDialog 로직
            var text by remember { mutableStateOf("") } // 사용자 입력 관리 상태 변수
            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    title = {
                        Text(
                            text = "$selectedFood"
                        )
                    },

                    text = {
                        Column {
                            Text(
                                text = "1회 제공량 50(g, ml)",
                                modifier = Modifier
                                    .padding(bottom = 10.dp)
                            )

                            // 먹은양
                            Text(
                                text = "먹은양",
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight.Bold,
                                color = Color.Gray,
                            )

                            // 먹은 양 입력 상자
                            TextField(
                                value = text,
                                onValueChange = { text = it },
                                modifier = Modifier
                                    .width(100.dp)
                                    .height(65.dp)
                                    .padding(bottom = 15.dp),
                                singleLine = true,
                                placeholder = { Text(text = "", fontSize = 10.sp) },
                                textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Center),
                            )

                            // 영양정보 박스
                            Box(
                                modifier = Modifier
                                    .width(450.dp)
                                    .height(160.dp)
                                    .background(Color.LightGray, shape = RoundedCornerShape(8.dp))
                                    .padding(8.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Column(
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Top
                                ) {
                                    Text(
                                        "192kcal",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold,
                                        modifier = Modifier
                                            .align(Alignment.Start)
                                            .padding(start = 35.dp)
                                    )

                                    Spacer(modifier = Modifier.height(5.dp))  // 텍스트와 선 사이 공간

                                    // 실선
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.8f)
                                            .height(3.dp)
                                            .background(Color.Gray)
                                            .padding(vertical = 45.dp)
                                    )

                                    // 탄단지 명칭
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 20.dp),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // 탄수화물
                                        Text(
                                            text = "탄수화물",
                                            color = Color.Gray,
                                            fontSize = 18.sp,
                                            modifier = Modifier.padding(end = 16.dp)
                                        )
                                        // 단백질
                                        Text(
                                            text = "단백질",
                                            color = Color.Gray,
                                            fontSize = 18.sp,
                                            modifier = Modifier.padding(end = 25.dp)
                                        )
                                        // 지방
                                        Text(
                                            text = "지방",
                                            color = Color.Gray,
                                            fontSize = 18.sp,
                                        )
                                    }

                                    // 탄단지 그램 수
                                    Row(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(top = 12.dp),
                                        horizontalArrangement = Arrangement.Center,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // 탄수화물 그램
                                        Text(
                                            text = "100g",
                                            color = Color.Black,
                                            fontSize = 15.sp,
                                            modifier = Modifier.padding(start = 15.dp, end = 40.dp)
                                        )
                                        // 단백질 그램
                                        Text(
                                            text = "100g",
                                            color = Color.Black,
                                            fontSize = 15.sp,
                                            modifier = Modifier.padding(end = 33.dp)
                                        )
                                        // 지방 그램
                                        Text(
                                            text = "200g",
                                            color = Color.Black,
                                            fontSize = 15.sp,
                                        )
                                    }
                                }
                            }
                        }
                    },

                    confirmButton = {
                        Button(
                            onClick = {

                            }
                        ) {
                            Text("수정")
                        }
                    },
                    dismissButton = {
                        Button(onClick = { showDialog = false }) {
                            Text("취소")
                        }
                    }
                )
            }
        }

        // dummyData가 비어있을 때만 표시
        if (dummyData.isEmpty()) {
            // 수저 포크 사진
            Image(
                painter = painterResource(id = R.drawable.fork),
                contentDescription = "Signature Image",
                modifier = Modifier
                    .padding(top = 60.dp, bottom = 40.dp)
                    .width(120.dp)
                    .height(120.dp)
            )

            // 최근 추가한 음식이 없어요
            Text(
                text = "최근 추가한 음식이 없어요",
                modifier = Modifier.padding(bottom = 10.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )

            // 최근 식사하신 음식을 보여주세요
            Text(
                text = "최근 식사하신 음식을 보여주세요",
                modifier = Modifier,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Gray,
            )
        }
    }
}

@Composable
// 음식 선택 유무
fun SwitchWithIconExample() {
    var checked by remember { mutableStateOf(true) }

    Switch(
        checked = checked,
        onCheckedChange = {
            checked = it
        },
        thumbContent = if (checked) {
            {
                Icon(
                    imageVector = Icons.Filled.Check,
                    contentDescription = null,
                    modifier = Modifier.size(SwitchDefaults.IconSize),
                )
            }
        } else {
            null
        }
    )
}

@Composable
// 취소 버튼
fun CancelButtonExample(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(50.dp)
            .height(33.dp)
            .padding(start = 5.dp, end = 5.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text("X", fontSize = 20.sp)
    }
}

@Composable
// 추가 버튼
fun AdditionButtonExample(onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .width(50.dp)
            .height(33.dp)
            .padding(start = 5.dp, end = 5.dp),
        contentPadding = PaddingValues(0.dp)
    ) {
        Text("X", fontSize = 20.sp)
    }
}