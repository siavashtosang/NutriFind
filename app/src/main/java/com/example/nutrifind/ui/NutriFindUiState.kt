package com.example.nutrifind.ui

import androidx.compose.runtime.Immutable
import com.example.nutrifind.data.model.Hits
import com.example.nutrifind.data.network.DataResponse

@Immutable
data class HomeUiState(
    val searchText: String = "",
    val foodSuggestionTitle: String = "",
    val categoryResultsScreenTitle: String = "",
    val isActiveSearchBar: Boolean = false,
    val searchHistory: List<SearchedHistory> = emptyList(),
    val searchedResults: DataResponse = DataResponse.Loading,
    val foodsSuggestion: DataResponse = DataResponse.Loading,
    val salads: DataResponse = DataResponse.Loading,
    val pizzas: DataResponse = DataResponse.Loading,
    val chinese: DataResponse = DataResponse.Loading,
    val favoriteDishes: List<Hits> = emptyList()
)

@Immutable
data class SearchedHistory(
    val searchedText: String,
    val searchTime: String,
)