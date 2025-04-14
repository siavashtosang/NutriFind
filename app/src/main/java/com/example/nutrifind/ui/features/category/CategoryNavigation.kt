package com.example.nutrifind.ui.features.category

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.nutrifind.ui.NutriFindViewModel
import kotlinx.serialization.Serializable

@Serializable
object ScreenCategoryItems

@Serializable
object ScreenCategoryResults

fun NavController.navigateToCategoryResultsScreen() {
    navigate(ScreenCategoryResults)
}

fun NavController.navigateToCategoryItemsScreen() {
    navigate(ScreenCategoryItems)
}

fun NavGraphBuilder.categoryResultsScreen(
    viewModel: NutriFindViewModel,
    onFoodCardClick: (String?) -> Unit,
    onBackClick: () -> Unit
) {
    composable<ScreenCategoryResults> {
        CategoryResultsScreenRout(
            viewModel = viewModel,
            onFoodCardClick = onFoodCardClick,
            onBack = onBackClick,
        )
    }
}

fun NavGraphBuilder.categoryItemsScreen(
    viewModel: NutriFindViewModel,
    onBackClick: () -> Unit,
    navigateToCategoryResults: () -> Unit,
) {
    composable<ScreenCategoryItems> {
        CategoryItemsScreenRout(
            viewModel = viewModel,
            onBackClick = onBackClick,
            navigateToCategoryResults = navigateToCategoryResults
        )
    }
}