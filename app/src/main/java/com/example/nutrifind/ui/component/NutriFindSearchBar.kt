package com.example.nutrifind.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nutrifind.R
import com.example.nutrifind.data.model.Ingredients
import com.example.nutrifind.ui.features.home.SearchedHistory
import com.example.nutrifind.ui.theme.NutriFindTheme
import com.example.nutrifind.utils.Food

@Composable
fun SearchedHistoryList(
    modifier: Modifier = Modifier,
    searchedHistory: List<SearchedHistory>?,
    onClearButtonClick: () -> Unit,
    onReUseSearchedHistory: (searchedText: String) -> Unit,
) {

    if (searchedHistory.isNullOrEmpty()) {

        SearchedHistoryEmptyState(modifier = modifier)

    } else {

        LazyColumn(
            modifier = modifier,
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            item {
                SearchedHistoryHeader(onClearButtonClick)
            }

            items(searchedHistory) { searchedItem ->
                SearchedHistoryItem(searchedItem, onReUseSearchedHistory = onReUseSearchedHistory)
            }
        }
    }

}

@Composable
private fun SearchedHistoryHeader(onClearButtonClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = stringResource(R.string.recent_searches),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(R.string.clear),
            style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.outline),
            textAlign = TextAlign.Center
        )

        IconButton(
            modifier = Modifier.size(20.dp),
            onClick = onClearButtonClick
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_xcross_16),
                contentDescription = stringResource(R.string.clear),
                tint = MaterialTheme.colorScheme.outline
            )
        }
    }
}

@Composable
private fun SearchedHistoryItem(
    searchedItem: SearchedHistory,
    onReUseSearchedHistory: (searchedText: String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .background(color = Color.Transparent)
                .clip(shape = CircleShape)
                .border(
                    width = 1.dp,
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.outline
                ),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_time_20),
                contentDescription = stringResource(R.string.search_time_icon)
            )
        }

        Text(
            text = searchedItem.searchedText,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.padding(start = 12.dp, end = 6.dp)
        )
        Text(
            text = searchedItem.searchTime,
            style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.outline
            )
        )
        Spacer(modifier = Modifier.weight(1f))

        IconButton(onClick = { onReUseSearchedHistory(searchedItem.searchedText) }) {
            Icon(
                painter = painterResource(R.drawable.ic_arrow_right_24),
                contentDescription = stringResource(R.string.navigate_to_search),
                tint = MaterialTheme.colorScheme.outline,
            )
        }
    }
}


@Composable
fun SearchedHistoryEmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(color = MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_search_24),
                contentDescription = stringResource(R.string.no_recent_searches)
            )
        }

        Text(
            text = stringResource(R.string.you_haven_t_search_anything_yet),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
        )

        Text(
            text = stringResource(R.string.search_by_food_names),
            style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.outline),
        )
    }
}

@Composable
fun SearchResults(
    modifier: Modifier = Modifier,
    searchedFood: String,
    filterItems: Int?,
    searchResults: List<Food>,
    onSearchedFoodCardClick: (String) -> Unit,
    onFilterButtonClick: () -> Unit
) {

    val gradientColor = listOf(Color.Transparent, MaterialTheme.colorScheme.background)

    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(modifier = Modifier) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .height(40.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.search_results_for, searchedFood),
                    style = MaterialTheme.typography.titleMedium,
                )
            }

            LazyColumn(
                contentPadding = PaddingValues(start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(searchResults) { food ->
                    HorizontalFoodCard(
                        title = food.name,
                        image = food.image,
                        calories = food.calories,
                        ingredients = food.ingredients.size,
                        onClick = { onSearchedFoodCardClick(food.name) }
                    )
                }
            }
        }

        Box(
            modifier = Modifier
                .height(100.dp)
                .fillMaxWidth()
                .background(brush = Brush.verticalGradient(colors = gradientColor)),
            contentAlignment = Alignment.BottomCenter
        ) {
            FilterButton(
                modifier = Modifier.padding(bottom = 16.dp),
                filterItems = filterItems,
                onClick = onFilterButtonClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchedHistoryList() {
    NutriFindTheme {
        SearchedHistoryList(
            searchedHistory = MutableList(size = 4) {
                SearchedHistory(
                    searchedText = "Sushi",
                    searchTime = "1 Week ago"
                )
            },
            onClearButtonClick = {},
            onReUseSearchedHistory = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchedHistoryHeader() {
    NutriFindTheme {
        SearchedHistoryHeader(onClearButtonClick = {})
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchedHistoryItem() {
    NutriFindTheme {
        SearchedHistoryItem(
            searchedItem = SearchedHistory(
                searchedText = "Sushi",
                searchTime = "1 Week ago"
            ),
            onReUseSearchedHistory = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchedHistoryEmptyState() {
    NutriFindTheme {
        SearchedHistoryEmptyState()
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSearchResults() {
    NutriFindTheme {
        SearchResults(
            searchedFood = "Pasta",
            filterItems = 4,
            searchResults = MutableList(4) {
                Food(
                    name = "Pasta alla Gracia Recipe",
                    image = "",
                    calories = 50,
                    ingredients = MutableList(4) {
                        Ingredients()
                    },
                    nutrition = emptyList(),
                )
            },
            onSearchedFoodCardClick = {},
            onFilterButtonClick = {}
        )
    }
}
