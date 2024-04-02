package com.ssafy.d101.ui.view.screens

import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.ssafy.d101.model.OCRRequest

import com.ssafy.d101.ui.theme.Ivory
import com.ssafy.d101.ui.theme.White
import com.ssafy.d101.viewmodel.ModelViewModel
import com.ssafy.d101.viewmodel.OCRViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun OCRResultScreen(navController: NavController) {

    val ocrViewModel: OCRViewModel = hiltViewModel()

    val modelViewModel : ModelViewModel = hiltViewModel()
    val imageUri by modelViewModel.getImageUri().collectAsState()
    val ocrInfo by modelViewModel.getOCRResponse().collectAsState()
    var calories by remember { mutableStateOf(if (ocrInfo?.calorie!! == -1) "" else ocrInfo?.calorie.toString()) }
    var carbohydrate by remember { mutableStateOf(if (ocrInfo?.carbohydrate!! == -1.0) "" else ocrInfo?.carbohydrate.toString()) }
    var sugar by remember { mutableStateOf(if (ocrInfo?.sugar!! == -1.0) "" else ocrInfo?.sugar.toString()) }
    var protein by remember { mutableStateOf(if (ocrInfo?.protein!! == -1.0) "" else ocrInfo?.protein.toString()) }
    var fat by remember { mutableStateOf(if (ocrInfo?.fat!! == -1.0) "" else ocrInfo?.fat.toString()) }
    var saturated by remember { mutableStateOf(if (ocrInfo?.saturated!! == -1.0) "" else ocrInfo?.saturated.toString()) }
    var trans by remember { mutableStateOf(if (ocrInfo?.trans!! == -1.0) "" else ocrInfo?.trans.toString()) }
    var cholesterol by remember { mutableStateOf(if (ocrInfo?.cholesterol!! == -1.0) "" else ocrInfo?.cholesterol.toString()) }
    var sodium by remember { mutableStateOf(if (ocrInfo?.sodium!! == -1.0) "" else ocrInfo?.sodium.toString()) }
    var name by remember { mutableStateOf("") }
    var type by remember { mutableIntStateOf(0) }
    val typeToString = when (type){
        0 -> "BREAKFAST"
        1 -> "BRUNCH"
        2 -> "LUNCH"
        3 -> "LINNER"
        4 -> "DINNER"
        5 -> "NIGHT"
        6 -> "SNACK"
        7 -> "BEVERAGE"
        8 -> "ALCOHOL"
        else -> "몰라"
    }
    Log.d("ocrInfo",ocrInfo.toString())

    fun saveOCR() {
        ocrViewModel.saveOCR(
            OCRRequest(
            name,typeToString,
            LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),
            calories.toInt(),
            carbohydrate.toDouble(),
            protein.toDouble(),
            fat.toDouble()
            )
        )
        navController.navigate("ocrResist")
    }

    Column( modifier = Modifier // 백그라운드
        .fillMaxSize()
        .background(Ivory)
    ){
        OCRImage(uri = imageUri!!)
        LazyColumn (
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp, 10.dp, 30.dp, 30.dp)
                .height(400.dp)
                .shadow(15.dp, RoundedCornerShape(12.dp))
                .background(White, shape = RoundedCornerShape(12.dp)),
            verticalArrangement = Arrangement.Center
        ){
            item{
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp, 20.dp, 0.dp)){
                    Box(modifier=Modifier.weight(1f)) {
                        Text(
                            text = "영양 정보", style = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,
                                shadow = Shadow(
                                    color = Color.Gray,
                                    offset = Offset(10f, 10f),
                                    blurRadius = 8f
                                )
                            )

                        )
                    }
                    Button(
                        onClick = {saveOCR() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFB6B284), // 버튼 배경색
                            contentColor = Color.White // 버튼 텍스트색
                        ),
                        // 버튼의 크기를 설정
                        modifier = Modifier.size(width = 80.dp, height = 35.dp)
                    ) {
                        Text(
                            text = "저장", style = TextStyle(
                                fontWeight = FontWeight.Bold,
                                fontSize = 15.sp,
                                color = Color.White // 색상도 설정할 수 있음
                            )
                        )
                    }
                }
                Divider(modifier = Modifier.padding(20.dp,10.dp,20.dp,0.dp), color = Color.Gray, thickness = 1.dp)
                Column() {
                    OCRInfoInputField(
                        title = "칼로리",
                        unit = "kcal",
                        value = calories,
                        onValueChange = { new ->
                            calories = new.filter { it.isDigit() }
                        })
                    OCRInfoInputField(
                        title = "탄수화물",
                        unit = "g",
                        value = carbohydrate,
                        onValueChange = { new ->
                            carbohydrate = new.filter { it.isDigit() || it == '.' }
                        })
                    OCRInfoInputField(
                        title = "당류",
                        unit = "g",
                        value = sugar,
                        onValueChange = { new ->
                            sugar = new.filter { it.isDigit() || it == '.' }
                        })
                    OCRInfoInputField(
                        title = "단백질",
                        unit = "g",
                        value = protein,
                        onValueChange = { new ->
                            protein = new.filter { it.isDigit() || it == '.' }
                        })
                    OCRInfoInputField(
                        title = "지방",
                        unit = "g",
                        value = fat,
                        onValueChange = { new ->
                            fat = new.filter { it.isDigit() || it == '.' }
                        })
                    OCRInfoInputField(
                        title = "포화 지방",
                        unit = "g",
                        value = saturated,
                        onValueChange = { new ->
                            saturated = new.filter { it.isDigit() || it == '.' }
                        })
                    OCRInfoInputField(
                        title = "트랜스 지방",
                        unit = "g",
                        value = trans,
                        onValueChange = { new ->
                            trans = new.filter { it.isDigit() || it == '.' }
                        })
                    OCRInfoInputField(
                        title = "콜레스테롤",
                        unit = "mg",
                        value = cholesterol,
                        onValueChange = { new ->
                            cholesterol = new.filter { it.isDigit() || it == '.' }
                        }
                    )
                    OCRInfoInputField(
                        title = "나트륨",
                        unit = "mg",
                        value = sodium,
                        onValueChange = { new ->
                            sodium = new.filter { it.isDigit() || it == '.' }
                        }
                    )
                }

                // 저장
                Row (modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp, 20.dp, 20.dp, 0.dp)){
                    Box(modifier=Modifier.weight(1f)) {
                        Text(
                            text = "추가 정보", style = TextStyle(
                                color = Color.Black,
                                fontWeight = FontWeight.Bold,
                                fontSize = 21.sp,
                                shadow = Shadow(
                                    color = Color.Gray,
                                    offset = Offset(10f, 10f),
                                    blurRadius = 8f
                                )
                            )

                        )
                    }
                }
                Divider(modifier = Modifier.padding(20.dp,10.dp,20.dp,0.dp), color = Color.Gray, thickness = 1.dp)
                Column() {
                    InfoInputTextField(title = "식품명", unit = "", value = name, onValueChange = { new -> name=new })
                    TypeDropDownList(title = "분류", activityIndex = type, onItemClick = { type=it})
                }
            }
        }
    }
}

@Composable
fun OCRInfoInputField(title: String, unit: String, value: String, onValueChange: (String) -> Unit) {
    Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)) {
        Box(modifier=Modifier.width(100.dp)){Text(text = title, style = titleTextStyle)}
        Spacer(modifier = Modifier.width(10.dp))
        InputField(value = value, onValueChange = onValueChange)
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = unit, style = titleTextStyle)
    }
}

@Composable
fun OCRImage(uri: Uri) {
    Row(modifier = Modifier.fillMaxWidth(), // Row를 부모의 최대 너비로 확장
        horizontalArrangement = Arrangement.Center ) {
        Image(
            painter = rememberAsyncImagePainter(model = uri),
            contentDescription = "성분표 사진",
            modifier = Modifier
                .size(300.dp)
                .padding(top = 8.dp)
        )
    }
}

@Composable
fun InputTextField(value: String, onValueChange: (String) -> Unit) {
    BasicTextField(
        value = value,
        onValueChange = onValueChange,
        singleLine = true,
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .height(IntrinsicSize.Min)
                    .width(120.dp)
                    .border(width = 2.dp, color = Color.Black, shape = RoundedCornerShape(12.dp))
                    .padding(horizontal = 8.dp, vertical = 6.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Box(modifier = Modifier.padding(horizontal = 5.dp)){innerTextField()}
            }
        }
    )
}

@Composable
fun InfoInputTextField(title: String, unit: String, value: String, onValueChange: (String) -> Unit) {
    Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)) {
        Box(modifier=Modifier.width(100.dp)){Text(text = title, style = titleTextStyle)}
        Spacer(modifier = Modifier.width(10.dp))
        InputTextField(value = value, onValueChange = onValueChange)
        Spacer(modifier = Modifier.width(5.dp))
        Text(text = unit, style = titleTextStyle)
    }
}

@Composable
fun TypeDropDownList(title: String, activityIndex: Int, onItemClick: (Int) -> Unit) {
    val items = listOf("아침", "아점", "점심", "점저", "저녁", "야식", "간식", "음료", "주류")
    Row(modifier = Modifier.padding(horizontal = 20.dp, vertical = 5.dp)) {
        Box(modifier=Modifier.width(80.dp)){Text(text = title, style = titleTextStyle)}
        Spacer(modifier = Modifier.width(30.dp))
        DropdownList(itemList = items, selectedIndex = activityIndex, modifier = Modifier.width(120.dp), onItemClick = onItemClick)
    }
}

@Preview
@Composable
fun OCRResultScreenPreview() {
    val navController = rememberNavController()
    OCRResultScreen(navController)
}