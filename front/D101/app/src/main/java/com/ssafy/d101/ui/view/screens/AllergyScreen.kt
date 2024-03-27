package com.ssafy.d101.ui.view.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.ssafy.d101.R
import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.theme.White
import com.ssafy.d101.ui.view.components.BackHeader

@Composable
fun AllergyScreen(navController: NavHostController) {
    Column( modifier = Modifier // 백그라운드
        .fillMaxSize()
        .background(Ivory)
    ) {
        BackHeader("알레르기 등록", navController)
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 30.dp, 30.dp, 30.dp)
                .shadow(15.dp, RoundedCornerShape(12.dp))
                .background(White, shape = RoundedCornerShape(12.dp))
        ) {
            SearchAllergy()
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun SearchAllergy() {
    val items = listOf("난류","우유","메밀","땅콩","대두","밀","잣","호두","게",
        "새우","오징어","고등어","조개류","복숭아","토마토","닭고기","돼지고기","소고기","아황산류")
    val images = listOf(
        R.drawable.egg,
        R.drawable.milk,
        R.drawable.buckwheat,
        R.drawable.peanuts,
        R.drawable.soybeans,
        R.drawable.wheat,
        R.drawable.pinenuts,
        R.drawable.walnuts,
        R.drawable.crab,
        R.drawable.shrimp,
        R.drawable.squid,
        R.drawable.mackerel,
        R.drawable.shellfish,
        R.drawable.peach,
        R.drawable.tomato,
        R.drawable.chicken,
        R.drawable.pork,
        R.drawable.beef,
        R.drawable.sulphites
    )

    var selectedIndex by remember { mutableIntStateOf(0) }
    var myAllergy by remember { mutableStateOf(mutableListOf(0, 1, 7, 8, 9, 10, 11)) }

     Row(modifier = Modifier.padding(20.dp,20.dp,0.dp,10.dp)) {
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = R.drawable.search),
            contentDescription = null
        )
         Spacer(modifier = Modifier.padding(horizontal = 7.dp))
        Text(text = allergyText)
    }
    Row(modifier = Modifier.padding(30.dp,0.dp,0.dp,10.dp)){
        var optionModifier = Modifier.width(200.dp)
        Box(modifier=Modifier.padding(0.dp,11.dp)) {
            DropdownList(itemList = items, selectedIndex = selectedIndex, modifier = optionModifier, onItemClick = {selectedIndex = it})
        }
        Box(){
            IconButton(onClick = {
                if (!myAllergy.contains(selectedIndex)) {
                    myAllergy = myAllergy.toMutableList().apply { add(selectedIndex) }
                }
            }) {
                Icon(
                    imageVector = Icons.Default.CheckCircle,
                    contentDescription = "Back Button"
                )
            }
        }
    }

    Divider(modifier = Modifier.padding(20.dp,10.dp,20.dp,0.dp), color = Color.Gray, thickness = 1.dp)

    Row(modifier = Modifier.padding(20.dp)){
        Icon(
            modifier = Modifier.size(20.dp),
            painter = painterResource(id = R.drawable.hand),
            contentDescription = null
        )
        Spacer(modifier = Modifier.padding(horizontal = 7.dp))
        Text(text = "내 알레르기 정보", style = TextStyle(color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold))
    }

    FlowRow (
        modifier = Modifier.padding(30.dp,0.dp,30.dp,20.dp)
    ) {
        myAllergy.forEach {item ->
            AllergyItem(icon = images[item], onRemoveClick = { myAllergy = myAllergy.toMutableList().apply { remove(item) } }, name = items[item])
        }
    }

}

@Composable
fun AllergyItem(icon: Int, onRemoveClick: () -> Unit, name: String) {
    Box(modifier = Modifier.padding(20.dp,0.dp)){
         Column(horizontalAlignment = Alignment.CenterHorizontally) {
             IconButton(
                 onClick = onRemoveClick,
                 modifier = Modifier
                     .size(15.dp)
                     .align(Alignment.End)
             ) {
                 Icon(
                     painter = painterResource(id = R.drawable.cancel),
                     contentDescription = "Remove",
                 )
             }
             Image(
                 modifier = Modifier
                     .size(50.dp)
                     .padding(0.dp, 0.dp, 5.dp, 0.dp),
                 painter = painterResource(id = icon),
                 contentDescription = null
             )
             Text(text = name, style = TextStyle(fontSize = 10.sp, fontWeight = FontWeight.Bold))
         }
    }
}

@Composable
fun DropdownList(itemList: List<String>, selectedIndex: Int, modifier: Modifier, onItemClick: (Int) -> Unit) {

    var showDropdown by rememberSaveable { mutableStateOf(false) }
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {

        // button
        Box(
            modifier = modifier
                .clickable { showDropdown = true }
                .border(
                    BorderStroke(2.dp, Color.Black), // 테두리 굵기와 색상 설정
                    shape = RoundedCornerShape(5.dp) // 모서리 둥근 정도 설정
                ),
//            .clickable { showDropdown = !showDropdown },
            contentAlignment = Alignment.Center
        ) {
            Text(text = itemList[selectedIndex], modifier = Modifier.padding(3.dp))
        }

        // dropdown list
        Box() {
            if (showDropdown) {
                Popup(
                    alignment = Alignment.TopCenter,
                    properties = PopupProperties(
                        excludeFromSystemGesture = true,
                    ),
                    // to dismiss on click outside
                    onDismissRequest = { showDropdown = false }
                ) {
                    Box(modifier=Modifier.border(
                        BorderStroke(2.dp, Color.Black), // 테두리 굵기와 색상 설정
                        shape = RoundedCornerShape(5.dp) // 모서리 둥근 정도 설정
                    )){
                        Column(
                            modifier = modifier
                                .heightIn(max = 90.dp)
                                .verticalScroll(state = scrollState),
                            horizontalAlignment = Alignment.CenterHorizontally,
                        ) {

                            itemList.onEachIndexed { index, item ->
                                if (index != 0) {
                                    Divider(thickness = 1.dp, color = Color.LightGray)
                                }
                                Box(
                                    modifier = Modifier
                                        .height(30.dp)
                                        .background(Color.White)
                                        .fillMaxWidth()
                                        .clickable {
                                            onItemClick(index)
                                            showDropdown = !showDropdown
                                        },
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(text = item,)
                                }
                            }

                        }
                    }

                }
            }
        }
    }
}


val allergyText = buildAnnotatedString {
    withStyle(style = SpanStyle(color = Color.Black, fontSize = 15.sp, fontWeight = FontWeight.Bold)) {
        append("알레르기 검색 - ")
    }
    withStyle(style = SpanStyle(color = Color.Gray, fontSize = 12.sp, fontWeight = FontWeight.Bold)) {
        append("식약처 지정 19종")
    }
}

//@Preview(showSystemUi = true)
//@Composable
//fun previewTest(){
//    val navController = rememberNavController()
//    AllergyScreen(navController = navController)
//}