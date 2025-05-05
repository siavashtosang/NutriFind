package com.example.nutrifind.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifind.repository.NutriFindRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: NutriFindRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            repository.fetchInitialUserPreferences()
        }
    }

    val isDarkTheme: StateFlow<Boolean> =
        repository.userPreferencesFlow
            .map { prefs -> prefs.showDarkMode }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.Eagerly,
                initialValue = false
            )

}
