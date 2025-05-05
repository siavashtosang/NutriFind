package com.example.nutrifind.repositories

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.nutrifind.data.local.preferences.PreferencesKeys
import com.example.nutrifind.data.local.preferences.UserPreferences
import com.example.nutrifind.data.remote.model.ApiEdamam
import com.example.nutrifind.data.remote.network.DataResponse
import com.example.nutrifind.data.repositories.NutriFindRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map

class FakeNutriFindRepository : NutriFindRepository {

    private val USER_PREFERENCES_NAME = "user_preferences"
    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
        USER_PREFERENCES_NAME
    )

    private lateinit var context: Context

    private val userPreferencesFlow: Flow<UserPreferences> = flowOf(UserPreferences())

    private var shouldReturnNetWorkError = false

    fun setShouldReturnNetWorkError(value: Boolean) {
        shouldReturnNetWorkError = value
    }

    override suspend fun fetchFoodData(
        foodName: String,
        dietFilter: String?,
        dishTypeFilter: String?,
        mealTypeFilter: String?,
        cuisineTypeFilter: String?
    ): DataResponse {
        return if (shouldReturnNetWorkError){
            DataResponse.Error(message = "Network Error")
        }else {
            DataResponse.Success(ApiEdamam())
        }
    }

    override suspend fun fetchInitialUserPreferences(): UserPreferences =
        context.dataStore.data
            .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
            .map { prefs ->
                UserPreferences(prefs[PreferencesKeys.DARK_MODE] ?: false)
            }
            .first()


    override suspend fun updateDarkTheme(darkMode: Boolean) {
        context.dataStore.edit { prefs ->
            prefs[PreferencesKeys.DARK_MODE] = darkMode
        }
    }

    override suspend fun removePreferences(prefsKey: Preferences.Key<PreferencesKeys>) {
        context.dataStore.edit { pref ->
            pref.remove(prefsKey)
        }
    }

    override fun userPreferencesFlow(): Flow<UserPreferences> {
        return userPreferencesFlow
    }
}