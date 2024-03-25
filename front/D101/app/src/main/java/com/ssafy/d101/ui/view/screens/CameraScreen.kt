package com.ssafy.d101.ui.view.screens

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.core.content.ContextCompat
import android.Manifest
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.ui.platform.LocalContext

// 수정 필요
@androidx.compose.ui.tooling.preview.Preview(showBackground = true)
@Composable
fun CameraPreview() {
    val context = LocalContext.current
    val cameraPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission(),
        onResult = { isGranted: Boolean ->
            if (isGranted) {
                // 권한이 부여되었을 때 로직
                Log.d("CameraPreview", "Camera permission granted")
            } else {
                // 권한이 거부되었을 때 로직
                Toast.makeText(context, "Camera permission is required to use camera", Toast.LENGTH_SHORT).show()
            }
        }
    )

    // 카메라 권한이 이미 부여되었는지 확인
    val hasCameraPermission = ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.CAMERA
    ) == android.content.pm.PackageManager.PERMISSION_GRANTED

    if (!hasCameraPermission) {
        // 권한이 없으면 요청
        cameraPermissionLauncher.launch(Manifest.permission.CAMERA)
    } else {
        // 기존 카메라 프리뷰 로직...
    }
}