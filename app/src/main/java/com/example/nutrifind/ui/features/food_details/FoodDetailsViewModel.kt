package com.example.nutrifind.ui.features.food_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifind.data.network.DataResponse
import com.example.nutrifind.repository.NutriFindRepository
import com.example.nutrifind.utils.convertToFoodClass
import dagger.hilt.android.lifecycle.HiltViewModel
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
                findSelectedFood(selectedFoodName = it)
            }
        }
    }

    private suspend fun searchFood(foodName: String) {
        _uiState.update { it.copy(results = DataResponse.Loading) }

        repository.fetchFoodData(foodName) { result ->
            _uiState.update { currentState ->
                currentState.copy(results = result)
            }
        }
    }

    fun onRetry() {
        viewModelScope.launch {
            selectedFoodNameFlow.collectLatest {
                searchFood(foodName = it)
                findSelectedFood(selectedFoodName = it)
            }
        }
    }

    private fun findSelectedFood(selectedFoodName: String) {

        val currentResult = _uiState.value.results

        if (currentResult is DataResponse.Success) {
            val foods = currentResult.apiEdamam?.convertToFoodClass()
            val findFood = foods?.find { food -> food.name == selectedFoodName }

            if (findFood != null) {
                _uiState.update { currentState ->
                    currentState.copy(food = findFood)
                }

            } else {
                _uiState.update { it.copy(results = DataResponse.Error(message = "Food not found")) }
            }
        } else {
            _uiState.update { it.copy(results = DataResponse.Error(message = "No data available")) }
        }
    }
}