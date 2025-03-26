package com.example.nutrifind.ui.features.home

import androidx.compose.runtime.Immutable
import com.example.nutrifind.data.offline.Food

@Immutable
data class HomeUiState(
    val searchText: String = "",
    val isSearching: Boolean = false,
    val searchHistory: List<SearchedHistory> = emptyList(),
    val foodSuggestion: List<Food> = emptyList(),
    val topFood: List<Food> = emptyList(),
)

data class SearchedHistory(
    val id: Int = -1,
    val searchedText: String,
    val searchTime: String,
)
