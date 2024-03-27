package com.ssafy.d101.utils

import android.util.Log
import com.kakao.sdk.common.Constants.AUTHORIZATION
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.net.HttpURLConnection.HTTP_OK
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val tokenManager: TokenManager) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalRequest = chain.request()
        if (originalRequest.url.encodedPath.contains("user/login")) {
            return chain.proceed(originalRequest)
        }

        val token: String = runBlocking {
            tokenManager.getAccessToken().first()
        } ?: return errorResponse(chain.request())

        val request = chain.request().newBuilder().header(AUTHORIZATION, "Bearer $token").build()

        val response = chain.proceed(request)
        if (response.code == HTTP_OK) {
            val newAccessToken: String = response.header(AUTHORIZATION, null) ?: return response
            Log.d("AUTH", "new Access Token = $newAccessToken")

            CoroutineScope(Dispatchers.IO).launch {
                val existedAccessToken = tokenManager.getAccessToken().first()
                if (existedAccessToken != newAccessToken) {
                    tokenManager.saveAccessToken(newAccessToken)
                    Log.d("AUTH", "newAccessToken = ${newAccessToken}\nExistedAccessToken = ${existedAccessToken}")
                }
            }
        } else {
            Log.e("AUTH","${response.code} : ${response.request} \n ${response.message}")
        }

        return response
    }

    private fun errorResponse(request: Request): Response = Response.Builder()
        .request(request)
        .protocol(Protocol.HTTP_2)
        .code(100)
        .message("")
        .body(ResponseBody.create(null, ""))
        .build()
}