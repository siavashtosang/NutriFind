package com.example.nutrifind.ui.features.food_details

import com.example.nutrifind.data.remote.network.DataResponse


data class FoodDetailsUiState(
    val selectedFood: String = "",
    val foodResults: DataResponse = DataResponse.Loading
)

