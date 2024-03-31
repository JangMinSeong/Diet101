package com.ssafy.d101.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import com.ssafy.d101.model.UserSubInfo
import com.ssafy.d101.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _hasUserSubInfo = MutableStateFlow<Boolean>(false)
    val hasUserSubInfo = _hasUserSubInfo.asStateFlow()
    fun getUserInfo() = userRepository.userInfo
    fun getUserSubInfo() = userRepository.userSubInfo

    suspend fun setUserSubInfo(userSubInfo: UserSubInfo) = userRepository.setUserSubInfo(userSubInfo)

    suspend fun fetchUserSubInfo(): Result<UserSubInfo> {
        val result = userRepository.fetchUserSubInfo()
        if (result.isSuccess) {
            _hasUserSubInfo.value = true
        }
        return result
    }

    private val height = mutableIntStateOf(0)
    private val weight = mutableIntStateOf(0)
    private val activityLevel = mutableIntStateOf(0)

    fun setHeight(height: Int) {
        this.height.intValue = height
    }

    fun setWeight(weight: Int) {
        this.weight.intValue = weight
    }

    fun setActivityLevel(activityLevel: Int) {
        this.activityLevel.intValue = activityLevel
    }

//    fun registerUser(userInfo: UserInfo) {
//        viewModelScope.launch {
//            val result = userRepository.registerUser(userInfo)
//            if (result.isSuccess && result.getOrDefault(false)) {
//
//            }
//        }
//    }

//    fun getUserSubInfo() {
//        viewModelScope.launch {
//            val response = withContext(Dispatchers.IO) {
//                userService.getUserSubInfo()
//            }
//            if (response.isSuccessful) {
//                _userSubInfo.value = response.body()
//            }
//        }
//    }
//
//    fun updateUserInfo(userSubInfo: UserSubInfo) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                userService.updateUserSubInfo(userSubInfo)
//            }
//        }
//    }
}