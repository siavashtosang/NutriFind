package com.example.nutrifind.ui.features.home

import androidx.compose.runtime.Immutable

@Immutable
data class HomeUiState(
    val newSuggestDailyFood: String = "",
    val searchText: String = "",
    val isSearching: Boolean = false,
    val searchHistory: List<SearchedHistory> = emptyList(),
)

data class SearchedHistory(
    val id: Int = -1,
    val searchedText: String,
    val searchTime: String,
)