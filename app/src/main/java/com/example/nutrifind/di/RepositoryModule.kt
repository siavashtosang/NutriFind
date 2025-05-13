package com.example.nutrifind.di

import com.example.nutrifind.data.local.preferences.UserPreferencesDataSource
import com.example.nutrifind.data.remote.network.EdamamApi
import com.example.nutrifind.data.repositories.DefaultNutriFindRepository
import com.example.nutrifind.data.repositories.NutriFindRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideDefaultNutriFindRepository(
        apiService: EdamamApi,
        prefsDataSource: UserPreferencesDataSource
    ) = DefaultNutriFindRepository(apiService, prefsDataSource) as NutriFindRepository
}