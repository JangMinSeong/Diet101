package com.ssafy.d101.ui.view.components


import android.graphics.BitmapFactory
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.platform.LocalContext

@Composable
fun CroppedImagesDisplay(imageInfo: Map<String, Any>) {
    val context = LocalContext.current
    val resources = context.resources
    val density = resources.displayMetrics.density

    // 리소스에서 이미지 로드
    val resourceId = resources.getIdentifier(
        imageInfo["image_filename"] as String,
        "drawable",
        context.packageName
    )
    val bitmap = BitmapFactory.decodeResource(resources, resourceId)

    // JSON에서 받은 비율 정보
    val leftTop = imageInfo["left_top"] as List<Double>
    val widthRatio = imageInfo["width"] as Double
    val heightRatio = imageInfo["height"] as Double

    // 실제 이미지의 크기를 기준으로 좌표 및 크기 계산
    val originalWidth = bitmap.width
    val originalHeight = bitmap.height
    val xMin = (leftTop[0] * originalWidth).toFloat()
    val yMin = (leftTop[1] * originalHeight).toFloat()
    val width = (widthRatio * originalWidth).toFloat()
    val height = (heightRatio * originalHeight).toFloat()

    // Compose에서 사용할 크기를 dp 단위로 변환
    val widthDp = width / density
    val heightDp = height / density

    Canvas(modifier = Modifier.size(widthDp.dp, heightDp.dp)) {
        drawIntoCanvas { canvas ->
            val srcRect = android.graphics.Rect(
                xMin.toInt(),
                yMin.toInt(),
                (xMin + width).toInt(),
                (yMin + height).toInt()
            )
            val dstRect = android.graphics.Rect(
                0,
                0,
                size.width.toInt(),
                size.height.toInt()
            )
            canvas.nativeCanvas.drawBitmap(
                bitmap,
                srcRect,
                dstRect,
                null
            )
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    val imageInfo =
//        mapOf(
//            "image_filename" to "test3", // drawable에 test11111 이미지가 있어야 합니다.
//            "tag" to "삼계탕",
//            "left_top" to listOf(0.17829206766839956, 0.3150813583500129), // 원본 이미지에 대한 비율 기반 좌표
//            "width" to 0.43770721322098627, // 원본 이미지에 대한 비율 기반 너비
//            "height" to 0.5562760029942536 // 원본 이미지에 대한 비율 기반 높이
//        )
//    mapOf(
//        "image_filename" to "test3", // drawable에 test11111 이미지가 있어야 합니다.
//        "tag" to "밥",
//        "left_top" to listOf(0.7197671013398389, 0.46144979177427686), // 원본 이미지에 대한 비율 기반 좌표
//        "width" to 0.23638114314687889, // 원본 이미지에 대한 비율 기반 너비
//        "height" to 0.2949396811240961 // 원본 이미지에 대한 비율 기반 높이
//    )
    mapOf(
        "image_filename" to "test3", // drawable에 test11111 이미지가 있어야 합니다.
        "tag" to "수박",
        "left_top" to listOf(0.6996849466581475, 0.006072836079873329), // 원본 이미지에 대한 비율 기반 좌표
        "width" to 0.26781160148989314, // 원본 이미지에 대한 비율 기반 너비
        "height" to 0.26890098437790044 // 원본 이미지에 대한 비율 기반 높이
    )


    CroppedImagesDisplay(imageInfo = imageInfo)
}