package com.example.nutrifind.ui.features.home

import com.example.nutrifind.data.local.offline.TagFilterItem
import com.example.nutrifind.data.local.offline.cuisineTypes
import com.example.nutrifind.data.local.offline.dietsFilterList
import com.example.nutrifind.data.local.offline.dishTypesFilterList
import com.example.nutrifind.data.local.offline.mealTypesFilterList
import com.example.nutrifind.data.remote.network.DataResponse


data class HomeUiState(
    val searchText: String = "",
    val darkMode: Boolean = false,
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
    val suggestionResults: DataResponse = DataResponse.Loading,
    val saladsResults: DataResponse = DataResponse.Loading,
    val pizzasResults: DataResponse = DataResponse.Loading,
    val chineseResults: DataResponse = DataResponse.Loading,
)

data class SearchedHistory(
    val searchedText: String,
    val searchTime: String,
)