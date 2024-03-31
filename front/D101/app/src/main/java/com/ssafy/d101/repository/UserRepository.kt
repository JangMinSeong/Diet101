package com.ssafy.d101.repository

import android.util.Log
import com.ssafy.d101.api.UserService
import com.ssafy.d101.model.UserInfo
import com.ssafy.d101.model.UserSubInfo
import com.ssafy.d101.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class UserRepository @Inject constructor(private val userService: UserService, private val tokenManager: TokenManager) {

    private val _userInfo = MutableStateFlow<UserInfo?>(null)
    val userInfo = _userInfo.asStateFlow()

    private val _userSubInfo = MutableStateFlow<UserSubInfo?>(null)
    val userSubInfo = _userSubInfo.asStateFlow()

    suspend fun setUserInfo(userInfo: UserInfo) {
        _userInfo.emit(userInfo)
    }

    // UserInfo를 받아서 백에 로그인 요청
    suspend fun registerUser(userInfo: UserInfo): Result<Boolean> {
        return try {
            val response = userService.registerUser(userInfo)
            if (response.isSuccessful) {
                response.body()?.accessToken?.let { tokenManager.saveAccessToken(it) }
                response.body()?.refreshToken?.let { tokenManager.saveRefreshToken(it) }
                Result.success(true)
            } else {
                Log.e("User", "Failed to register user")
                Result.success(false)
            }
        } catch (e: Exception) {
            Log.e("User", "Exception during user registration", e)
            Result.failure(e)
        }
    }

    // UserSubInfo를 Repository에 저장
    suspend fun setUserSubInfo(userSubInfo: UserSubInfo) {
        _userSubInfo.emit(userSubInfo)
    }

    // 백에서 UserSubInfo 가져오기
    suspend fun fetchUserSubInfo(): Result<UserSubInfo> {
        return try {
            val response = userService.getUserSubInfo()
            if (response.isSuccessful) {
                Log.i("UserRepository", "UserSubInfo: ${response.body()}")
                _userSubInfo.emit(response.body())
                Result.success(response.body()!!)
            } else {
                Log.e("User", "Failed to get user sub info")
                Result.failure(Exception("Failed to get user sub info"))
            }
        } catch (e: Exception) {
            Log.e("User", "Exception during user sub info fetching", e)
            Result.failure(e)
        }

    }
}