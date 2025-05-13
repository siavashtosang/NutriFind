package com.example.nutrifind.ui.features.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nutrifind.data.local.offline.TagFilterItem
import com.example.nutrifind.data.local.offline.foodNames
import com.example.nutrifind.data.remote.network.DataResponse
import com.example.nutrifind.data.repositories.NutriFindRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: NutriFindRepository,
) : ViewModel() {


    private val _uiState: MutableStateFlow<HomeUiState> = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    private val userPreferences = repository.userPreferencesFlow()

    init {
        viewModelScope.launch {
            repository.fetchInitialUserPreferences()
            observePrefs()
        }
        loadHomeData()
    }

    private suspend fun observePrefs() {
        repository.userPreferencesFlow()
            .collectLatest { prefs ->
                _uiState.update { currentState ->
                    currentState.copy(darkMode = prefs.showDarkMode)
                }
            }
    }

    private fun loadHomeData() {

        viewModelScope.launch {

            val suggestionTitle = foodNames.random()

            val suggestionDeferred = async { repository.fetchFoodData(suggestionTitle) }
            val saladsDeferred = async { repository.fetchFoodData("salad") }
            val pizzasDeferred = async { repository.fetchFoodData("pizza") }
            val chineseDeferred = async { repository.fetchFoodData("chinese") }

            val suggestionResult = suggestionDeferred.await()
            val saladsResult = saladsDeferred.await()
            val pizzasResult = pizzasDeferred.await()
            val chineseResult = chineseDeferred.await()

            Log.i("SiaTag", "loadHomeData:  suggestion is $suggestionResult , salad is $saladsResult , pizza is $pizzasResult , chines is $chineseResult ")

            _uiState.update { currentState ->
                currentState.copy(
                    suggestionResults = suggestionResult,
                    saladsResults = saladsResult,
                    pizzasResults = pizzasResult,
                    chineseResults = chineseResult
                )
            }
        }
    }

    fun onSearchTextChange(searchingText: String) {
        _uiState.update { it.copy(searchText = searchingText) }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onSearchTriggered() {

        viewModelScope.launch {

            _uiState.update { currentSate ->
                currentSate.copy(searchedResults = DataResponse.Loading)
            }

            val searchResults = async { repository.fetchFoodData(_uiState.value.searchText) }

            _uiState.update { currentSate ->
                currentSate.copy(searchedResults = searchResults.await())
            }

            updateSearchHistory(_uiState.value.searchText)
        }
    }

    fun onActiveSearchBarChange(isSearching: Boolean) {
        _uiState.update { currentState -> currentState.copy(isActiveSearchBar = isSearching) }

        if (!isSearching) {
            onSearchTextChange("")
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun updateSearchHistory(searchedText: String) {

        _uiState.update { currentState ->
            currentState.copy(
                searchHistory = currentState.searchHistory + SearchedHistory(
                    searchedText = searchedText,
                    searchTime = nowStamp()
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onReUseSearchedHistory(searchedHistoryText: String) {

        viewModelScope.launch {

            _uiState.update { currentSate ->
                currentSate.copy(searchedResults = DataResponse.Loading)
            }

            val searchResults = async { repository.fetchFoodData(searchedHistoryText) }

            _uiState.update { currentSate ->
                currentSate.copy(searchedResults = searchResults.await())
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

        viewModelScope.launch {

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


            val results = async {
                repository.fetchFoodData(
                    foodName = _uiState.value.searchText,
                    dietFilter = _uiState.value.dietsFilters.find { it.selected }?.title,
                    dishTypeFilter = _uiState.value.dishTypesFilters.find { it.selected }?.title,
                    mealTypeFilter = _uiState.value.mealTypesFilters.find { it.selected }?.title,
                    cuisineTypeFilter = _uiState.value.cuisineTypeFilters.find { it.selected }?.title

                )
            }

            _uiState.update { currentSate ->
                currentSate.copy(searchedResults = results.await())
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
        viewModelScope.launch {
            repository.fetchInitialUserPreferences()
            observePrefs()
            loadHomeData()
        }
    }

    private fun List<TagFilterItem>.toggleExactly(one: TagFilterItem) =
        map { it.copy(selected = it.id == one.id && !one.selected) }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nowStamp(): String = DateTimeFormatter
        .ofPattern("HH:mm:ss, MMM d")
        .format(Instant.now().atZone(ZoneId.systemDefault()))
}