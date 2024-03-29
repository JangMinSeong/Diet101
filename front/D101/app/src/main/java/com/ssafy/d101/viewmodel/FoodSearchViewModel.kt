package com.ssafy.d101.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.d101.api.FoodSearchService
import com.ssafy.d101.api.userAdditionFoodService
import com.ssafy.d101.model.FoodInfo
import com.ssafy.d101.model.FoodItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class FoodSearchViewModel(private val foodSearchService: FoodSearchService) : ViewModel() {
    private val _foodItems = MutableStateFlow<List<FoodItem>>(emptyList())
    val foodItems: StateFlow<List<FoodItem>> = _foodItems

    // 서버에 해당 파라미터의 음식을 GET 요청하는 함수
    fun fetchFoodItems(foodName: String) {
        viewModelScope.launch {
            try {
                val response = foodSearchService.fetchFoodItems(foodName)
                if (response.isSuccessful && response.body() != null) {
                    _foodItems.value = response.body()!!
                    Log.d("FoodSearchViewModel", "Fetched ${response.body()?.size} food items")
                } else {
                    Log.e("FoodSearchViewModel", "Error fetching food items: ${response.errorBody()?.string()}")
                }
            } catch (e: Exception) {
                Log.e("FoodSearchViewModel", "Exception fetching food items")
            }
        }
    }


    // 음식 항목 추가하는 함수
    fun addFoodItem(foodItem: FoodItem) {
        // 현재 리스트에 새로운 항목 추가
        val updateList = _foodItems.value.toMutableList().apply {
            add(foodItem)
        }
        // mutableStateFlow 업데이트
        _foodItems.value = updateList

        Log.d("FoodSearchViewModel", "Updated list with ${updateList.size} items.")
        Log.d("FoodSearchViewModel", "Latest item: ${updateList.last().name}")
    }


    // 서버에 음식 정보를 POST 요청하는 함수
//    fun postFoodToServer(foodInfo: FoodInfo) {
//        viewModelScope.launch {
//            try {
//                val response = userAdditionFoodService.postUserAdditionFood(foodInfo)
//                if (response.isSuccessful) {
//                    // 요청 성공
//                    Log.d("PostFood", "Success: ${response.body()}")
//                } else {
//                    Log.e("PostFood", "Failed: ${response.errorBody()?.string()}")
//                }
//            } catch (e: Exception) {
//                Log.e("PostFood", "Exception", e)
//            }
//        }
//    }
}