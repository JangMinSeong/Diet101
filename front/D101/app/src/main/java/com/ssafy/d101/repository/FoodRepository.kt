package com.ssafy.d101.repository

import android.util.Log
import com.ssafy.d101.api.FoodService
import com.ssafy.d101.model.FoodInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class FoodRepository @Inject constructor(private val foodService: FoodService){

    private val _foods = MutableStateFlow<List<FoodInfo>?>(null)
    private val foods = _foods.asStateFlow()

    suspend fun getRecommendFoods(kcal: String): StateFlow<List<FoodInfo>?> {
        val result = getRecommendFoodFromBack(kcal)
        result.onSuccess { foods ->
            _foods.value = foods
        }.onFailure { exception ->
            Log.e("FoodSearchRepository", "Failed to get Recommend Foods: ${exception.message}")
        }
        return foods
    }

    private suspend fun getRecommendFoodFromBack(kcal: String): Result<List<FoodInfo>> {
        return try {
            val response = foodService.getRecommendFood(kcal)
            if (response.isSuccessful) {
                Result.success(response.body()!!)
            } else {
                Result.failure(Exception("Failed to get Recommend Foods"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}