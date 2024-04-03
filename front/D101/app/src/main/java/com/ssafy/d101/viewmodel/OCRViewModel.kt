package com.ssafy.d101.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.d101.model.DailyNutrient
import com.ssafy.d101.model.OCRInfo
import com.ssafy.d101.model.OCRRequest
import com.ssafy.d101.repository.OCRRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
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

        private val _dayOCR = MutableStateFlow<List<OCRInfo>?>(null)
        val dayOCR: StateFlow<List<OCRInfo>?> = _dayOCR.asStateFlow()
        fun loaDayOCR(date: String) {
            viewModelScope.launch {
                val result = ocrRepository.getDayOCR(date).first()
                _dayOCR.value = result
            }
        }

    private fun getTotalDayCalories(): Int {
        return _dayOCR.value?.sumOf { it.calorie } ?: 0
    }

    private fun getTotalDayCarbohydrate(): Double {
        return _dayOCR.value?.sumOf {it.carbohydrate } ?: 0.0
    }

    private fun getTotalDayProtein(): Double {
        return _dayOCR.value?.sumOf {it.protein } ?: 0.0
    }

    private fun getTotalDayFat(): Double {
        return _dayOCR.value?.sumOf {it.fat } ?: 0.0
    }

    private val _dailyOCRNutrient = MutableStateFlow<DailyNutrient?>(null)
    val dailyOCRNutrient: StateFlow<DailyNutrient?> = _dailyOCRNutrient.asStateFlow()

    fun refreshDailyOCRNutrient() {
        _dailyOCRNutrient.value = DailyNutrient(
            totalCalorie = getTotalDayCalories(),
            totalCarbohydrate = getTotalDayCarbohydrate(),
            totalProtein = getTotalDayProtein(),
            totalFat = getTotalDayFat()
        )
    }

}