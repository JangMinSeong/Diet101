package com.ssafy.d101.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import com.ssafy.d101.api.AllergyService
import com.ssafy.d101.api.DietService
import com.ssafy.d101.api.UserService
import com.ssafy.d101.repository.DietRepository
import com.ssafy.d101.repository.UserRepository
import com.ssafy.d101.api.FoodService
import com.ssafy.d101.api.ModelService
import com.ssafy.d101.api.OCRService
import com.ssafy.d101.repository.AllergyRepository
import com.ssafy.d101.repository.FoodRepository
import com.ssafy.d101.repository.ModelRepository
import com.ssafy.d101.repository.OCRRepository
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
    fun provideUserRepository(userService: UserService, tokenManager: TokenManager): UserRepository =
        UserRepository(userService, tokenManager)

    @Provides
    @Singleton
    fun provideDietRepository(dietService: DietService): DietRepository =
        DietRepository(dietService)

    @Provides
    @Singleton
    fun provideFoodRepository(foodService: FoodService): FoodRepository =
        FoodRepository(foodService)

    @Provides
    @Singleton
    fun provideModelRepository(modelService: ModelService): ModelRepository =
        ModelRepository(modelService)

    @Provides
    @Singleton
    fun provideOCRRepository(ocrService: OCRService): OCRRepository =
        OCRRepository(ocrService)

    @Provides
    @Singleton
    fun provideAllergyRepository(allergyService: AllergyService): AllergyRepository =
        AllergyRepository(allergyService)
}