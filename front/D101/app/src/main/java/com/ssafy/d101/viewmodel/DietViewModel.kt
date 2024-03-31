package com.ssafy.d101.viewmodel;

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.d101.api.DietService
import com.ssafy.d101.api.UserService
import com.ssafy.d101.model.AnalysisDiet
import com.ssafy.d101.model.DietInfo
import com.ssafy.d101.model.User
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class DietViewModel @Inject constructor(private val dietService: DietService) : ViewModel() {
    fun getCurrentDate(): String {
        return LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    fun getStartOfWeek(): String {
        return LocalDate.now().with(TemporalAdjusters.previousOrSame(java.time.DayOfWeek.MONDAY))
            .format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    fun getEndOfWeek(): String {
        return LocalDate.now().with(TemporalAdjusters.nextOrSame(java.time.DayOfWeek.SUNDAY))
            .format(DateTimeFormatter.ISO_LOCAL_DATE)
    }


    private val _analysisDiet = MutableStateFlow<AnalysisDiet?>(null)
    val resultDiet = _analysisDiet.asStateFlow()

    fun analysisDiet() {
        val date = getCurrentDate()
        val dateFrom = getStartOfWeek()
        val dateTo = getEndOfWeek()

        viewModelScope.launch {
            try {
                val analysisDiet = dietService.getWeekDiet(date, dateFrom, dateTo)
                _analysisDiet.emit(analysisDiet) // 직접 StateFlow를 업데이트
            } catch (e: Exception) {
                Log.e("AnalysisDiet", "네트워크 요청 실패: $e")
                _analysisDiet.emit(null) // 실패 시 null로 업데이트
            }
        }
    }
}
