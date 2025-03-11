package com.example.nutrifind.ui.features.home

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onSearchTextChange(searchingText: String) {
        _uiState.update { it.copy(searchText = searchingText) }
    }

    fun onToggleSearch(searchedText: SearchedHistory) {
        _uiState.update { currentState ->
            currentState.copy(isSearching = !currentState.isSearching)
        }
        if (!_uiState.value.isSearching) {
            onSearchTextChange("")
        }

        if (_uiState.value.searchHistory.find { it == searchedText } == null) {
            _uiState.update { currentState ->
                currentState.copy(searchHistory = _uiState.value.searchHistory.plus(searchedText))
            }
        }
    }
}