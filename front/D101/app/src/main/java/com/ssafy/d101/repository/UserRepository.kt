package com.ssafy.d101.repository

import android.util.Log
import androidx.compose.runtime.mutableIntStateOf
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

    private val height = mutableIntStateOf(0)
    private val weight = mutableIntStateOf(0)
    private val activityLevel = mutableIntStateOf(0)
    private val calorie = mutableIntStateOf(0)

    fun setHeight(height: Int) {
        val currentInfo = _userSubInfo.value ?: UserSubInfo()
        _userSubInfo.value = currentInfo.copy(height = height)
//        this.height.intValue = height
    }

    fun setWeight(weight: Int) {
        val currentInfo = _userSubInfo.value ?: UserSubInfo()
        _userSubInfo.value = currentInfo.copy(weight = weight)
//        this.weight.intValue = weight
    }

    fun setActivityLevel(activityLevel: Int) {
        val currentInfo = _userSubInfo.value ?: UserSubInfo()
        _userSubInfo.value = currentInfo.copy(activity = activityLevel)
//        this.activityLevel.intValue = activityLevel
    }

    fun setCalorie(calorie: Int) {
        val currentInfo = _userSubInfo.value ?: UserSubInfo()
        _userSubInfo.value = currentInfo.copy(calorie = calorie)
//        this.calorie.intValue = calorie
    }

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

    suspend fun storeUserSubInfo(userSubInfo: UserSubInfo): Result<Boolean> {
        return try {
            val response = userService.updateUserSubInfo(userSubInfo)
            if (response.isSuccessful) {
                setUserSubInfo(userSubInfo)
                Result.success(true)
            } else {
                Log.e("User", "Failed to store user sub info")
                Result.success(false)
            }
        } catch (e: Exception) {
            Log.e("User", "Exception during user sub info storing", e)
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