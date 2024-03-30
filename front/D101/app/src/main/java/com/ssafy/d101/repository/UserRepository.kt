package com.ssafy.d101.repository

import android.util.Log
import com.google.gson.JsonObject
import com.ssafy.d101.api.UserService
import com.ssafy.d101.model.User
import com.ssafy.d101.model.UserInfo
import com.ssafy.d101.model.UserSubInfo
import com.ssafy.d101.utils.TokenManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flow
import retrofit2.Response
import javax.inject.Inject

class UserRepository @Inject constructor(private val userService: UserService, private val tokenManager: TokenManager) {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    suspend fun fetchAndStoreUser(user: User) {
        _user.emit(user)
    }

    fun getUser(): Flow<User?> {
        return user
    }

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

    suspend fun fetchAndStoreUserSubInfo(userSubInfo: UserSubInfo) {
        val currentUser = _user.value
        if (currentUser != null) {
            val updateUser = currentUser.copy(userSubInfo = userSubInfo)
            _user.emit(updateUser)
        }
        else {
            Log.e("User", "User is not available to update UserSubInfo")
        }
    }

    suspend fun getUserSubInfo(): Result<UserSubInfo> {
        return try {
            val response = userService.getUserSubInfo()
            if (response.isSuccessful) {
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