package com.ssafy.d101.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.d101.model.OCRRequest
import com.ssafy.d101.repository.OCRRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OCRViewModel @Inject constructor(
    private val ocrRepository: OCRRepository) : ViewModel() {

        fun saveOCR(ocrReq: OCRRequest) {
            viewModelScope.launch {
                ocrRepository.saveOCRFromBack(ocrReq)
            }
        }
}