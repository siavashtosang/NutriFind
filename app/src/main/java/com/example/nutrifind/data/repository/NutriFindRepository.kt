package com.example.nutrifind.data.repository

import androidx.datastore.preferences.core.Preferences
import com.example.nutrifind.data.local.preferences.PreferencesKeys
import com.example.nutrifind.data.local.preferences.UserPreferences
import com.example.nutrifind.data.local.preferences.UserPreferencesDataSource
import com.example.nutrifind.data.remote.network.DataResponse
import com.example.nutrifind.data.remote.network.EdamamApi
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class NutriFindRepository @Inject constructor(
    private val apiService: EdamamApi,
    private val prefsDataSource: UserPreferencesDataSource
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

    val userPreferencesFlow: Flow<UserPreferences> =
        prefsDataSource.preferencesFlow()

    suspend fun fetchInitialUserPreferences(): UserPreferences =
        prefsDataSource.getPreferences()

    suspend fun updateDarkTheme(darkMode: Boolean) =
        prefsDataSource.setDarkMode(darkMode)

    suspend fun removePreferences(prefsKey: Preferences.Key<PreferencesKeys>) =
        prefsDataSource.removePreferences(prefsKey)

}