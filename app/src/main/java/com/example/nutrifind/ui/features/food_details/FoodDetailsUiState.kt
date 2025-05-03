package com.example.nutrifind.ui.features.food_details

import com.example.nutrifind.data.network.DataResponse
import com.example.nutrifind.utils.Food


data class FoodDetailsUiState(
    val food: Food = Food(),
    val results: DataResponse = DataResponse.Loading
)

