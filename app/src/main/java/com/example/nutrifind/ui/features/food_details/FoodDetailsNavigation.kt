package com.example.nutrifind.ui.features.food_details

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data class ScreenFoodDetails(
   val selectedFood: String
)

fun NavController.navigateToFoodDetailsScreen(selectedFood: String){
    navigate(ScreenFoodDetails(selectedFood))
}

fun NavGraphBuilder.foodDetailsScreen(
    onBackButtonClick: () -> Unit
){
    composable<ScreenFoodDetails> {
        FoodDetailsScreenRout(
            onBackButtonClick = onBackButtonClick
        )
    }
}