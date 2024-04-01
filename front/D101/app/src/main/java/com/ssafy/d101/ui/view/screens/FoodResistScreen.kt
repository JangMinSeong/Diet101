package com.ssafy.d101.ui.view.screens

import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.ssafy.d101.viewmodel.ModelViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Date
import java.util.Locale


@Preview(showBackground = true)
@Composable
fun FoodResistScreen(navController: NavHostController) {
    val currentDate = remember { LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")) }
    // 선택된 항목을 추적하는 상태 변수
    var selectedMeal by remember { mutableStateOf<String?>(null) }
    var showDialog by remember { mutableStateOf(false) }    // 섭취한 음식 사진 첨부 상태 관리
    val scrollState = rememberScrollState()

    // 사용자가 항목을 선택하거나 선택을 취소하는 로직
    val onMealSelected: (String) -> Unit = { meal ->
        selectedMeal = if (selectedMeal == meal) null else meal
    }

    val modelViewModel : ModelViewModel = hiltViewModel()


    val context = LocalContext.current
    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val imageFile = File(context.getExternalFilesDir(null), "food_image_${System.currentTimeMillis()}.jpg")
    var contentUri by remember { mutableStateOf<Uri?>(null) }

    // 선택된 항목이 있는지 확인하는 함수
    val isItemSelected: (String) -> Boolean = { it == selectedMeal }

    //////음식 사진 카메라
    val launcherForCameraFood = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            // 사진 촬영이 성공했을 때, 이미 할당된 contentUri를 imageUri에 할당
            imageUri = contentUri
            Log.d("Camera", "Photo saved to $imageUri")

        } else {
            Log.e("Camera", "Failed to capture photo")
        }
    }
//    val launcherForCameraFood = rememberLauncherForActivityResult(ActivityResultContracts.TakePicturePreview()) { bitmap ->
//        // 카메라에서 받아온 이미지 처리. bitmap이 null일 수 있으니 null 체크 필요
//
//    }

    //////음식 사진 갤러리
    val launcherForGalleryFood = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        // 갤러리에서 선택한 이미지 처리. uri가 null일 수 있으니 null 체크 필요
        if (uri != null) {
            Log.d("uri","$uri")
            Log.d("imageUri","$imageUri")
            imageUri = uri
            Log.d("ddd","$imageUri")
        }else {
            Log.d("????","$imageUri")
        }
    }

    val takePicture = {
        val fileName = "IMG_${SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())}.jpg"
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, fileName)
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_PICTURES + File.separator + "D101")
        }

        contentUri = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)

        contentUri?.let { uri ->
            launcherForCameraFood.launch(uri)
        }
    }

    // 화면 전체
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCE8))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        // 화면 상단 - 뒤로 가기 버튼 & 타이틀 화면
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 30.dp),
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

            // 카테고리
            Text(
                text = "음식 등록",
                modifier = Modifier
                    .weight(2f)
                    .padding(start = 10.dp),
                textAlign = TextAlign.Center,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier
                .weight(1f)
                .padding(bottom = 10.dp))
        }

        // 오늘은 어떤 음식을 드셨나요?
        Text(
            text = "오늘은 어떤 음식을 드셨나요?",
            modifier = Modifier
                .weight(2f)
                .padding(start = 10.dp),
            textAlign = TextAlign.Center,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF004D40),
        )

        // 섭취한 음식 사진 첨부 AlertDialog 표시 로직
        if (showDialog) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = {
                    Text(
                        "첨부하기",
                        modifier = Modifier
                            .padding(top = 15.dp, start = 10.dp),
                        fontWeight = FontWeight.Bold,
                    )
                },
                text = {
                    Column {
                        Divider(thickness = 3.dp)
                        TextItem("카메라", R.drawable.camera) {
                            // 카메라 로직
                            showDialog = false
                          //  launcherForCameraFood.launch(contentUri)
                            takePicture()
                        }
                        TextItem("갤러리", R.drawable.gallery) {
                            // 갤러리 아이템 클릭 시 수행할 로직
                            showDialog = false
                            launcherForGalleryFood.launch("image/*")
                        }
                        TextItem("내가 추가한 음식", R.drawable.minefood) {
                            showDialog = false
                            navController.navigate("foodAddition")
                        }
                    }
                },
                confirmButton = {

                },
            )
        }

        Column (
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFFFFCE8))
                .padding(16.dp)
                .verticalScroll(scrollState),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // 사진 등록 배경
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp,)
                    .background(Color(0x3700897B), RoundedCornerShape(10.dp))
                    .width(200.dp)
                    .height(600.dp),
                contentAlignment = Alignment.Center
            ) {
                Column (
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    // 날짜
                    Text(
                        text = currentDate,
                        color = Color.Black,
                        fontSize = 21.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(top = 10.dp, bottom = 30.dp)
                    )

                    // 섭취한 음식 사진 타이틀
                    Text(
                        text = "섭취한 음식 사진",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 8.dp)
                    )

                    // 섭취한 음식 사진 첨부 공간
                    Image(
                        painter = if (imageUri != null) {
                            rememberAsyncImagePainter(model = imageUri)
                        } else {
                            painterResource(id = R.drawable.file) // 사진이 없을 때의 플레이스홀더
                        },
                        contentDescription = "섭취한 음식 사진",
                        modifier = Modifier
                            .size(150.dp)
                            .padding(top = 8.dp)
                            .clickable(onClick = { showDialog = true })
                    )


                    Text("분류", fontSize = 18.sp, fontWeight = FontWeight.Bold, modifier = Modifier
                        .align(Alignment.Start)
                        .padding(start = 35.dp, top = 10.dp))
                    listOf("아침", "아점", "점심", "점저", "저녁", "야식", "간식", "음료", "주류").chunked(3).forEach { chunk ->
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp), horizontalArrangement = Arrangement.SpaceEvenly) {
                            chunk.forEach { meal ->
                                OutlinedButton(
                                    onClick = { onMealSelected(meal) },
                                    border = BorderStroke(if (isItemSelected(meal)) 4.dp else 1.dp, Color.Black),
                                    modifier = Modifier
                                        .width(90.dp)
                                ) {
                                    Text(meal, fontWeight = FontWeight.Bold, color = if (isItemSelected(meal)) Color.Black else Color.Black)
                                }
                            }
                        }
                    }

                    // "등록하기" 버튼 추가
                    Button(
                        onClick = {
                            imageUri?.let { uri ->
                                // ViewModel을 통해 이미지 URI 설정
                                modelViewModel.setImageUri(uri)
                                // 이미지 분석 시작
                                modelViewModel.transferImageToYolo(context) { isSuccess ->
                                    if (isSuccess) {
                                        // 분석 성공 시 로딩 화면으로 넘어감
                                        navController.navigate("ailoading")
                                    } else {
                                        // 실패 처리 로직 (에러 메시지 표시 등)
                                    }
                                }
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.Black),
                        modifier = Modifier
                            .width(130.dp)
                            .height(60.dp)
                            .padding(top = 20.dp),
                    ) {
                        Text(
                            text = "등록하기",
                            color = Color.White,
                            fontSize = 16.sp,
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.height(56.dp))
        }
    }
}

@Composable
fun TextItem(text: String, @DrawableRes iconRes: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = iconRes),
            contentDescription = "Icon for $text",
            modifier = Modifier
                .size(50.dp)
                .padding(start = 16.dp, top = 15.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = text,
            fontSize = 18.sp,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .padding(start = 8.dp, top = 15.dp)
        )
    }
}
