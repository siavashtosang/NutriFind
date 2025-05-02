package com.example.nutrifind.ui.features.food_details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifind.data.model.Hits
import com.example.nutrifind.data.network.DataResponse
import com.example.nutrifind.data.nutri_find_repository.repository.NutriFindRepository
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
                findSelectedFood(foodName = it)
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

    fun onRetry(){
        viewModelScope.launch {
            selectedFoodNameFlow.collectLatest {
                searchFood(foodName = it)
                findSelectedFood(foodName = it)
            }
        }
    }

    private fun findSelectedFood(foodName: String) {

        val currentResult = _uiState.value.results

        if (currentResult is DataResponse.Success) {
            val findFood = currentResult.apiEdamam?.hits?.find { it.recipe?.label == foodName }

            if (findFood != null) {
                val nutritionList = createNutritionList(findFood)

                _uiState.update { currentState ->
                    currentState.copy(
                        recipeUri = findFood.recipe?.url ?: "",
                        foodName = findFood.recipe?.label ?: "",
                        foodImage = findFood.recipe?.images?.regular?.url ?: "",
                        calories = findFood.recipe?.calories?.toInt().toString(),
                        dailyValue = findFood.recipe?.yield?.toInt().toString(),
                        ingredients = findFood.recipe?.ingredients ?: emptyList(),
                        nutritionList = nutritionList

                    )
                }
            } else {
                _uiState.update { it.copy(results = DataResponse.Error(message = "Food not found")) }
            }
        } else {
            _uiState.update { it.copy(results = DataResponse.Error(message = "No data available")) }
        }
    }

    private fun createNutritionList(findFood: Hits?): List<Nutrition> {
        val totalNutrients = findFood?.recipe?.totalNutrients ?: return emptyList()

        return listOfNotNull(
            totalNutrients.procnt?.let {
                Nutrition(
                    name = it.label.toString(),
                    value = it.quantity?.toInt().toString(),
                    unit = it.unit.toString()
                )
            },

            totalNutrients.chocdf?.let {
                Nutrition(
                    name = it.label.toString(),
                    value = it.quantity?.toInt().toString(),
                    unit = it.unit.toString()
                )
            },

            totalNutrients.fat?.let {
                Nutrition(
                    name = it.label.toString(),
                    value = it.quantity?.toInt().toString(),
                    unit = it.unit.toString()
                )
            },

            totalNutrients.chole?.let {
                Nutrition(
                    name = it.label.toString(),
                    value = it.quantity?.toInt().toString(),
                    unit = it.unit.toString()
                )
            },

            totalNutrients.sugar?.let {
                Nutrition(
                    name = it.label.toString(),
                    value = it.quantity?.toInt().toString(),
                    unit = it.unit.toString()
                )
            },

            totalNutrients.fibtg?.let {
                Nutrition(
                    name = it.label.toString(),
                    value = it.quantity?.toInt().toString(),
                    unit = it.unit.toString()
                )
            },

            totalNutrients.ca?.let {
                Nutrition(
                    name = it.label.toString(),
                    value = it.quantity?.toInt().toString(),
                    unit = it.unit.toString()
                )
            },

            totalNutrients.mg?.let {
                Nutrition(
                    name = it.label.toString(),
                    value = it.quantity?.toInt().toString(),
                    unit = it.unit.toString()
                )
            }
        )
    }
}