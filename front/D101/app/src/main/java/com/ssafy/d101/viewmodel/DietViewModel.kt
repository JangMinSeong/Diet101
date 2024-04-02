package com.ssafy.d101.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.d101.api.DietService
import com.ssafy.d101.api.UserService
import com.ssafy.d101.model.AnalysisDiet
import com.ssafy.d101.model.DietInfo
import com.ssafy.d101.repository.DietRepository
import com.ssafy.d101.repository.ModelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.TemporalAdjusters
import javax.inject.Inject

@HiltViewModel
class DietViewModel @Inject constructor(
    private val dietRepository: DietRepository,
    private val modelRepository: ModelRepository
) : ViewModel() {

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
                val analysisDiet = dietRepository.getAnalysisDiet(date, dateFrom, dateTo).first()
                _analysisDiet.value = analysisDiet
            } catch (e: Exception) {
                Log.e("AnalysisDiet", "네트워크 요청 실패: $e")
                _analysisDiet.emit(null) // 실패 시 null로 업데이트
            }
        }
    }
//    suspend fun analysisDiet() {
//        val date = getCurrentDate()
//        val dateFrom = getStartOfWeek()
//        val dateTo = getEndOfWeek()
//
//        dietService.getWeekDiet(date, dateFrom, dateTo).enqueue(object : Callback<AnalysisDiet> {
//            override fun onResponse(call: Call<AnalysisDiet>, response: Response<AnalysisDiet>) {
//                if (response.isSuccessful) {
//                    _analysisDiet.postValue(response.body())
//                } else {
//                    // Handle the error
//                }
//            }
//
//            override fun onFailure(call: Call<AnalysisDiet>, t: Throwable) {
//            }
//        })
//    }


    private val _dayDiet = MutableStateFlow<List<DietInfo>?>(null)
    val dayDiet: StateFlow<List<DietInfo>?> = _dayDiet.asStateFlow()
    fun loadDayDiet(date: String) {
        viewModelScope.launch {
            val result = dietRepository.getDayDiet(date).first()
            _dayDiet.value = result
        }
    }

    suspend fun saveMeal() {
        val file = modelRepository.prepareImageForUpload(modelRepository.context.value!!)
        viewModelScope.launch {
            //        dietRepository.saveMeal(file, )
        }
    }

}
