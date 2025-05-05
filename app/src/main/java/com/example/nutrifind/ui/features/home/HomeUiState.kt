package com.example.nutrifind.ui.features.home

import androidx.compose.runtime.Immutable
import com.example.nutrifind.data.local.Food
import com.example.nutrifind.data.local.TagFilterItem
import com.example.nutrifind.data.local.cuisineTypes
import com.example.nutrifind.data.local.dietsFilterList
import com.example.nutrifind.data.local.dishTypesFilterList
import com.example.nutrifind.data.local.mealTypesFilterList
import com.example.nutrifind.data.remote.network.DataResponse

@Immutable
data class HomeUiState(
    val searchText: String = "",
    val isDarkTheme: Boolean = false,
    val results: DataResponse = DataResponse.Loading,
    val foodSuggestionTitle: String = "",
    val selectedCuisineType: String = "",
    val isActiveSearchBar: Boolean = false,
    val filterItems: Int? = null,
    val dietsFilters: List<TagFilterItem> = dietsFilterList,
    val dishTypesFilters: List<TagFilterItem> = dishTypesFilterList,
    val mealTypesFilters: List<TagFilterItem> = mealTypesFilterList,
    val cuisineTypeFilters: List<TagFilterItem> = cuisineTypes,
    val searchHistory: List<SearchedHistory> = emptyList(),
    val searchedResultsState: DataResponse = DataResponse.Loading,
    val searchedResults: List<Food> = emptyList(),
    val foodsSuggestion: List<Food> = emptyList(),
    val salads: List<Food> = emptyList(),
    val pizzas: List<Food> = emptyList(),
    val chinese: List<Food> = emptyList(),
)

@Immutable
data class SearchedHistory(
    val searchedText: String,
    val searchTime: String,
)