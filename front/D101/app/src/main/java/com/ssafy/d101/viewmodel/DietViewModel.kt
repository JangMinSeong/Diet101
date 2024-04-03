package com.ssafy.d101.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ssafy.d101.model.AnalysisDiet
import com.ssafy.d101.model.CreateMealReq
import com.ssafy.d101.model.DietInfo
import com.ssafy.d101.model.Dunchfast
import com.ssafy.d101.model.IntakeReq
import com.ssafy.d101.repository.DietRepository
import com.ssafy.d101.repository.ModelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
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
        val file = modelRepository.prepareImageForUpload(modelRepository.context.value!!).getOrThrow()

        val createMealReq = CreateMealReq(dietRepository.dietType.value!!, getCurrentDate(), dietRepository.takeReqList.value!!)
        val gson = Gson()
        val createMealReqJson = gson.toJson(createMealReq)
        val createMealReqBody = createMealReqJson.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        viewModelScope.launch {
            dietRepository.saveMeal(file, createMealReqBody)
        }
    }

    fun setDietType(type : Dunchfast) {
        viewModelScope.launch{
            dietRepository.setDietType(type)
        }
    }

    fun setTakeReqList(takeReqList : List<IntakeReq>) {
        viewModelScope.launch{
            dietRepository.setTakeReqList(takeReqList)
        }
    }

    fun setDietDate(dietDate: LocalDate) {
        viewModelScope.launch {
            dietRepository.setDietDate(dietDate)
        }
    }

    fun getTakeReqs() = dietRepository.takeReqList
    fun getType() = dietRepository.dietType

    fun getDietDate() = dietRepository.dietDate

    fun getTakeReqList(): List<IntakeReq> {
        return dietRepository.takeReqList.value!!
    }
    fun getDietType(): Dunchfast {
        return dietRepository.dietType.value!!
    }
}
