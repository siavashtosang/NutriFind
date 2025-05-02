package com.example.nutrifind.ui.features.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifind.data.network.DataResponse
import com.example.nutrifind.data.nutri_find_repository.repository.NutriFindRepository
import com.example.nutrifind.data.offline.TagFilterItem
import com.example.nutrifind.data.offline.foodNames
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NutriFindRepository
) : ViewModel() {


    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val userPreferences = repository.userPreferencesFlow

    init {
        initializeFoodData()

        viewModelScope.launch {
            repository.fetchInitialUserPreferences()
            _uiState.update { it.copy(isDarkTheme = userPreferences.first().showDarkMode) }
        }
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

        val topFoodNames = listOf("salad", "pizza", "chinese")

        topFoodNames.forEach { foodName ->
            getTopFood(foodName)
        }
    }

    private suspend fun getFoodSuggestion(foodName: String) {

        _uiState.update { currentState ->
            currentState.copy(foodsSuggestion = DataResponse.Loading)
        }

        repository.fetchFoodData(foodName = foodName) { result ->

            _uiState.update { it.copy(foodsSuggestion = result) }
        }
    }

    private suspend fun getTopFood(foodName: String) {

        when (foodName) {
            "salad" -> _uiState.update { it.copy(salads = DataResponse.Loading) }
            "pizza" -> _uiState.update { it.copy(pizzas = DataResponse.Loading) }
            "chinese" -> _uiState.update { it.copy(chinese = DataResponse.Loading) }
        }

        repository.fetchFoodData(foodName) { result ->

            when (foodName) {
                "salad" -> _uiState.update { it.copy(salads = result) }
                "pizza" -> _uiState.update { it.copy(pizzas = result) }
                "chinese" -> _uiState.update { it.copy(chinese = result) }
            }
        }
    }


    fun onSearchTextChange(searchingText: String) {
        _uiState.update { it.copy(searchText = searchingText) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSearchTriggered() {

        val searchText = _uiState.value.searchText

        _uiState.update { currentSate ->
            currentSate.copy(searchedResults = DataResponse.Loading)
        }

        viewModelScope.launch {
            repository.fetchFoodData(foodName = searchText) { result ->
                _uiState.update { currentState ->
                    currentState.copy(searchedResults = result)
                }
            }
        }

        updateSearchHistory(searchText)
    }

    fun onActiveSearchBarChange(isSearching: Boolean) {
        _uiState.update { currentState -> currentState.copy(isActiveSearchBar = isSearching) }

        if (!isSearching) {
            onSearchTextChange("")
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateSearchHistory(searchedText: String) {

        val now = Instant.now().atZone(ZoneId.systemDefault())
            .format(DateTimeFormatter.ofPattern("HH:mm:ss, MMM d"))

        _uiState.update { currentState ->
            currentState.copy(
                searchHistory = currentState.searchHistory + SearchedHistory(
                    searchedText = searchedText,
                    searchTime = now
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onReUseSearchedHistory(searchedHistoryText: String) {

        _uiState.update { currentSate ->
            currentSate.copy(searchedResults = DataResponse.Loading)
        }

        viewModelScope.launch {
            repository.fetchFoodData(foodName = searchedHistoryText) { result ->
                _uiState.update { currentState ->
                    currentState.copy(searchedResults = result)
                }
            }
        }

        updateSearchHistory(searchedHistoryText)

    }

    fun onClearSearchedHistoriesClick() {
        _uiState.update { currentState ->
            currentState.copy(searchHistory = emptyList())
        }
    }

    fun onDietFilterClick(selectedFilter: TagFilterItem) {

        _uiState.update { currentState ->
            currentState.copy(dietsFilters = currentState.dietsFilters.toggleExactly(selectedFilter))
        }
    }


    fun onDishFilterClick(selectedFilter: TagFilterItem) {

        _uiState.update { currentState ->
            currentState.copy(
                dishTypesFilters = currentState.dishTypesFilters.toggleExactly(
                    selectedFilter
                )
            )
        }
    }

    fun onMealFilterCLick(selectedFilter: TagFilterItem) {

        _uiState.update { currentState ->
            currentState.copy(
                mealTypesFilters = currentState.mealTypesFilters.toggleExactly(
                    selectedFilter
                )
            )
        }
    }

    fun onCuisineTypeFilterClick(selectedFilter: TagFilterItem) {

        _uiState.update { currentState ->
            currentState.copy(
                cuisineTypeFilters = currentState.cuisineTypeFilters.toggleExactly(
                    selectedFilter
                )
            )
        }
    }

    fun onClearAllFilterClick() {

        _uiState.update { currentState ->

            currentState.copy(
                dietsFilters = currentState.dietsFilters.map { it.copy(selected = false) },
                dishTypesFilters = currentState.dishTypesFilters.map { it.copy(selected = false) },
                mealTypesFilters = currentState.mealTypesFilters.map { it.copy(selected = false) },
                cuisineTypeFilters = currentState.cuisineTypeFilters.map { it.copy(selected = false) },
                filterItems = null
            )
        }
    }


    fun onApplyFilterClick() {

        val selectedFilterList = listOf(
            _uiState.value.dietsFilters,
            _uiState.value.dishTypesFilters,
            _uiState.value.mealTypesFilters,
            _uiState.value.cuisineTypeFilters,
        )

        val selectedFilterCount = selectedFilterList.sumOf { filterList ->
            filterList.count { it.selected }
        }

        _uiState.update { currentState ->
            currentState.copy(
                filterItems = selectedFilterCount,
                searchedResults = DataResponse.Loading
            )
        }

        viewModelScope.launch {

            repository.fetchFoodData(
                foodName = _uiState.value.searchText,
                dietFilter = _uiState.value.dietsFilters.find { it.selected }?.title,
                dishTypeFilter = _uiState.value.dishTypesFilters.find { it.selected }?.title,
                mealTypeFilter = _uiState.value.mealTypesFilters.find { it.selected }?.title,
                cuisineTypeFilter = _uiState.value.cuisineTypeFilters.find { it.selected }?.title
            ) { result ->

                _uiState.update { currentState ->
                    currentState.copy(searchedResults = result)
                }
            }
        }
    }

    fun onDarkThemeChange(isDarkTheme: Boolean) {
        viewModelScope.launch {

            repository.updateDarkTheme(darkTheme = isDarkTheme)

            _uiState.update { currentState ->
                currentState.copy(isDarkTheme = userPreferences.first().showDarkMode)
            }
        }

    }

    fun onRetryHome(){
        initializeFoodData()
    }

    private fun List<TagFilterItem>.toggleExactly(one: TagFilterItem) =
        map { it.copy(selected = it.id == one.id && !one.selected) }
}