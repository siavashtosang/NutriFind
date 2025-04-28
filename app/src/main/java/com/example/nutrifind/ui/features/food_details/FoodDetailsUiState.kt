package com.example.nutrifind.ui.features.food_details

import com.example.nutrifind.data.model.Ingredients
import com.example.nutrifind.data.network.DataResponse


data class FoodDetailsUiState(
    val recipeUri: String = "",
    val foodName: String = "",
    val foodImage: String = "",
    val calories: String = "",
    val dailyValue: String = "",
    val ingredients: List<Ingredients> = emptyList(),
    val nutritionList: List<Nutrition> = emptyList(),
    val results: DataResponse =  DataResponse.Loading
)

data class Nutrition(
    val name: String,
    val value: String,
    val unit: String
)
