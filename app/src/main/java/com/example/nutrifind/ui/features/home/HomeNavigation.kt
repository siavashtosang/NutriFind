package com.example.nutrifind.ui.features.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import kotlinx.serialization.Serializable

@Serializable
object GraphHome

@Serializable
object ScreenHome

@RequiresApi(Build.VERSION_CODES.O)
fun NavGraphBuilder.homeScreen(
    nestedGraphs: NavGraphBuilder.() -> Unit,
    onCategoryItemClick: (String) -> Unit,
    onAllCategoriesClick: () -> Unit,
    onMoreCategoriesButtonClick: () -> Unit,
    onFoodCardClick: (String) -> Unit
) {
    navigation<GraphHome>(
        startDestination = ScreenHome,
        builder = {
            composable<ScreenHome> {
                HomeScreenRote(
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