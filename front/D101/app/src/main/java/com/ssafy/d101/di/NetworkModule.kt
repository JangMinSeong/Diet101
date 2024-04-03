package com.ssafy.d101.di


import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ssafy.d101.api.AllergyService
import com.ssafy.d101.api.DietService
import com.ssafy.d101.api.FoodSearchService
import com.ssafy.d101.api.FoodService
import com.ssafy.d101.api.ModelService
import com.ssafy.d101.api.OCRService
import com.ssafy.d101.api.UserLoginService
import com.ssafy.d101.api.UserService
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
import java.util.concurrent.TimeUnit
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
            .readTimeout(30, TimeUnit.SECONDS) // 읽기 타임아웃
            .connectTimeout(30, TimeUnit.SECONDS) // 연결 타임아웃
            .writeTimeout(30, TimeUnit.SECONDS) // 쓰기 타임아웃
            .build()

    private val gson : Gson = GsonBuilder().setLenient().create()

    @Provides
    @Singleton
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://j10d101.p.ssafy.io:8000/api/")
 //           .baseUrl("http://10.0.2.2:8080/api/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    @Named("auth")
    fun provideAuthRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("http://j10d101.p.ssafy.io:8000/api/")
 //           .baseUrl("http://10.0.2.2:8080/api/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

    @Provides
    @Singleton
    fun provideUserLoginService(@Named("auth") retrofit: Retrofit): UserLoginService =
        retrofit.create(UserLoginService::class.java)

    @Provides
    @Singleton
    fun provideUserService(retrofit: Retrofit): UserService =
        retrofit.create(UserService::class.java)

    @Provides
    @Singleton
    fun provideDietService(retrofit: Retrofit): DietService =
        retrofit.create(DietService::class.java)

    @Provides
    @Singleton
    fun provideFoodSearchService(retrofit: Retrofit): FoodSearchService =
        retrofit.create(FoodSearchService::class.java)

    @Provides
    @Singleton
    fun provideFoodService(retrofit: Retrofit): FoodService =
        retrofit.create(FoodService::class.java)

    @Provides
    @Singleton
    fun provideModelService(retrofit: Retrofit): ModelService =
        retrofit.create(ModelService::class.java)

    @Provides
    @Singleton
    fun provideOCRlService(retrofit: Retrofit): OCRService =
        retrofit.create(OCRService::class.java)

    @Provides
    @Singleton
    fun provideAllergyService(retrofit: Retrofit): AllergyService =
        retrofit.create(AllergyService::class.java)
}