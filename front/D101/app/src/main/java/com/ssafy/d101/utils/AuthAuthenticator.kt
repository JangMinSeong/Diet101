package com.ssafy.d101.utils

import com.ssafy.d101.api.UserLoginService
import com.ssafy.d101.model.RegisterResponse
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Named

class AuthAuthenticator @Inject constructor(
    private val tokenManager: TokenManager,
    @Named("auth") private val retrofit: Retrofit
) : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request? {
        val accessToken = runBlocking {
            tokenManager.getAccessToken().first()
        } ?: return null
        val refreshToken = runBlocking {
            tokenManager.getRefreshToken().first()
        } ?: return null

        val userLoginService = retrofit.create(UserLoginService::class.java)
        val tokenResponse = runBlocking {
            userLoginService.reissueToken(RegisterResponse(accessToken = accessToken, refreshToken = refreshToken, email = "")).execute()
        }

        if (tokenResponse.isSuccessful) {
            val newToken = tokenResponse.body()
            runBlocking {
                newToken?.accessToken?.let {tokenManager.saveAccessToken(it) }
                newToken?.refreshToken?.let { tokenManager.saveRefreshToken(it) }
            }

            return response.request.newBuilder()
                .header("Authorization", "Bearer ${newToken?.accessToken}")
                .build()
        }

        return null
    }

}