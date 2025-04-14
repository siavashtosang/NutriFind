package com.example.nutrifind.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.nutrifind.ui.NutriFindViewModel
import com.example.nutrifind.ui.features.category.categoryItemsScreen
import com.example.nutrifind.ui.features.category.categoryResultsScreen
import com.example.nutrifind.ui.features.category.navigateToCategoryItemsScreen
import com.example.nutrifind.ui.features.category.navigateToCategoryResultsScreen
import com.example.nutrifind.ui.features.home.GraphHome
import com.example.nutrifind.ui.features.home.homeScreen
import kotlin.reflect.KClass

@Composable
fun NutriFindNavHost(
    modifier: Modifier = Modifier,
    viewModel: NutriFindViewModel = hiltViewModel(),
    navController: NavHostController,
    startDestination: KClass<*> = GraphHome::class
) {

    fun onBackClick() {
        navController.popBackStack()
    }

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination,
        builder = {
            homeScreen(
                viewModel = viewModel,
                nestedGraphs = {
                    categoryItemsScreen(
                        viewModel = viewModel,
                        onBackClick = { onBackClick() },
                        navigateToCategoryResults = {navController.navigateToCategoryResultsScreen()}
                    )
                    categoryResultsScreen(
                        viewModel = viewModel,
                        onBackClick = { onBackClick() },
                        onFoodCardClick = {
                            //TODO navigate to food details screen
                        }
                    )
                },
                onCategoryItemClick = {
                    navController.navigateToCategoryResultsScreen()
                },
                onAllCategoriesClick = { navController.navigateToCategoryItemsScreen() },
                onMoreCategoriesButtonClick = { navController.navigateToCategoryItemsScreen() },
                onFoodCardClick = {
                    //TODO navigate to food details screen
                }
            )
        }
    )

}