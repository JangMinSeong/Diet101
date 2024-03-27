package com.ssafy.d101.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.kakao.sdk.auth.AuthApiClient
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.common.model.ClientError
import com.kakao.sdk.common.model.ClientErrorCause
import com.kakao.sdk.common.model.KakaoSdkError
import com.kakao.sdk.user.UserApiClient
import com.ssafy.d101.api.RetrofitBuilder
import com.ssafy.d101.model.RegisterResponse
import com.ssafy.d101.model.UserInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Response
import java.time.LocalDate
import java.time.Period
import java.time.format.DateTimeFormatter
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class KakaoAuthViewModel(application: Application) : AndroidViewModel(application) {

    companion object {
        private const val TAG = "KakaoAuthViewModel"
    }

    private val context = application.applicationContext

    val isLoggedIn = MutableStateFlow<Boolean>(false)

    fun kakaoLogin() {
        viewModelScope.launch {
            isLoggedIn.emit(handleKakaoLogin())
        }
    }

    fun checkLogin() {
        viewModelScope.launch {
            isLoggedIn.emit(hasToken())
        }
    }

    fun kakaoLogout() {
        viewModelScope.launch {
            isLoggedIn.emit(handleKakaoLogout())
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

                            RetrofitBuilder.userService.registerUser(userInfo).enqueue(object: retrofit2.Callback<RegisterResponse> {
                                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                                    if (response.isSuccessful) {
                                        // 회원가입 성공 처리
                                        Log.d("Register", "회원가입 성공")
                                    } else {
                                        // 에러 처리
                                        Log.e("Register", "회원가입 실패: ${response.errorBody()?.string()}")
                                    }
                                }

                                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                                    // 네트워크 에러 등의 실패 처리
                                    Log.e("Register", "회원가입 에러: $t")
                                }
                            })

                            continuation.resume(true)
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