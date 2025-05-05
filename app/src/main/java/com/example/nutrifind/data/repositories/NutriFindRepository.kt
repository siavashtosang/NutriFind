package com.example.nutrifind.data.repositories

import androidx.datastore.preferences.core.Preferences
import com.example.nutrifind.data.local.preferences.PreferencesKeys
import com.example.nutrifind.data.local.preferences.UserPreferences
import com.example.nutrifind.data.remote.network.DataResponse
import kotlinx.coroutines.flow.Flow

interface NutriFindRepository {

    suspend fun fetchFoodData(
        foodName: String,
        dietFilter: String? = null,
        dishTypeFilter: String? = null,
        mealTypeFilter: String? = null,
        cuisineTypeFilter: String? = null,
    ): DataResponse

    suspend fun fetchInitialUserPreferences(): UserPreferences

    suspend fun updateDarkTheme(darkMode: Boolean)

    suspend fun removePreferences(prefsKey: Preferences.Key<PreferencesKeys>)

    fun userPreferencesFlow(): Flow<UserPreferences>
}