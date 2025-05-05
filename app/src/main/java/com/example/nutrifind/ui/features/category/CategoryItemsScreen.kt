package com.example.nutrifind.ui.features.category

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nutrifind.R
import com.example.nutrifind.data.local.foodCategoryItems
import com.example.nutrifind.ui.component.FoodCategoryCard
import com.example.nutrifind.ui.component.NutriFindTopAppBar
import com.example.nutrifind.ui.theme.NutriFindTheme

@Composable
fun CategoryItemsScreenRout(
    modifier: Modifier = Modifier,
    onCategoryItemClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {

    CategoryItemsScreen(
        modifier = modifier,
        onBackClick = onBackClick,
        onCategoryItemClick = onCategoryItemClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CategoryItemsScreen(
    modifier: Modifier = Modifier,
    onCategoryItemClick: (String) -> Unit,
    onBackClick: () -> Unit,
) {
    val scrollState = rememberLazyGridState()

    var categoryScreenTopAppBarState by remember { mutableStateOf(CategoryTopAppBarState.Collapse) }
    val scrollY by remember { derivedStateOf { scrollState.firstVisibleItemIndex } }


    categoryScreenTopAppBarState = if (scrollY != 0) {
        CategoryTopAppBarState.Extend
    } else {
        CategoryTopAppBarState.Collapse
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            NutriFindTopAppBar(
                title = stringResource(R.string.food_category),
                showTitle = categoryScreenTopAppBarState == CategoryTopAppBarState.Extend,
                onNavigationIconClick = onBackClick
            )
        },
        containerColor = MaterialTheme.colorScheme.background

    ) { paddingValues ->

        LazyVerticalGrid(
            columns = GridCells.Adaptive(80.dp),
            modifier = Modifier.padding(paddingValues),
            state = scrollState,
            contentPadding = PaddingValues(horizontal = 28.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(32.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp),
        ) {

            item(span = { GridItemSpan(3) }) {
                Text(
                    stringResource(R.string.food_category),
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight(713)),
                )
            }

            items(foodCategoryItems) { category ->
                FoodCategoryCard(title = category.title,
                    image = category.image,
                    onClick = {
                        onCategoryItemClick(category.title)
                    }
                )
            }

        }
    }
}

@Preview
@Composable
private fun PreviewCategoryScreen() {
    NutriFindTheme {
        CategoryItemsScreen(
            onBackClick = {},
            onCategoryItemClick = {}
        )
    }
}
