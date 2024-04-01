package com.ssafy.d101.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.d101.model.FoodInfo
import com.ssafy.d101.repository.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodViewModel @Inject constructor(
    private val foodRepository: FoodRepository
) : ViewModel() {

    private val _recommendFood = MutableStateFlow<List<FoodInfo>?>(null)
    val recommendFood: StateFlow<List<FoodInfo>?> = _recommendFood.asStateFlow()
    fun loadRecommendFoods(kcal: String) {
        viewModelScope.launch {
            val result = foodRepository.getRecommendFoods(kcal).first()
            _recommendFood.value = result
        }
    }
}