package com.ssafy.d101.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.ssafy.d101.model.UserSubInfo
import com.ssafy.d101.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _hasUserSubInfo = MutableStateFlow<Boolean>(false)
    private val _height = MutableStateFlow<Int?>(null)
    private val _weight = MutableStateFlow<Int?>(null)
    private val _activityLevel = MutableStateFlow<Int?>(null)
    private val _calorie = MutableStateFlow<Int?>(null)
    val username = userRepository.userInfo.map { it?.username.orEmpty() }.asLiveData()

    init {
        initializeUserSubInfo()
    }

    private fun initializeUserSubInfo() {
        viewModelScope.launch {
            userRepository.userSubInfo.collect { userSubInfo ->
                userSubInfo?.let {
                    _height.value = it.height
                    _weight.value = it.weight
                    _activityLevel.value = it.activity
                    _calorie.value = it.calorie
                    // 이곳에서 필요한 경우 _hasUserSubInfo 업데이트
                    _hasUserSubInfo.value = true
                } ?: run {
                    // userSubInfo가 null일 경우의 처리
                    _hasUserSubInfo.value = false
                }
            }
        }
    }

    val height = _height.asStateFlow()
    val weight = _weight.asStateFlow()
    val activityLevel = _activityLevel.asStateFlow()
    val calorie = _calorie.asStateFlow()
    val hasUserSubInfo = _hasUserSubInfo.asStateFlow()

    private val _saveSuccess = MutableStateFlow<Boolean>(false)
    val saveSuccess = _saveSuccess.asStateFlow()

    fun getUserInfo() = userRepository.userInfo
    fun getUserSubInfo() = userRepository.userSubInfo

    suspend fun setUserSubInfo() {
        Log.i("UserViewModel", "height: ${_height.value}, weight: ${_weight.value}, activity: ${_activityLevel.value}")
        val userSubInfo = UserSubInfo(
            height = _height.value ?: 0,
            weight = _weight.value ?: 0,
            activity = _activityLevel.value ?: -1,
            calorie = calculateCalories()
        )
        viewModelScope.launch {
            val result = userRepository.storeUserSubInfo(userSubInfo)
            _saveSuccess.value = result.isSuccess
        }
    }

    private fun caculateBMR(): Double {
        Log.i("UserViewModel", "weight: ${_weight.value}, height: ${_height.value}, age: ${userRepository.userInfo.value?.age}")
        return if (userRepository.userInfo.value?.gender == "MALE") {
            66.47 + (13.75 * _weight.value!!) + (5.003 * _height.value!!) - (6.755 * userRepository.userInfo.value?.age!!)
        } else {
            655.1 + (9.563 * _weight.value!!) + (1.85 * _height.value!!) - (4.676 * userRepository.userInfo.value?.age!!)
        }
    }

    private fun calculateCalories(): Int {
        val bmr = caculateBMR()
        val activityLevel = userRepository.userSubInfo.value?.activity!!
        val factor = when (activityLevel) {
            0 -> 1.2
            1 -> 1.375
            2 -> 1.55
            3 -> 1.725
            else -> 1.9
        }
        return (bmr * factor).toInt()
    }

    suspend fun fetchUserSubInfo(): Result<UserSubInfo> {
        val result = userRepository.fetchUserSubInfo()
        if (result.isSuccess) {
            _hasUserSubInfo.value = true
        }
        return result
    }



    fun setHeight(height: Int) {
        userRepository.setHeight(height)
    }

    fun setWeight(weight: Int) {
        userRepository.setWeight(weight)
    }

    fun setActivityLevel(activityLevel: Int) {
        userRepository.setActivityLevel(activityLevel)
    }

    fun setCalorie() {
        userRepository.setCalorie(calculateCalories())
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