package com.ssafy.d101.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.d101.repository.AllergyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllergyViewModel @Inject constructor(
    private val allergyRepository: AllergyRepository
) : ViewModel() {

    // 사용자 알레르기 정보 업데이트
    fun updateUserAllergy(allergies: List<String>) {
        viewModelScope.launch {
            allergyRepository.addUserAllergy(allergies)
        }
    }
}