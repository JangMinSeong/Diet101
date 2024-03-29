package com.ssafy.d101.repository

import com.ssafy.d101.model.User
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class UserRepository @Inject constructor() {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    suspend fun fetchAndStoreUser(user: User) {
        _user.emit(user)
    }

    fun getUser(): Flow<User?> {
        return user
    }
}