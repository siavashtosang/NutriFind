package com.example.nutrifind.data.repositories

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
class DefaultNutriFindRepository @Inject constructor(
    private val apiService: EdamamApi,
    private val prefsDataSource: UserPreferencesDataSource
) : NutriFindRepository {


    override suspend fun fetchFoodData(
        foodName: String,
        dietFilter: String?,
        dishTypeFilter: String?,
        mealTypeFilter: String?,
        cuisineTypeFilter: String?,
    ): DataResponse {
        try {
            val result = apiService.getFoods(
                searchFood = foodName,
                dietFilter = dietFilter,
                dishTypeFilter = dishTypeFilter,
                mealTypeFilter = mealTypeFilter,
                cuisineTypeFilter = cuisineTypeFilter
            )
            return (DataResponse.Success(result))
        } catch (e: IOException) {
            return (DataResponse.Error(message = "Network error"))

        } catch (e: HttpException) {
            return (DataResponse.Error(message = "Server error"))
        } catch (e: Exception) {
            return (DataResponse.Error(message = "Unexpected error"))
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