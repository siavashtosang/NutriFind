package com.example.nutrifind.ui.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nutrifind.R
import com.example.nutrifind.data.model.Hits
import com.example.nutrifind.data.network.DataResponse
import com.example.nutrifind.data.offline.FoodCategoryItem
import com.example.nutrifind.data.offline.fakeFoodData
import com.example.nutrifind.data.offline.foodCategoryItems
import com.example.nutrifind.ui.NutriFindViewModel
import com.example.nutrifind.ui.SearchedHistory
import com.example.nutrifind.ui.component.FoodCategoryCard
import com.example.nutrifind.ui.component.MoreButtonCard
import com.example.nutrifind.ui.component.SearchResults
import com.example.nutrifind.ui.component.SearchedHistoryList
import com.example.nutrifind.ui.component.TagFilter
import com.example.nutrifind.ui.component.TopFoodCard
import com.example.nutrifind.ui.component.VerticalFoodCard
import com.example.nutrifind.ui.theme.NutriFindTheme
import kotlin.math.roundToInt

@Composable
fun HomeScreenRote(
    modifier: Modifier = Modifier,
    viewModel: NutriFindViewModel,
    onCategoryItemClick: () -> Unit,
    onAllCategoriesClick: () -> Unit,
    onMoreCategoriesButtonClick: () -> Unit,
    onFoodCardClick: (foodUri: String?) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    HomeScreen(
        modifier = modifier,
        searchText = uiState.searchText,
        foodsSuggestionTitle = uiState.foodSuggestionTitle,
        isActiveSearchBar = uiState.isActiveSearchBar,
        searchedHistories = uiState.searchHistory,
        suggestionFood = uiState.foodsSuggestion,
        searchedResults = uiState.searchedResults,
        favoriteDishes = uiState.favoriteDishes,
        salads = uiState.salads,
        pizzas = uiState.pizzas,
        chinese = uiState.chinese,
        onSearchTextChange = viewModel::onSearchTextChange,
        onActiveSearchBarChange = viewModel::onActiveSearchBarChange,
        onSearchTriggered = viewModel::onSearchTriggered,
        onClearSearchedHistoriesClick = viewModel::onClearSearchedHistoriesClick,
        onCategoryItemClick = {
            viewModel.onCategorySearched(it)
            onCategoryItemClick()
        },
        onAllCategoriesClick = onAllCategoriesClick,
        onMoreCategoriesButtonClick = onMoreCategoriesButtonClick,
        onFoodCardClick = onFoodCardClick,
        onReUseSearchedHistory = viewModel::onReUseSearchedHistory,
    )
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    searchText: String,
    foodsSuggestionTitle: String,
    isActiveSearchBar: Boolean,
    searchedHistories: List<SearchedHistory>,
    suggestionFood: DataResponse,
    searchedResults: DataResponse,
    salads: DataResponse,
    pizzas: DataResponse,
    chinese: DataResponse,
    favoriteDishes: List<Hits>,
    onSearchTextChange: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    onReUseSearchedHistory: (String) -> Unit,
    onFoodCardClick: (foodUri: String?) -> Unit,
    onActiveSearchBarChange: (Boolean) -> Unit,
    onClearSearchedHistoriesClick: () -> Unit,
    onCategoryItemClick: (categoryTitle: String) -> Unit,
    onAllCategoriesClick: () -> Unit,
    onMoreCategoriesButtonClick: () -> Unit,
) {

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValues ->

        val lazyListState = rememberLazyListState()

        if (isActiveSearchBar) {
            HomeScreenSearchBar(
                modifier = Modifier,
                searchText = searchText,
                isActiveSearchBar = isActiveSearchBar,
                searchedHistory = searchedHistories,
                searchedResults = searchedResults,
                onSearchTextChange = onSearchTextChange,
                onSearchTriggered = onSearchTriggered,
                onFoodCardClick = onFoodCardClick,
                onActiveSearchBarChange = onActiveSearchBarChange,
                onClearSearchedHistoriesClick = onClearSearchedHistoriesClick,
                onReUseSearchedHistory = onReUseSearchedHistory,
            )
        } else {

            LazyColumn(
                modifier = Modifier
                    .padding(paddingValues),
                state = lazyListState
            ) {
                item {
                    TopScreenBanner()
                }

                item {
                    HomeScreenSearchBar(
                        modifier = Modifier,
                        searchText = searchText,
                        isActiveSearchBar = isActiveSearchBar,
                        searchedHistory = searchedHistories,
                        searchedResults = searchedResults,
                        onSearchTextChange = onSearchTextChange,
                        onSearchTriggered = onSearchTriggered,
                        onFoodCardClick = onFoodCardClick,
                        onActiveSearchBarChange = onActiveSearchBarChange,
                        onClearSearchedHistoriesClick = onClearSearchedHistoriesClick,
                        onReUseSearchedHistory = onReUseSearchedHistory,
                    )
                }

                item {
                    HomeScreenContent(
                        modifier = Modifier,
                        foodsSuggestionTitle = foodsSuggestionTitle,
                        onCategoryItemClick = onCategoryItemClick,
                        onAllCategoriesClick = onAllCategoriesClick,
                        onMoreCategoriesButtonClick = onMoreCategoriesButtonClick,
                        foodsSuggestion = suggestionFood,
                        favoriteDishes = favoriteDishes,
                        saladList = salads,
                        pizzaList = pizzas,
                        chineseList = chinese,
                        onFoodSuggestionClick = onFoodCardClick,
                        onFavoriteFoodClick = onFoodCardClick,
                        onTopFoodClick = onFoodCardClick,
                    )
                }
            }

        }

    }
}

@Composable
private fun TopScreenBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(280.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize(),
            painter = painterResource(R.drawable.img_top_home_screen_food),
            contentDescription = stringResource(R.string.massage_top_home_screen_title),
            contentScale = ContentScale.FillBounds,
            alignment = Alignment.TopCenter
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                text = stringResource(R.string.massage_top_home_screen_title),
                style = MaterialTheme.typography.displaySmall.copy(
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight(900)
                )
            )

            Text(
                text = stringResource(R.string.massage_top_home_screen_subtitle_line1) +
                        stringResource(R.string.massage_top_home_screen_subtitle_line2),
                style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.surface)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeScreenSearchBar(
    modifier: Modifier = Modifier,
    searchText: String,
    isActiveSearchBar: Boolean,
    searchedResults: DataResponse,
    searchedHistory: List<SearchedHistory>,
    onSearchTextChange: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    onActiveSearchBarChange: (Boolean) -> Unit,
    onFoodCardClick: (foodUri: String?) -> Unit,
    onClearSearchedHistoriesClick: () -> Unit,
    onReUseSearchedHistory: (String) -> Unit,
) {

    var showSearchResult by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        SearchBar(
            modifier = Modifier,
            query = searchText,
            onQueryChange = onSearchTextChange,
            onSearch = {
                onSearchTriggered()
                showSearchResult = true
            },
            active = isActiveSearchBar,
            onActiveChange = onActiveSearchBarChange,
            tonalElevation = 0.dp,
            colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_foods_drinks),
                    style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.outline)
                )
            },
            leadingIcon = {
                Icon(
                    painter = painterResource(
                        if (isActiveSearchBar) R.drawable.ic_arrow_left_24 else R.drawable.ic_search_24
                    ),
                    contentDescription = if (isActiveSearchBar) "ic_arrow_left_24" else "ic_search",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.clickable(enabled = isActiveSearchBar) {
                        onActiveSearchBarChange(false)
                        showSearchResult = false
                    }
                )
            }

        ) {
            if (isActiveSearchBar) {
                if (showSearchResult) {

                    when (searchedResults) {
                        DataResponse.Error -> {
                            // TODO: Handle error state, e.g., display an error message
                            Text("Error loading results")
                        }

                        DataResponse.Loading -> {
                            // TODO: Display a loading indicator
                            Text("Loading...")
                        }

                        is DataResponse.Success -> {
                            searchedResults.apiEdamam?.hits?.let { hits ->
                                if (hits.isNotEmpty()) {
                                    SearchResults(
                                        searchedFood = searchText,
                                        searchedResults = hits,
                                        onSearchedFoodCardClick = { onFoodCardClick(it) }
                                    )
                                } else {
                                    // TODO: Display a "no results found" message
                                    Text("No results found")
                                }
                            } ?: run {
                                // TODO: Handle case where apiEdamam or hits is null, possibly an unexpected API response
                                Text("Unexpected API response")
                            }
                        }
                    }


                } else {
                    SearchedHistoryList(
                        searchedHistory = searchedHistory,
                        onClearButtonClick = onClearSearchedHistoriesClick,
                        onReUseSearchedHistory = onReUseSearchedHistory
                    )
                }


            } else {
                showSearchResult = false
            }
        }
    }

}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    foodsSuggestionTitle: String,
    foodsSuggestion: DataResponse,
    favoriteDishes: List<Hits>,
    saladList: DataResponse,
    pizzaList: DataResponse,
    chineseList: DataResponse,
    onCategoryItemClick: (categoryTitle: String) -> Unit,
    onAllCategoriesClick: () -> Unit,
    onMoreCategoriesButtonClick: () -> Unit,
    onFoodSuggestionClick: (foodUri: String?) -> Unit,
    onTopFoodClick: (foodUri: String?) -> Unit,
    onFavoriteFoodClick: (foodUri: String?) -> Unit,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(R.string.food_category),
                style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight(
                        600
                    )
                )
            )

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                modifier = Modifier
                    .height(16.dp)
                    .clip(RoundedCornerShape(14.dp)),
                shape = RoundedCornerShape(14.dp),
                contentPadding = PaddingValues(0.dp),
                onClick = { onAllCategoriesClick() },
            ) {
                Text(
                    text = stringResource(R.string.view_all),
                    style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.outline)
                )

                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right_16),
                    contentDescription = "ic_arrow_right_24",
                    tint = MaterialTheme.colorScheme.outline,
                )
            }
        }

        LazyRow(
            modifier = Modifier,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.Start,
        ) {
            items(foodCategoryItems.take(5)) { items: FoodCategoryItem ->
                FoodCategoryCard(
                    modifier = Modifier.padding(end = 12.dp),
                    title = items.title,
                    image = items.image,
                    onClick = { onCategoryItemClick(items.title) })

            }

            item {
                MoreButtonCard(onClick = onMoreCategoriesButtonClick)
            }
        }

        Text(
            text = foodsSuggestionTitle,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, top = 38.dp, bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.Start
            ),
        ) {
            when (foodsSuggestion) {
                DataResponse.Error -> {
                    item { Text("Error") }
                }

                DataResponse.Loading -> {
                    item { Text("loading") }
                }

                is DataResponse.Success -> {

                    items(foodsSuggestion.apiEdamam?.hits?.take(5) ?: emptyList()) { food: Hits ->
                        VerticalFoodCard(
                            title = food.recipe?.label ?: "",
                            image = food.recipe?.images?.small?.url ?: "",
                            calories = food.recipe?.calories?.roundToInt() ?: 0,
                            ingredients = food.recipe?.ingredientLines?.size ?: 0,
                            onClick = { onFoodSuggestionClick(food.recipe?.uri) },
                        )
                    }

                }
            }

        }

        TopFoodCard(
            modifier = Modifier
                .padding(top = 55.dp),
            saladList = saladList,
            pizzaList = pizzaList,
            chineseList = chineseList,
            onClick = onTopFoodClick
        )

        Text(
            text = stringResource(R.string.favorite_dishes),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, top = 64.dp, bottom = 8.dp)
        )

        LazyRow(
            modifier = Modifier,
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.Start
            ),
        ) {
            items(favoriteDishes) { food: Hits ->
                VerticalFoodCard(
                    title = food.recipe?.label ?: "",
                    image = food.recipe?.image ?: "",
                    calories = food.recipe?.calories?.roundToInt() ?: 0,
                    ingredients = food.recipe?.ingredientLines?.size ?: 0,
                    onClick = { onFavoriteFoodClick(food.recipe?.uri) },
                )
            }
        }

        Text(
            text = stringResource(R.string.diets),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(start = 16.dp, top = 48.dp, bottom = 8.dp)
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            TagFilter(title = "High-protein") { }
        }

    }

}


@Preview
@Composable
private fun PreViewHomeScreen() {
    NutriFindTheme {
        HomeScreen(
            modifier = Modifier,
            searchText = "",
            foodsSuggestionTitle = "random food name",
            isActiveSearchBar = false,
            searchedHistories = MutableList(4) {
                SearchedHistory(searchedText = "Hot Dog", searchTime = "Today")
            },
            suggestionFood = DataResponse.Success(fakeFoodData),
            searchedResults = DataResponse.Success(fakeFoodData),
            salads = DataResponse.Success(fakeFoodData),
            pizzas = DataResponse.Success(fakeFoodData),
            chinese = DataResponse.Success(fakeFoodData),
            favoriteDishes = fakeFoodData.hits,
            onSearchTextChange = {},
            onActiveSearchBarChange = {},
            onClearSearchedHistoriesClick = {},
            onCategoryItemClick = {},
            onAllCategoriesClick = {},
            onMoreCategoriesButtonClick = {},
            onSearchTriggered = {},
            onReUseSearchedHistory = {},
            onFoodCardClick = {},
        )
    }
}