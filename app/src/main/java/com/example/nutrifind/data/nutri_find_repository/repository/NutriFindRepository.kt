package com.example.nutrifind.data.nutri_find_repository.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import com.example.nutrifind.data.network.ApiService
import com.example.nutrifind.data.network.DataResponse
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


private const val USER_PREFERENCES_NAME = "user_preferences"


val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = USER_PREFERENCES_NAME)

data class UserPreferences(
    val showDarkMode: Boolean = false
)

object PreferencesKeys {
    val DARK_MODE = booleanPreferencesKey(name = "dark_mode")
}


@Singleton
class NutriFindRepository @Inject constructor(
    private val apiService: ApiService,
   @ApplicationContext private val context: Context,
) {

    suspend fun fetchFoodData(
        foodName: String,
        dietFilter: String? = null,
        dishTypeFilter: String? = null,
        mealTypeFilter: String? = null,
        cuisineTypeFilter: String? = null,
        updateState: (DataResponse) -> Unit
    ) {
        try {
            val result = apiService.getFoods(
                searchFood = foodName,
                dietFilter = dietFilter,
                dishTypeFilter = dishTypeFilter,
                mealTypeFilter = mealTypeFilter,
                cuisineTypeFilter = cuisineTypeFilter
            )
            updateState(DataResponse.Success(result))
        } catch (e: IOException) {
            updateState(DataResponse.Error(message = "Network error"))

        } catch (e: HttpException) {
            updateState(DataResponse.Error(message = "Server error"))
        } catch (e: Exception) {
            updateState(DataResponse.Error(message = "Unexpected error"))
        }
    }

    val userPreferencesFlow: Flow<UserPreferences> = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }.map { preferences ->
            mapUserPreferences(preferences)
        }

    suspend fun updateDarkTheme(darkTheme: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.DARK_MODE] = darkTheme
        }
    }

    suspend fun fetchInitialUserPreferences() {
        mapUserPreferences(context.dataStore.data.first().toPreferences())
    }

    private fun mapUserPreferences(preferences: Preferences): UserPreferences {
        val isDarkTheme = preferences[PreferencesKeys.DARK_MODE] ?: false
        return UserPreferences(isDarkTheme)
    }

}