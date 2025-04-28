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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nutrifind.R
import com.example.nutrifind.data.model.Hits
import com.example.nutrifind.data.network.DataResponse
import com.example.nutrifind.data.offline.fakeFoodData
import com.example.nutrifind.ui.component.HorizontalFoodCard
import com.example.nutrifind.ui.component.NutriFindTopAppBar
import kotlin.math.roundToInt

@Composable
fun CategoryResultsScreenRout(
    modifier: Modifier = Modifier,
    viewModel: CategoryViewModel = hiltViewModel(),
    onFoodCardClick: (String) -> Unit,
    onBack: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()


    when (uiState.results) {
        is DataResponse.Error -> {
            //TODO Error Screen
            Text(stringResource(R.string.error))
        }

        DataResponse.Loading -> {
            //TODO Loading Screen
            Text(stringResource(R.string.loading))
        }

        is DataResponse.Success -> {
            CategoryResultsScreen(
                modifier = modifier,
                title = uiState.categoryResultsScreenTitle,
                results = (uiState.results as DataResponse.Success).apiEdamam?.hits,
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
    results: List<Hits>?,
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

            items(results ?: emptyList()) { hits ->
                HorizontalFoodCard(
                    title = hits.recipe?.label ?: "",
                    image = hits.recipe?.images?.thumbnail?.url ?: "",
                    calories = hits.recipe?.calories?.roundToInt() ?: 0,
                    ingredients = hits.recipe?.ingredientLines?.size ?: 0,
                    onClick = { onFoodCardClick(hits.recipe?.label!!) }
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
            results = fakeFoodData.hits,
            onBack = {},
            onFoodCardClick = {}
        )
    }
}

