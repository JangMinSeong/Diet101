package com.ssafy.d101.repository

import com.ssafy.d101.api.AllergyService
import com.ssafy.d101.model.AllergyInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class AllergyRepository @Inject constructor(
    private val allergyService: AllergyService
) {
    // 사용자 알러지 정보 담는 곳
    private val _userAllergies = MutableStateFlow<AllergyInfo?>(null)
    val userAllergies: StateFlow<AllergyInfo?> = _userAllergies.asStateFlow()

    // 서버에 사용자 알레르기 정보 추가
    suspend fun addUserAllergy(allergies: List<String>) {
        try {
            val response = allergyService.postAllergy(AllergyInfo(allergies))
            if (response.isSuccessful && response.body() != null) {
                // 성공 시 업데이트
                _userAllergies.value = response.body()
            } else {

            }
        } catch (e: Exception) {

        }
    }
}
