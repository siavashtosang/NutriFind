package com.example.nutrifind.ui

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifind.data.network.ApiService
import com.example.nutrifind.data.network.DataResponse
import com.example.nutrifind.data.offline.foodNames
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class NutriFindViewModel @Inject constructor(
    private val apiService: ApiService
) : ViewModel() {

    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()


    init {
        Log.d(TAG, "Initializing HomeViewModel")
        initializeFoodData()
    }

    private fun initializeFoodData() {
        viewModelScope.launch {
            launch { initializeFoodSuggestion() }
            launch { initializeTopFoods() }
        }
    }

    private suspend fun initializeFoodSuggestion() {
        val foodSuggestionTitle = foodNames.random()
        _uiState.update { currentState ->
            currentState.copy(foodSuggestionTitle = foodSuggestionTitle)
        }
        getFoodSuggestion(foodSuggestionTitle)
    }

    private suspend fun initializeTopFoods() {
        // Use a list to define top food names
        val topFoodNames = listOf("salad", "pizza", "chinese")

        // Fetch top foods concurrently and update the state
        topFoodNames.forEach { foodName ->
            getTopFood(foodName)
        }
    }

    private suspend fun getFoodSuggestion(foodName: String) {
        /*
                fetchFoodData(foodName) { result ->
                    _uiState.update { it.copy(foodsSuggestion = result) }
                }
        */
    }

    private suspend fun getTopFood(foodName: String) {
        /*
                fetchFoodData(foodName) { result ->
                    when (foodName) {
                        "salad" -> _uiState.update { it.copy(salads = result) }
                        "pizza" -> _uiState.update { it.copy(pizzas = result) }
                        "chinese" -> _uiState.update { it.copy(chinese = result) }
                    }
                }
        */
    }

    private suspend fun fetchFoodData(
        foodName: String,
        updateState: (DataResponse) -> Unit
    ) {
        _uiState.update { currentState ->
            when (foodName) {
                "salad" -> currentState.copy(salads = DataResponse.Loading)
                "pizza" -> currentState.copy(pizzas = DataResponse.Loading)
                "chinese" -> currentState.copy(chinese = DataResponse.Loading)
                else -> currentState.copy(foodsSuggestion = DataResponse.Loading)
            }
        }
        try {
            Log.d(TAG, "Fetching food data for: $foodName")
            val result = apiService.getFoods(
                type = "public",
                searchFood = foodName,
                app_id = "aeff32ad",
                app_key = "b5a9932ceec96638ae9e30ea128926ba"
            )
            updateState(DataResponse.Success(result))
        } catch (e: IOException) {
            Log.e(TAG, "IOException while fetching food data for $foodName: ${e.message}", e)
            updateState(DataResponse.Error)
        } catch (e: HttpException) {
            Log.e(TAG, "HttpException while fetching food data for $foodName: ${e.message}", e)
            updateState(DataResponse.Error)
        }
    }

    fun onCategorySearched(categoryTitle: String) {
        searchFood(categoryTitle)
        _uiState.update { it.copy(categoryResultsScreenTitle = categoryTitle) }
    }

    fun onSearchTextChange(searchingText: String) {
        _uiState.update { it.copy(searchText = searchingText) }
    }

    fun onSearchTriggered() {
            searchFood(_uiState.value.searchText)
            updateSearchHistory(_uiState.value.searchText)
    }

    fun onActiveSearchBarChange(isSearching: Boolean) {
        _uiState.update { currentState -> currentState.copy(isActiveSearchBar = isSearching) }

        if (!isSearching){
            onSearchTextChange("")
        }
    }

    private fun searchFood(foodName: String) {
        viewModelScope.launch {
            _uiState.update { it.copy(searchedResults = DataResponse.Loading) }
            try {
                Log.d(TAG, "Searching for food: $foodName")

                val result = apiService.getFoods(
                    type = "public",
                    searchFood = foodName,
                    app_id = "aeff32ad",
                    app_key = "b5a9932ceec96638ae9e30ea128926ba"
                )

                _uiState.update { it.copy(searchedResults = DataResponse.Success(result)) }


            } catch (e: IOException) {
                Log.e(TAG, "IOException during search: ${e.message}", e)
                _uiState.update { it.copy(searchedResults = DataResponse.Error) }

            } catch (e: HttpException) {
                Log.e(TAG, "HttpException during search: ${e.message}", e)
                _uiState.update { it.copy(searchedResults = DataResponse.Error) }
            }
        }
    }

    private fun updateSearchHistory(searchedText: String) {
        if (_uiState.value.searchHistory.none { it.searchedText == searchedText }) {
            _uiState.update { currentState ->
                currentState.copy(
                    searchHistory = currentState.searchHistory + SearchedHistory(
                        searchedText = searchedText,
                        searchTime = "" // Consider using a timestamp here
                    )
                )
            }
        }
    }

    fun onReUseSearchedHistory(searchedHistoryText: String) {
        searchFood(searchedHistoryText)
    }

    fun onClearSearchedHistoriesClick() {
        _uiState.update { currentState ->
            currentState.copy(searchHistory = emptyList())
        }
    }

    companion object {
        private const val TAG = "SiaTag"
    }


}