package com.ssafy.d101.repository

import android.net.Uri
import com.ssafy.d101.api.ModelService
import com.ssafy.d101.api.UserService
import com.ssafy.d101.model.UserInfo
import com.ssafy.d101.model.YoloResponse
import com.ssafy.d101.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class ModelRepository @Inject constructor(private val modelService: ModelService) {
    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri = _imageUri.asStateFlow()

    private val _yoloInfo = MutableStateFlow<List<YoloResponse>?>(null)
    val yoloInfo = _yoloInfo.asStateFlow()

//    suspend fun TransferImageToYolo(fileUri: Uri) : List<YoloResponse> {
//
//    }

    suspend fun setImageUri(uri : Uri) {
        _imageUri.emit(uri)
    }
}