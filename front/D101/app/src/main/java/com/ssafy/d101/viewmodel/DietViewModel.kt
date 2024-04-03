package com.ssafy.d101.viewmodel

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.ssafy.d101.R
import com.ssafy.d101.model.AnalysisDiet
import com.ssafy.d101.model.CreateMealReq
import com.ssafy.d101.model.DailyNutrient
import com.ssafy.d101.model.DietInfo
import com.ssafy.d101.model.Dunchfast
import com.ssafy.d101.model.IntakeReq
import com.ssafy.d101.repository.DietRepository
import com.ssafy.d101.repository.ModelRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
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

    private fun getTotalDayCalories(): Int {
        return _dayDiet.value?.sumOf { it.kcal } ?: 0
    }

    private fun getTotalDayCarbohydrate(): Double {
        return _dayDiet.value?.sumOf { diet -> diet.intake.sumOf { foodInfo -> foodInfo.food.carbohydrate} } ?: 0.0
    }

    private fun getTotalDayProtein(): Double {
        return _dayDiet.value?.sumOf { diet -> diet.intake.sumOf { foodInfo -> foodInfo.food.protein} } ?: 0.0
    }

    private fun getTotalDayFat(): Double {
        return _dayDiet.value?.sumOf { diet -> diet.intake.sumOf { foodInfo -> foodInfo.food.fat } } ?: 0.0
    }

    private val _dailyNutrient = MutableStateFlow<DailyNutrient?>(null)
    val dailyNutrient: StateFlow<DailyNutrient?> = _dailyNutrient.asStateFlow()

    fun refreshDailyNutrient() {
        _dailyNutrient.value = DailyNutrient(
            totalCalorie = getTotalDayCalories(),
            totalCarbohydrate = getTotalDayCarbohydrate(),
            totalProtein = getTotalDayProtein(),
            totalFat = getTotalDayFat()
        )
    }

    fun loadDayDiet(date: String) {
        viewModelScope.launch {
            val result = dietRepository.getDayDiet(date).first()
            _dayDiet.value = result
        }
    }

    private fun getDunchfastToString(dunchfast: Dunchfast): String {
        return when(dunchfast) {
            Dunchfast.BREAKFAST -> "BREAKFAST"
            Dunchfast.BRUNCH -> "BRUNCH"
            Dunchfast.LUNCH -> "LUNCH"
            Dunchfast.LINNER -> "LINNER"
            Dunchfast.DINNER -> "DINNER"
            Dunchfast.NIGHT -> "NIGHT"
            Dunchfast.SNACK -> "SNACK"
            Dunchfast.BEVERAGE -> "BEVERAGE"
            Dunchfast.ALCOHOL -> "ALCOHOL"
            else -> "BREAKFAST" // 선택한 항목이 매핑되지 않는 경우 아침으로 고정
        }
    }

    suspend fun saveMeal() {
//        val file = modelRepository.prepareImageForUpload(modelRepository.context.value!!).getOrThrow()
        var MultipartBodyFile = modelRepository.temp.value
        if (MultipartBodyFile == null) {
            val context = modelRepository.context.value!!
            val drawableId = R.drawable.gallery
            val drawable = ContextCompat.getDrawable(context, drawableId) as? BitmapDrawable
            val bitmap = drawable?.bitmap
            val file = File(context.cacheDir, "gallery")
            FileOutputStream(file).use { out ->
                bitmap?.compress(Bitmap.CompressFormat.PNG, 100, out)
            }
            val requestFile = file.asRequestBody("image/png".toMediaTypeOrNull())
            val multipartFile = MultipartBody.Part.createFormData("default image", file.name, requestFile)
            MultipartBodyFile = modelRepository.convertDrawableToFile()
        }
        val createMealReq = CreateMealReq(getDunchfastToString(dietRepository.dietType.value!!), dietRepository.dietDate.value!!, dietRepository.takeReqList.value!!)
        val gson = Gson()
        val createMealReqJson = gson.toJson(createMealReq)
        val createMealReqBody = createMealReqJson.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())
        viewModelScope.launch(Dispatchers.IO) {
            dietRepository.saveMeal(MultipartBodyFile, createMealReqBody)
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
