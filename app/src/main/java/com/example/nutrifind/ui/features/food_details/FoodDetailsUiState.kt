package com.example.nutrifind.ui.features.food_details

import com.example.nutrifind.data.local.offline.Food
import com.example.nutrifind.data.remote.network.DataResponse


data class FoodDetailsUiState(
    val food: Food = Food(),
    val results: DataResponse = DataResponse.Loading
)

