package com.ssafy.d101.repository

import com.ssafy.d101.model.FoodAddInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class FoodRepository @Inject constructor(

){
    private val _userAddedFoodItems = MutableStateFlow<List<FoodAddInfo>>(emptyList())
    val userAddedFoodItems: StateFlow<List<FoodAddInfo>> = _userAddedFoodItems

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