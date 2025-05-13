package com.example.nutrifind.repositories

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.test.core.app.ApplicationProvider
import com.example.nutrifind.data.local.preferences.PreferencesKeys
import com.example.nutrifind.data.local.preferences.UserPreferences
import com.example.nutrifind.data.local.preferences.UserPreferencesDataSource
import com.example.nutrifind.data.remote.model.ApiEdamam
import com.example.nutrifind.data.remote.network.DataResponse
import com.example.nutrifind.data.repositories.NutriFindRepository
import com.example.nutrifind.di.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeNutriFindRepository : NutriFindRepository {


    private val userPreferencesFlow: Flow<UserPreferences> = flowOf(UserPreferences())

    private var shouldReturnNetWorkError = false

    val context = ApplicationProvider.getApplicationContext<Context>()

    private val prefsDataSource = UserPreferencesDataSource(dataStore = context.dataStore)

    fun setShouldReturnNetWorkError(networkReturn: Boolean) {
        shouldReturnNetWorkError = networkReturn
    }

    override suspend fun fetchFoodData(
        foodName: String,
        dietFilter: String?,
        dishTypeFilter: String?,
        mealTypeFilter: String?,
        cuisineTypeFilter: String?
    ): DataResponse {
        return if (shouldReturnNetWorkError) {
            DataResponse.Error(message = "Network Error")
        } else {
            DataResponse.Success(ApiEdamam())
        }
    }

    override suspend fun fetchInitialUserPreferences(): UserPreferences =
        prefsDataSource.getPreferences()

    override suspend fun updateDarkTheme(darkMode: Boolean) =
        prefsDataSource.setDarkMode(darkMode)

    override fun userPreferencesFlow(): Flow<UserPreferences> =
        prefsDataSource.preferencesFlow()

    override suspend fun removePreferences(prefsKey: Preferences.Key<PreferencesKeys>) =
        prefsDataSource.removePreferences(prefsKey)
}