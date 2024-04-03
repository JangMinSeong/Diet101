package com.ssafy.d101.ui.view.screens
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Color
import com.ssafy.d101.R
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.ssafy.d101.viewmodel.ModelViewModel

@Composable
fun AiAnalysisScreen(navController: NavController) {
    val modelViewModel : ModelViewModel = hiltViewModel()
    val analysisYoloResult by modelViewModel.getYoloResponse().collectAsState(null)
    val analysisOCRResult by modelViewModel.getOCRResponse().collectAsState(null)
    val option by modelViewModel.getOption().collectAsState()
    val context by modelViewModel.getContext().collectAsState(null)

    LaunchedEffect(option) {
        if(option == true) { //yolo
            // 이미지 분석 시작
            context?.let {
                modelViewModel.transferImageToYolo(it) { isSuccess ->
                    if (isSuccess) {

                    } else {
                    }
                }
            }
        }
        else { //ocr
            // 이미지 분석 시작
            context?.let {
                modelViewModel.transferImageToOCR(it) { isSuccess ->
                    if (isSuccess) {
                    } else {
                    }
                }
            }
        }
    }

    LaunchedEffect(analysisOCRResult) {
        if(option == false) {
            Log.d("in loading", "$analysisOCRResult")
            analysisOCRResult?.let {
                Log.d("in loading", analysisOCRResult.toString())
                //TODO : ocr 수정페이지로 이동
                 navController.navigate("ocrResult")
            }
        }
    }
    // 분석 결과가 준비되면 결과 화면으로 전환
    LaunchedEffect(analysisYoloResult) {
        if(option == true) {
            Log.d("in loading", "$analysisYoloResult")
            analysisYoloResult?.let {
                Log.d("in loading", "aaaaa")
                navController.navigate("analysisResult")
            }
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFFCE8))
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 30.dp, top = 16.dp, bottom = 30.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            Image(
                painter = painterResource(id = R.drawable.previous),
                contentDescription = "Previous Button",
                modifier = Modifier
                    .size(60.dp)
                    .padding(end = 16.dp)
            )
        }

        // 분석중 화면
        Column(
            modifier = Modifier
                .align(Alignment.Center)
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // "AI 분석중 ..." 텍스트
            Text(
                text = "AI 분석중 ....",
                textAlign = TextAlign.Center,
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF004D40),
            )
            Spacer(modifier = Modifier.height(40.dp))
            // 로딩 상태
            IndeterminateCircularIndicator()
        }
    }
}



@Composable
fun IndeterminateCircularIndicator() {
    val loading = remember { mutableStateOf(true) }

    // 로딩 상태 활성화
    if (loading.value) {
        CircularProgressIndicator(
            modifier = Modifier.size(64.dp),
            color = Color.Black,
            trackColor = MaterialTheme.colorScheme.surfaceVariant
        )
    }
}