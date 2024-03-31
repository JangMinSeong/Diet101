package com.ssafy.d101.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.d101.api.FoodSearchService
import com.ssafy.d101.model.FoodInfo
import com.ssafy.d101.model.FoodItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FoodSearchViewModel @Inject constructor(
    private val foodSearchService: FoodSearchService
) : ViewModel() {
    private val _foodItems = MutableStateFlow<List<FoodInfo>>(emptyList())
    val foodItems: StateFlow<List<FoodInfo>> = _foodItems

    // 서버에 해당 파라미터의 음식을 GET 요청하는 함수
    fun fetchFoodItems(foodName: String) {
        Log.d("Foodname", "$foodName")
        viewModelScope.launch {
            try {
                    val response = foodSearchService.fetchFoodItems(foodName)
                    if (response.isSuccessful) {
                        val foodItems = response.body()!!
                        _foodItems.value = foodItems
                    } else {
                        // 에러 응답에 대한 보다 자세한 정보를 로깅
                        Log.e("FoodSearchViewModel", "HTTP Error: Code ${response.code()}, Message: ${response.message()}")
                        response.errorBody()?.let {
                            val errorBody = it.string()
                            Log.e("FoodSearchViewModel", "Error Body: $errorBody")
                        }
                    }
                } catch (e: IOException) {
                Log.e("FoodSearchViewModel", "Network error: ${e.message}", e)
            } catch (e: HttpException) {
                // HttpException을 통해 HTTP 에러 응답 처리
                Log.e("FoodSearchViewModel", "HTTP exception: ${e.message}", e)
            } catch (e: Exception) {
                Log.e("FoodSearchViewModel", "Unknown error: ${e.message}", e)
            }
        }
    }


//    // 음식 항목 추가하는 함수
//    fun addFoodItem(foodItem: FoodItem) {
//        // 현재 리스트에 새로운 항목 추가
//        val updateList = _foodItems.value.toMutableList().apply {
//            add(foodItem)
//        }
//        // mutableStateFlow 업데이트
//        _foodItems.value = updateList
//
//        Log.d("FoodSearchViewModel", "Updated list with ${updateList.size} items.")
//        Log.d("FoodSearchViewModel", "Latest item: ${updateList.last().name}")
//    }


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