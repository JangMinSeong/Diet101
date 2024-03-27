package com.ssafy.d101.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssafy.d101.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class UserViewModel : ViewModel() {
    private val _user = MutableStateFlow<User?>(null)
    val user = _user.asStateFlow()

    fun updateUser(user: User) {
        viewModelScope.launch {
            _user.emit(user)
        }
    }

    fun clearUser() {
        viewModelScope.launch {
            _user.emit(null)
        }
    }
}