package com.ssafy.d101.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
<<<<<<< HEAD
import com.ssafy.d101.api.DietService
import com.ssafy.d101.api.UserService
import com.ssafy.d101.repository.DietRepository
import com.ssafy.d101.repository.UserRepository
=======
import com.ssafy.d101.api.FoodService
import com.ssafy.d101.repository.FoodRepository
>>>>>>> b511d7dbf3504a7be94d9a42da0646d7b85b4186
import com.ssafy.d101.utils.TokenManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore<Preferences> =
        PreferenceDataStoreFactory.create(produceFile = { context.preferencesDataStoreFile("user_prefs") })

    @Provides
    @Singleton
    fun provideTokenManager(dataStore: DataStore<Preferences>): TokenManager =
        TokenManager(dataStore)

    @Provides
    @Singleton
<<<<<<< HEAD
    fun provideUserRepository(userService: UserService, tokenManager: TokenManager): UserRepository =
        UserRepository(userService, tokenManager)

    @Provides
    @Singleton
    fun provideDietRepository(dietService: DietService): DietRepository =
        DietRepository(dietService)
=======
    fun provideFoodRepository(foodService: FoodService): FoodRepository =
        FoodRepository(foodService)
>>>>>>> b511d7dbf3504a7be94d9a42da0646d7b85b4186
}