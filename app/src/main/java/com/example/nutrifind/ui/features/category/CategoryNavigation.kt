package com.example.nutrifind.ui.features.category

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.example.nutrifind.data.remote.model.Hits
import kotlinx.serialization.Serializable

@Serializable
object ScreenCategoryItems

@Serializable
data class ScreenCategoryResults(
    val categoryTitle: String
)

fun NavController.navigateToCategoryResultsScreen(categoryTitle: String) {
    navigate(ScreenCategoryResults(categoryTitle = categoryTitle))
}

fun NavController.navigateToCategoryItemsScreen() {
    navigate(ScreenCategoryItems)
}

fun NavGraphBuilder.categoryResultsScreen(
    onFoodCardClick: (String) -> Unit,
    onBackClick: () -> Unit
) {
    composable<ScreenCategoryResults> {
        CategoryResultsScreenRout(
            onFoodCardClick = onFoodCardClick,
            onBack = onBackClick,
        )
    }
}

fun NavGraphBuilder.categoryItemsScreen(
    onBackClick: () -> Unit,
    onCategoryItemClick: (String) -> Unit,
) {
    composable<ScreenCategoryItems> {
        CategoryItemsScreenRout(
            onBackClick = onBackClick,
            onCategoryItemClick = onCategoryItemClick
        )
    }
}