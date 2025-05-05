package com.example.nutrifind.ui.features.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifind.data.remote.network.DataResponse
import com.example.nutrifind.data.repository.NutriFindRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val repository: NutriFindRepository,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    private val _uiState: MutableStateFlow<CategoryUiState> = MutableStateFlow(CategoryUiState())
    val uiState: StateFlow<CategoryUiState> = _uiState.asStateFlow()

    private val selectedCategoryNameFlow: StateFlow<String> =
        savedStateHandle.getStateFlow("categoryTitle", initialValue = "")

    init {
        viewModelScope.launch {
            selectedCategoryNameFlow.collectLatest {
                _uiState.update { currentState -> currentState.copy(categoryResultsScreenTitle = it) }
                searchFood(foodName = it)
            }
        }
    }

    private suspend fun searchFood(foodName: String) {
        _uiState.update { it.copy(results = DataResponse.Loading) }

        repository.fetchFoodData(foodName) { results ->
            _uiState.update { currentState ->
                currentState.copy(results = results)
            }
        }
    }

    fun onRetry() {
        viewModelScope.launch {
            searchFood(foodName = selectedCategoryNameFlow.first())
        }
    }
}