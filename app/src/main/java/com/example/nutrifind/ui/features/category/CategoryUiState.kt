package com.example.nutrifind.ui.features.category

import com.example.nutrifind.data.remote.network.DataResponse


data class CategoryUiState(
    val categoryResultsScreenTitle: String = "",
    val results: DataResponse = DataResponse.Loading,
)

enum class CategoryTopAppBarState {
    Extend, Collapse
}
