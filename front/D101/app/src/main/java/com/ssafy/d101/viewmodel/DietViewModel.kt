package com.ssafy.d101.viewmodel;

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.d101.api.DietService
import com.ssafy.d101.api.RetrofitBuilder.dietService
import com.ssafy.d101.model.AnalysisDiet
import com.ssafy.d101.model.DietInfo
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

class DietViewModel() : ViewModel() {
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


    private val _analysisDiet = MutableLiveData<AnalysisDiet>()
    val resultDiet: LiveData<AnalysisDiet> = _analysisDiet

    fun analysisDiet() {
        val date = getCurrentDate()
        val dateFrom = getStartOfWeek()
        val dateTo = getEndOfWeek()

        dietService.getWeekDiet(date, dateFrom, dateTo).enqueue(object : Callback<AnalysisDiet> {
            override fun onResponse(call: Call<AnalysisDiet>, response: Response<AnalysisDiet>) {
                if (response.isSuccessful) {
                    _analysisDiet.postValue(response.body())
                } else {
                    Log.i("AnalysisDiet", "정보 가져 오기 실패" + response.errorBody().toString())
                }
            }

            override fun onFailure(call: Call<AnalysisDiet>, t: Throwable) {
                Log.i("AnalysisDiet", "에러 발생  $t")
            }
        })
    }
}
