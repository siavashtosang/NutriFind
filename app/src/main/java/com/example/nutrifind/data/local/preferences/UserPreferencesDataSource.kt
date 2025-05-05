package com.example.nutrifind.data.local.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserPreferencesDataSource(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun getPreferences(): UserPreferences =
        dataStore.data
            .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
            .map { prefs ->
                UserPreferences(prefs[PreferencesKeys.DARK_MODE] ?: false)
            }
            .first()

    fun preferencesFlow(): Flow<UserPreferences> =
        dataStore.data
            .catch { if (it is IOException) emit(emptyPreferences()) else throw it }
            .map { prefs -> UserPreferences(prefs[PreferencesKeys.DARK_MODE] ?: false) }

    suspend fun setDarkMode(enabled: Boolean) {
        dataStore.edit { prefs ->
            prefs[PreferencesKeys.DARK_MODE] = enabled
        }
    }

    suspend fun removePreferences(prefsKey: Preferences.Key<PreferencesKeys>) {
        dataStore.edit { pref ->
            pref.remove(prefsKey)
        }
    }
}

data class UserPreferences(
    val showDarkMode: Boolean = false
)

