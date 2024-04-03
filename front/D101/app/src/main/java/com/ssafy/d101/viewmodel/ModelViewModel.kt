package com.ssafy.d101.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.d101.model.OCRResponse
import com.ssafy.d101.model.YoloResponse
import com.ssafy.d101.repository.ModelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ModelViewModel @Inject constructor(
    private val modelRepository: ModelRepository
) : ViewModel(){
    private val _ocrResponse = MutableStateFlow<OCRResponse?>(null)
    private val _yoloResponse = MutableStateFlow<List<YoloResponse>?>(null)
   // val yoloResponse = _yoloResponse.asStateFlow()

    fun transferImageToYolo(context : Context, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                // 이미지 Uri를 백엔드로 전송하고 결과를 받음
                val result = modelRepository.transferImageToYolo(context).first()
                _yoloResponse.value = result
                Log.d("model","$result")
              //  Log.d("model","$yoloResponse")
                // 성공적으로 분석이 완료되면 결과 화면으로 전환
                onResult(true)
            } catch (e: Exception) {
                Log.e("ModelViewModel", "분석 요청 실패: $e")
                _yoloResponse.emit(null)
                onResult(false) // 실패 시 처리
            }
        }
    }

    fun transferImageToOCR(context : Context, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            try {
                // 이미지 Uri를 백엔드로 전송하고 결과를 받음
                val result = modelRepository.transferImageToOCR(context).first()
                _ocrResponse.value = result
                Log.d("model","$result")
                //  Log.d("model","$yoloResponse")
                // 성공적으로 분석이 완료되면 결과 화면으로 전환
                onResult(true)
            } catch (e: Exception) {
                Log.e("ModelViewModel", "분석 요청 실패: $e")
                _ocrResponse.emit(null)
                onResult(false) // 실패 시 처리
            }
        }
    }

    fun getOCRResponse() = modelRepository.ocrInfo

    fun getYoloResponse() = modelRepository.yoloInfo

    fun getImageUri() = modelRepository.imageUri

    fun getOption() = modelRepository.option
    fun getContext() = modelRepository.context

    fun setImageUri(uri : Uri) {
        viewModelScope.launch {
            modelRepository.setImageUri(uri)
        }
    }

    fun setOption(option:Boolean) {
        viewModelScope.launch {
            modelRepository.setOption(option)
        }
    }

    fun setContext(context:Context) {
        viewModelScope.launch{
            modelRepository.setContext(context)
        }
    }

    fun deleteYoloResponseItem(index: Int) {
        viewModelScope.launch {
            modelRepository.deleteYoloResponseItem(index)
        }
    }

    fun updateFoodItem(index: Int, newName: String, newCarbs: Double, newProtein: Double, newFat: Double, newCal : Int) {
        viewModelScope.launch{
            modelRepository.updateFoodItem(index,newName, newCarbs, newProtein, newFat, newCal)
        }
    }

    fun setInit() {
        viewModelScope.launch{
            modelRepository.initResult()
        }
    }
}