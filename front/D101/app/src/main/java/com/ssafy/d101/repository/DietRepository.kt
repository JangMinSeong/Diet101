package com.ssafy.d101.repository

import android.util.Log
import com.ssafy.d101.api.DietService
import com.ssafy.d101.model.AnalysisDiet
import com.ssafy.d101.model.DietInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class DietRepository @Inject constructor(private val dietService: DietService) {

    private val _analysisDiet = MutableStateFlow<AnalysisDiet?>(null)
    private val analysisDiet = _analysisDiet.asStateFlow()

    private val _diets = MutableStateFlow<List<DietInfo>?>(null)
    private val diets = _diets.asStateFlow()

    suspend fun getDayDiet(date: String): StateFlow<List<DietInfo>?> {
        val result = getDayDietFromBack(date)
        result.onSuccess { diets ->
            _diets.value = diets
        }.onFailure { exception ->
            Log.e("DietRepository", "Failed to get day diet: ${exception.message}")
        }
        return diets
    }

    private suspend fun getDayDietFromBack(date: String): Result<List<DietInfo>> {
        return try {
            val response = dietService.getDayDiet(date)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get Day Diet"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getAnalysisDiet(date: String, dateFrom : String, dateTo : String): StateFlow<AnalysisDiet?> {
        val result = getAnalysisDietFromBack(date, dateFrom, dateTo)
        result.onSuccess { analysisDiet ->
            _analysisDiet.value = analysisDiet
        }.onFailure { exception ->
            Log.e("DietRepository", "Failed to get analysis diet: ${exception.message}")
        }
        return analysisDiet
    }
    private suspend fun getAnalysisDietFromBack(date: String, dateFrom : String, dateTo : String) : Result<AnalysisDiet> {
        return try {
            val response = dietService.getAnalysisDiet(date, dateFrom, dateTo)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get AnalysisDiet"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}