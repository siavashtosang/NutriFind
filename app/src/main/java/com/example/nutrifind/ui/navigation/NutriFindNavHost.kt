package com.example.nutrifind.ui.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.nutrifind.ui.features.category.categoryItemsScreen
import com.example.nutrifind.ui.features.category.categoryResultsScreen
import com.example.nutrifind.ui.features.category.navigateToCategoryItemsScreen
import com.example.nutrifind.ui.features.category.navigateToCategoryResultsScreen
import com.example.nutrifind.ui.features.food_details.foodDetailsScreen
import com.example.nutrifind.ui.features.food_details.navigateToFoodDetailsScreen
import com.example.nutrifind.ui.features.home.GraphHome
import com.example.nutrifind.ui.features.home.homeScreen
import kotlin.reflect.KClass

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NutriFindNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: KClass<*> = GraphHome::class,
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
                nestedGraphs = {
                    categoryItemsScreen(
                        onBackClick = { onBackClick() },
                        onCategoryItemClick = {
                            navController.navigateToCategoryResultsScreen(
                                categoryTitle = it
                            )
                        }
                    )
                    categoryResultsScreen(
                        onBackClick = { onBackClick() },
                        onFoodCardClick = { navController.navigateToFoodDetailsScreen(selectedFood = it) }
                    )
                    foodDetailsScreen(
                        onBackButtonClick = { onBackClick() }
                    )
                },
                onCategoryItemClick = {
                    navController.navigateToCategoryResultsScreen(categoryTitle = it)
                },
                onAllCategoriesClick = { navController.navigateToCategoryItemsScreen() },
                onMoreCategoriesButtonClick = { navController.navigateToCategoryItemsScreen() },
                onFoodCardClick = { navController.navigateToFoodDetailsScreen(selectedFood = it) },
            )
        }
    )
}