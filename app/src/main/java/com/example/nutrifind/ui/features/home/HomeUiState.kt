package com.example.nutrifind.ui.features.home

import androidx.compose.runtime.Immutable
import com.example.nutrifind.data.model.Hits
import com.example.nutrifind.data.network.DataResponse
import com.example.nutrifind.data.offline.TagFilterItem
import com.example.nutrifind.data.offline.cuisineTypes
import com.example.nutrifind.data.offline.dietsFilterList
import com.example.nutrifind.data.offline.dishTypesFilterList
import com.example.nutrifind.data.offline.mealTypesFilterList

@Immutable
data class HomeUiState(
    val searchText: String = "",
    val isDarkTheme: Boolean = false,
    val foodSuggestionTitle: String = "",
    val selectedCuisineType: String = "",
    val isActiveSearchBar: Boolean = false,
    val filterItems: Int? = null,
    val dietsFilters: List<TagFilterItem> = dietsFilterList,
    val dishTypesFilters: List<TagFilterItem> = dishTypesFilterList,
    val mealTypesFilters: List<TagFilterItem> = mealTypesFilterList,
    val cuisineTypeFilters: List<TagFilterItem> = cuisineTypes,
    val searchHistory: List<SearchedHistory> = emptyList(),
    val searchedResults: DataResponse = DataResponse.Loading,
    val foodsSuggestion: DataResponse = DataResponse.Loading,
    val salads: DataResponse = DataResponse.Loading,
    val pizzas: DataResponse = DataResponse.Loading,
    val chinese: DataResponse = DataResponse.Loading,
)

@Immutable
data class SearchedHistory(
    val searchedText: String,
    val searchTime: String,
)