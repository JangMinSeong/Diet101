package com.ssafy.d101.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.ssafy.d101.model.UserInfo
import com.ssafy.d101.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import javax.inject.Inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

@HiltViewModel
class KakaoAuthViewModel @Inject constructor(
    private val userRepository: UserRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    companion object {
        private const val TAG = "KakaoAuthViewModel"
    }

    private val _isLoggedIn = MutableStateFlow<Boolean>(false)
    val isLoggedIn = _isLoggedIn.asStateFlow()

    private val _loginChecked = MutableStateFlow<Boolean>(false)
    val loginChecked = _loginChecked.asStateFlow()

    fun kakaoLogin() {
        viewModelScope.launch {
            _isLoggedIn.emit(handleKakaoLogin())
        }
    }

    fun checkLogin() {
        viewModelScope.launch {
            _isLoggedIn.emit(hasToken())
            _loginChecked.emit(true)
        }
    }

    fun kakaoLogout() {
        viewModelScope.launch {
            _isLoggedIn.emit(handleKakaoLogout())
        }
    }

    private fun calculateAge(birthdateStr: String): Int {
        val formatter = DateTimeFormatter.ofPattern("yyyyMMdd")
        val birthdate = LocalDate.parse(birthdateStr, formatter)

        val currentDate = LocalDate.now()

        return Period.between(birthdate, currentDate).years
    }

    private suspend fun hasToken() : Boolean =
        suspendCoroutine<Boolean> { continuation ->
            if (AuthApiClient.instance.hasToken()) {
                UserApiClient.instance.accessTokenInfo {_, error ->
                    if (error != null) {
                        if (error is KakaoSdkError && error.isInvalidTokenError()) {
                            continuation.resume(false)
                        }
                        else {
                            continuation.resume(false)
                        }
                    }
                    else {
                        continuation.resume(true)
                    }
                }
            }
            else {
                continuation.resume(false)
            }
        }

    private suspend fun handleKakaoLogout() : Boolean =
        suspendCoroutine<Boolean> { continuation ->
            UserApiClient.instance.logout { error ->
                if (error != null) {
                    Log.e(TAG, "로그아웃 실패", error)
                    continuation.resume(false)
                } else {
                    Log.i(TAG, "로그아웃 성공")
                    continuation.resume(false)
                }
            }
        }

    private suspend fun handleKakaoLogin() : Boolean =
        suspendCoroutine<Boolean> { continuation ->
            // 로그인 조합 예제

            // 카카오계정으로 로그인 공통 callback 구성
            // 카카오톡으로 로그인 할 수 없어 카카오계정으로 로그인할 경우 사용됨
            val callback: (OAuthToken?, Throwable?) -> Unit = { token, error ->
                if (error != null) {
                    Log.e(TAG, "카카오계정으로 로그인 실패", error)
                    continuation.resume(false)
                } else if (token != null) {
                    Log.i(TAG, "카카오계정으로 로그인 성공 ${token.accessToken}")
                    UserApiClient.instance.me {user, error ->
                        if (error != null) {
                            Log.e(TAG, "사용자 정보 요청 실패", error)
                            continuation.resume(false)
                        } else if (user != null) {
                            val userInfo = UserInfo(
                                oauthId = user.id.toString(),
                                email = user.kakaoAccount?.email?: "",
                                username = user.kakaoAccount?.profile?.nickname?: "",
                                age = calculateAge(user.kakaoAccount?.birthyear.toString() + user.kakaoAccount?.birthday.toString()),
                                gender = user.kakaoAccount?.gender?.name?: "",
                                image = user.kakaoAccount?.profile?.profileImageUrl?: ""
                            )
                            Log.i(TAG, "사용자 정보 요청 성공" +
                                    "\n${userInfo.toString()}"
                            )

                            viewModelScope.launch {
                                val result = userRepository.registerUser(userInfo)
                                if (result.isSuccess && result.getOrDefault(false)) {
                                    continuation.resume(true)
                                }
                                else {
                                    continuation.resume(false)
                                }
                            }
                        }
                    }
                }
            }

            // 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
            if (UserApiClient.instance.isKakaoTalkLoginAvailable(context)) {
                UserApiClient.instance.loginWithKakaoTalk(context) { token, error ->
                    if (error != null) {
                        Log.e(TAG, "카카오톡으로 로그인 실패", error)

                        // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                        // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                        if (error is ClientError && error.reason == ClientErrorCause.Cancelled) {
                            return@loginWithKakaoTalk
                        }

                        // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                        UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
                    } else if (token != null) {
                        Log.i(TAG, "카카오톡으로 로그인 성공 ${token.accessToken}")
                    }
                }
            } else {
                UserApiClient.instance.loginWithKakaoAccount(context, callback = callback)
            }
    }
}