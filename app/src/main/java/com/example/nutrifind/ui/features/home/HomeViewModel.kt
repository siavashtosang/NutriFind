package com.example.nutrifind.ui.features.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifind.data.local.offline.TagFilterItem
import com.example.nutrifind.data.local.offline.foodNames
import com.example.nutrifind.data.remote.model.ApiEdamam
import com.example.nutrifind.data.remote.network.DataResponse
import com.example.nutrifind.data.repository.NutriFindRepository
import com.example.nutrifind.utils.convertToFoodClass
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

        loadHome()

        viewModelScope.launch {
            repository.fetchInitialUserPreferences()
            _uiState.update { it.copy(darkMode = userPreferences.first().showDarkMode) }
        }
    }

    private fun loadHome() {
        viewModelScope.launch {
            launch { initializeFoodData() }
        }
    }

    private suspend fun initializeFoodData() {

        val foodSuggestionTitle = foodNames.random()

        _uiState.update { currentState ->
            currentState.copy(foodSuggestionTitle = foodSuggestionTitle)
        }

        getFoodSuggestion(foodSuggestionTitle)
        initializeTopFoods()
        _uiState.update { it.copy(results = DataResponse.Success(apiEdamam = ApiEdamam())) }
    }

    private suspend fun initializeTopFoods() {

        val topFoodNames = listOf("salad", "pizza", "chinese")

        topFoodNames.forEach { foodName ->
            getTopFood(foodName)
        }
    }

    private suspend fun getFoodSuggestion(foodName: String) {

        repository.fetchFoodData(foodName = foodName) { result ->

            if (result is DataResponse.Success) {
                _uiState.update { currentState ->
                    currentState.copy(
                        foodsSuggestion = result.apiEdamam?.convertToFoodClass() ?: emptyList(),
                        results = result
                    )
                }
            } else {
                _uiState.update { currentState ->
                    currentState.copy(
                        results = result
                    )
                }
            }
        }
    }

    private suspend fun getTopFood(foodName: String) {

        repository.fetchFoodData(foodName) { result ->

            when (foodName) {

                "salad" -> {
                    if (result is DataResponse.Success) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                salads = result.apiEdamam?.convertToFoodClass() ?: emptyList(),
                                results = result
                            )
                        }
                    }
                }

                "pizza" -> {

                    if (result is DataResponse.Success) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                pizzas = result.apiEdamam?.convertToFoodClass() ?: emptyList(),
                                results = result
                            )
                        }
                    }
                }

                "chinese" -> {
                    if (result is DataResponse.Success) {
                        _uiState.update { currentState ->
                            currentState.copy(
                                chinese = result.apiEdamam?.convertToFoodClass() ?: emptyList(),
                                results = result
                            )
                        }
                    }
                }
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
            currentSate.copy(searchedResultsState = DataResponse.Loading)
        }

        viewModelScope.launch {
            repository.fetchFoodData(foodName = searchText) { result ->
                if (result is DataResponse.Success) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            searchedResults = result.apiEdamam?.convertToFoodClass() ?: emptyList(),
                            searchedResultsState = result
                        )
                    }
                } else {
                    _uiState.update { currentState ->
                        currentState.copy(searchedResultsState = result)
                    }

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
            currentSate.copy(searchedResultsState = DataResponse.Loading)
        }

        viewModelScope.launch {
            repository.fetchFoodData(foodName = searchedHistoryText) { result ->
                if (result is DataResponse.Success) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            searchedResults = result.apiEdamam?.convertToFoodClass() ?: emptyList(),
                            searchedResultsState = result
                        )
                    }
                } else {
                    _uiState.update { currentState ->
                        currentState.copy(searchedResultsState = result)
                    }
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
                searchedResultsState = DataResponse.Loading
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

                if (result is DataResponse.Success) {
                    _uiState.update { currentState ->
                        currentState.copy(
                            searchedResults = result.apiEdamam?.convertToFoodClass() ?: emptyList(),
                            searchedResultsState = result
                        )
                    }
                } else {
                    _uiState.update { currentState ->
                        currentState.copy(searchedResultsState = result)
                    }
                }
            }
        }
    }

    fun onDarkThemeChange(isDarkMode: Boolean) {
        viewModelScope.launch {

            repository.updateDarkTheme(darkMode = isDarkMode)

            _uiState.update { currentState ->
                currentState.copy(darkMode = userPreferences.first().showDarkMode)
            }
        }

    }

    fun onRetryHome() {
        loadHome()
    }

    private fun List<TagFilterItem>.toggleExactly(one: TagFilterItem) =
        map { it.copy(selected = it.id == one.id && !one.selected) }
}