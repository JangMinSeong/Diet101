package com.ssafy.d101.viewmodel

import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.d101.api.UserService
import com.ssafy.d101.model.User
import com.ssafy.d101.model.UserInfo
import com.ssafy.d101.model.UserSubInfo
import com.ssafy.d101.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    private val userService: UserService,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _hasUserSubInfo = MutableStateFlow<Boolean>(false)
    val hasUserSubInfo = _hasUserSubInfo.asStateFlow()
    fun getUser() = userRepository.getUser()

    suspend fun setUser(user: User) = userRepository.fetchAndStoreUser(user)

    suspend fun setUserSubInfo(userSubInfo: UserSubInfo) = userRepository.fetchAndStoreUserSubInfo(userSubInfo)

    suspend fun getUserSubInfo(): Result<UserSubInfo> {
        val result = userRepository.getUserSubInfo()
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

    private val _userSubInfo = MutableStateFlow<UserSubInfo?>(null)
    val userSubInfo = _userSubInfo.asStateFlow()

//    fun getUserSubInfo() {
//        viewModelScope.launch {
//            val response: Response<UserSubInfo>
//            try {
//                response = userService.getUserSubInfo()
//                if (response.isSuccessful && response.body() != null) {
//                    _userSubInfo.value = response.body()
//                    Log.d("UserViewModel", "getUserSubInfo - ${response.body()}")
//                } else {
//                    Log.e("UserViewModel", "Error getUserSubInfo - ${response.errorBody()}")
//                }
//            } catch (e: Exception) {
//                Log.e("UserViewModel", "Exception fetching userSubInfo")
//            }
//        }
//    }
//    fun updateUserInfo(userSubInfo: UserSubInfo) {
//        viewModelScope.launch {
//            withContext(Dispatchers.IO) {
//                userService.updateUserSubInfo(userSubInfo)
//            }
//        }
//    }
}