package com.ssafy.d101.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.ssafy.d101.api.FoodSearchService
import com.ssafy.d101.model.FoodAddInfo
import com.ssafy.d101.model.FoodInfo
import com.ssafy.d101.model.FoodItem
import com.ssafy.d101.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class FoodSearchViewModel @Inject constructor(
    private val foodSearchService: FoodSearchService,
    private val foodRepository: FoodRepository
) : ViewModel() {
    private val _foodItems = MutableStateFlow<List<FoodInfo>>(emptyList())
    val foodItems: StateFlow<List<FoodInfo>> = _foodItems

    // 사용자가 추가한 음식을 저장할 리스트
    val userAddedFoodItems: StateFlow<List<FoodAddInfo>> = foodRepository.userAddedFoodItems

    // 선택된 음식의 최신 상태를 관리
    private val _selectedFoodItem = MutableStateFlow<FoodAddInfo?>(null)
    val selectedFoodItem: StateFlow<FoodAddInfo?> = _selectedFoodItem

    // 사용자가 선택한 음식을 저장할 리스트
    val uploadedPostItems: StateFlow<List<FoodAddInfo>> = foodRepository.selectedFoodItems

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

    // 음식 아이템 추가 함수
    fun addUserAddedFoodItem(foodAddInfo: FoodAddInfo) {
        viewModelScope.launch {
            foodRepository.addUserAddedFoodItem(foodAddInfo)
        }
        Log.d("FoodSearchViewModel", "Added food item: $foodAddInfo")
    }

    // 음식 아이템
    fun deleteFoodItem(foodAddInfo: FoodAddInfo) {
        viewModelScope.launch {
            foodRepository.deleteFoodItem(foodAddInfo)
        }
    }

    // 선택된 아이템을 설정하는 함수
    fun setSelectedFoodItem(item: FoodAddInfo) {
        _selectedFoodItem.value = item
    }

    // 선택된 음식 아이템들을 레퍼지토리에 저장하는 함수
    fun uploadSelectedItems(selectedItems: List<FoodAddInfo>) {
        viewModelScope.launch {
            foodRepository.uploadSelectedFoodItems(selectedItems)
        }
    }

    // 먹은 양 입력 값에 따라 업데이트하는 함수
    fun updateEatenAmount(updateFoodAddInfo: FoodAddInfo) {
        viewModelScope.launch {
            foodRepository.updateEatenAmount(updateFoodAddInfo)
            Log.d("UpdateEatenAmount", "Updated: $updateFoodAddInfo")
        }
    }
}