package com.ssafy.d101.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.d101.api.UserService
import com.ssafy.d101.model.User
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
    fun getUser() = userRepository.getUser()

    suspend fun setUser(user: User) = userRepository.fetchAndStoreUser(user)

    private val _userSubInfo = MutableStateFlow<UserSubInfo?>(null)
    val userSubInfo = _userSubInfo.asStateFlow()

    fun getUserSubInfo() {
        viewModelScope.launch {
            val response: Response<UserSubInfo>
            try {
                response = userService.getUserSubInfo()
                if (response.isSuccessful && response.body() != null) {
                    _userSubInfo.value = response.body()
                    Log.d("UserViewModel", "getUserSubInfo - ${response.body()}")
                } else {
                    Log.e("UserViewModel", "Error getUserSubInfo - ${response.errorBody()}")
                }
            } catch (e: Exception) {
                Log.e("UserViewModel", "Exception fetching userSubInfo")
            }
        }
    }

    fun updateUserInfo(userSubInfo: UserSubInfo) {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                userService.updateUserSubInfo(userSubInfo)
            }
        }
    }
}