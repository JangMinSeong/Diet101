package com.ssafy.d101.di

import com.ssafy.d101.api.UserLoginService
import com.ssafy.d101.utils.AuthAuthenticator
import com.ssafy.d101.utils.AuthInterceptor
import com.ssafy.d101.utils.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Provides
    fun provideAuthInterceptor(tokenManager: TokenManager): AuthInterceptor =
        AuthInterceptor(tokenManager)

    @Provides
    fun provideAuthAuthenticator(tokenManager: TokenManager, @Named("auth") retrofit: Retrofit): AuthAuthenticator =
        AuthAuthenticator(tokenManager, retrofit)

    @Provides
    @Singleton
    fun provideOkHttpClient(authInterceptor: AuthInterceptor, authAuthenticator: AuthAuthenticator): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .authenticator(authAuthenticator)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    @Named("auth")
    fun provideAuthRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideUserService(@Named("auth") retrofit: Retrofit): UserLoginService =
        retrofit.create(UserLoginService::class.java)
}