package com.example.nutrifind.ui.features.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nutrifind.data.model.Ingredients
import com.example.nutrifind.data.network.DataResponse
import com.example.nutrifind.ui.component.HorizontalFoodCard
import com.example.nutrifind.ui.component.NutriFindErrorScreen
import com.example.nutrifind.ui.component.NutriFindLoadingScreen
import com.example.nutrifind.ui.component.NutriFindTopAppBar
import com.example.nutrifind.utils.Food
import com.example.nutrifind.utils.convertToFoodClass

@Composable
fun CategoryResultsScreenRout(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = hiltViewModel(),
    onFoodCardClick: (String) -> Unit,
    onBack: () -> Unit,
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    when (uiState.results) {
        is DataResponse.Error -> {
            NutriFindErrorScreen(onRetry = viewModel::onRetry)
        }

        DataResponse.Loading -> {
            NutriFindLoadingScreen()
        }

        is DataResponse.Success -> {
            CategoryResultsScreen(
                modifier = modifier,
                title = uiState.categoryResultsScreenTitle,
                foods = (uiState.results as DataResponse.Success).apiEdamam?.convertToFoodClass()
                    ?: emptyList(),
                onFoodCardClick = onFoodCardClick,
                onBack = onBack
            )

        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryResultsScreen(
    modifier: Modifier = Modifier,
    title: String,
    foods: List<Food>,
    onFoodCardClick: (String) -> Unit,
    onBack: () -> Unit,
) {
    val lazyListState = rememberLazyListState()

    var categoryResultsTopAppBarState by remember { mutableStateOf(CategoryTopAppBarState.Collapse) }
    val scrollY by remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }


    categoryResultsTopAppBarState = if (scrollY != 0) {
        CategoryTopAppBarState.Extend
    } else {
        CategoryTopAppBarState.Collapse
    }

    Scaffold(
        topBar = {
            NutriFindTopAppBar(
                title = title,
                showTitle = categoryResultsTopAppBarState == CategoryTopAppBarState.Extend,
                onNavigationIconClick = onBack
            )
        }
    ) { paddingValues ->

        LazyColumn(
            modifier = modifier.padding(paddingValues),
            contentPadding = PaddingValues(horizontal = 16.dp),
            state = lazyListState,
            verticalArrangement = Arrangement.spacedBy(16.dp)

        ) {
            item {
                Text(
                    text = title, style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(start = 12.dp, bottom = 8.dp)
                )
            }

            items(foods) { food: Food ->
                HorizontalFoodCard(
                    title = food.name,
                    image = food.image,
                    calories = food.calories,
                    ingredients = food.ingredients.size,
                    onClick = { onFoodCardClick(food.name) }
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewCategoryResultsScreen(modifier: Modifier = Modifier) {
    MaterialTheme {
        CategoryResultsScreen(
            title = "Burger",
            foods = MutableList(4) {
                Food(
                    name = "Pasta alla Gracia Recipe",
                    image = "",
                    calories = 50,
                    ingredients = listOf(Ingredients()),
                    nutrition = emptyList(),
                )
            },
            onBack = {},
            onFoodCardClick = {}
        )
    }
}

