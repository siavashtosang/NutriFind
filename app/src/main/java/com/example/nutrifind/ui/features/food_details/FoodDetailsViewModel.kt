package com.example.nutrifind.ui.features.food_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifind.data.remote.network.DataResponse
import com.example.nutrifind.data.repositories.NutriFindRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FoodDetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: NutriFindRepository,
) : ViewModel() {

    private val _uiState: MutableStateFlow<FoodDetailsUiState> =
        MutableStateFlow(FoodDetailsUiState())
    val uiState: StateFlow<FoodDetailsUiState> = _uiState.asStateFlow()

    private val selectedFoodNameFlow: StateFlow<String> =
        savedStateHandle.getStateFlow("selectedFood", "")

    init {
        viewModelScope.launch {
            selectedFoodNameFlow.collectLatest {
                searchFood(foodName = it)
                _uiState.update { currentState ->
                    currentState.copy(selectedFood = it)
                }
            }
        }
    }

    private fun searchFood(foodName: String) {
        viewModelScope.launch {

            _uiState.update { it.copy(foodResults = DataResponse.Loading) }

            val results = async { repository.fetchFoodData(foodName = foodName) }

            _uiState.update { currentState ->
                currentState.copy(foodResults = results.await())
            }
        }
    }

    fun onRetry() {
        viewModelScope.launch {
            selectedFoodNameFlow.collectLatest {
                searchFood(foodName = it)
            }
        }
    }
}