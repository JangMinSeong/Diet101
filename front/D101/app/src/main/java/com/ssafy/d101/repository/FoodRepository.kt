package com.ssafy.d101.repository

import android.util.Log
import com.ssafy.d101.api.FoodService
import com.ssafy.d101.model.FoodInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import com.ssafy.d101.model.FoodAddInfo

class FoodRepository @Inject constructor(private val foodService: FoodService){

    private val _foods = MutableStateFlow<List<FoodInfo>?>(null)
    private val foods = _foods.asStateFlow()

    private val _userAddedFoodItems = MutableStateFlow<List<FoodAddInfo>>(emptyList())
    val userAddedFoodItems: StateFlow<List<FoodAddInfo>> = _userAddedFoodItems

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


        // 음식 아이템 추가
    suspend fun addUserAddedFoodItem(foodAddInfo: FoodAddInfo) {
        if (!_userAddedFoodItems.value.any { it.id == foodAddInfo.id }) {
            val updatedList = _userAddedFoodItems.value.toMutableList()
            updatedList.add(foodAddInfo)
            _userAddedFoodItems.value = updatedList
        } else {
            println("이미 추가된 상품입니다.")
        }
    }

    // 음식 아이템 삭제
    suspend fun deleteFoodItem(foodAddInfo: FoodAddInfo) {
        val updatedList = _userAddedFoodItems.value.filterNot { it == foodAddInfo }
        _userAddedFoodItems.value = updatedList
    }

}