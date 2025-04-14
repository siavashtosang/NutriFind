package com.example.nutrifind.ui.features.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.nutrifind.ui.NutriFindViewModel
import kotlinx.serialization.Serializable

@Serializable
object GraphHome

@Serializable
object ScreenHome

fun NavController.navigateToHome(navOption: NavOptions? = null) {
    this.navigate(GraphHome, navOption)
}

fun NavGraphBuilder.homeScreen(
    nestedGraphs: NavGraphBuilder.() -> Unit,
    viewModel: NutriFindViewModel,
    onCategoryItemClick: () -> Unit,
    onAllCategoriesClick: () -> Unit,
    onMoreCategoriesButtonClick: () -> Unit,
    onFoodCardClick: (foodUri: String?) -> Unit
) {
    navigation<GraphHome>(
        startDestination = ScreenHome,
        builder = {
            composable<ScreenHome> {
                HomeScreenRote(
                    viewModel = viewModel,
                    onCategoryItemClick = onCategoryItemClick,
                    onAllCategoriesClick = onAllCategoriesClick,
                    onMoreCategoriesButtonClick = onMoreCategoriesButtonClick,
                    onFoodCardClick = onFoodCardClick,
                )
            }
            nestedGraphs()
        }
    )
}