package com.example.nutrifind.ui.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nutrifind.R
import com.example.nutrifind.data.offline.Food
import com.example.nutrifind.data.offline.FoodCategoryItem
import com.example.nutrifind.data.offline.foodCategoryItems
import com.example.nutrifind.data.offline.foodNames
import com.example.nutrifind.ui.component.FoodCategoryCard
import com.example.nutrifind.ui.component.MoreButtonCard
import com.example.nutrifind.ui.component.TagFilter
import com.example.nutrifind.ui.component.TopFoodCard
import com.example.nutrifind.ui.component.VerticalFoodCard
import com.example.nutrifind.ui.theme.NutriFindTheme

@Composable
fun HomeScreenRote(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    searchText: String,
    isSearching: Boolean,
    searchedHistories: List<SearchedHistory>,
    foodSuggestions: List<Food>,
    favoriteDishes: List<Food>,
    saladList: List<Food>,
    pizzaList: List<Food>,
    chineseList: List<Food>,
    onSearchTextChange: (String) -> Unit,
    onToggleSearch: () -> Unit,
    onClearSearchedHistoriesClick: () -> Unit,
    onCategoryItemClick: (id: Int) -> Unit,
    onAllCategoriesClick: () -> Unit,
    onMoreCategoriesButtonClick: () -> Unit,
    onFavoriteFoodClick: (foodId: Int) -> Unit,
    onTopFoodClick: (foodId: Int) -> Unit,
    onFoodSuggestionClick: (foodId: Int) -> Unit,
) {

    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValue ->

        BoxWithConstraints(
            modifier = Modifier
                .padding(paddingValue)
                .verticalScroll(state = rememberScrollState()),
        ) {
            val searchBarTopPadding = (maxHeight / 3) - 20.dp
            val topContentPadding = (maxHeight / 3) + 20.dp

            TopScreenBanner()

            HomeScreenContent(
                modifier = Modifier
                    .padding(top = topContentPadding),
                onFoodCategoryItemClick = onCategoryItemClick,
                onAllCategoriesClick = onAllCategoriesClick,
                onMoreCategoriesButtonClick = onMoreCategoriesButtonClick,
                foodSuggestion = foodSuggestions,
                favoriteDishes = favoriteDishes,
                saladList = saladList,
                pizzaList = pizzaList,
                chineseList = chineseList,
                onFoodSuggestionClick = onFoodSuggestionClick,
                onFavoriteFoodClick = onFavoriteFoodClick,
                onTopFoodClick = onTopFoodClick,
            )

            HomeScreenSearchBar(
                modifier = Modifier
                    .padding(top = searchBarTopPadding),
                searchText = searchText,
                isSearching = isSearching,
                searchedHistory = searchedHistories,
                onSearchTextChange = onSearchTextChange,
                onToggleSearch = onToggleSearch,
                onClearSearchedHistoriesClick = onClearSearchedHistoriesClick,
            )
        }
    }
}

@Composable
private fun TopScreenBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .height(300.dp)
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth(),
            painter = painterResource(R.drawable.img_top_home_screen_food),
            contentDescription = stringResource(R.string.massage_top_home_screen_title),
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.TopCenter
        )

        Column(
            modifier = Modifier
                .padding(start = 16.dp, bottom = 40.dp)
                .fillMaxHeight(),
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
    isSearching: Boolean,
    searchedHistory: List<SearchedHistory>,
    onSearchTextChange: (String) -> Unit,
    onToggleSearch: () -> Unit,
    onClearSearchedHistoriesClick: () -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color = Color.Transparent),
        horizontalAlignment = Alignment.CenterHorizontally,
    )
    {
        SearchBar(
            modifier = Modifier,
            query = searchText,
            onQueryChange = onSearchTextChange,
            onSearch = onSearchTextChange,
            active = isSearching,
            onActiveChange = { onToggleSearch() },
            shadowElevation = 1.dp,
            tonalElevation = 0.dp,
            colors = SearchBarDefaults.colors(containerColor = MaterialTheme.colorScheme.surface),
            placeholder = {
                Text(
                    text = stringResource(R.string.search_foods_drinks),
                    style = MaterialTheme.typography.labelMedium.copy(color = MaterialTheme.colorScheme.outline)
                )
            },
            leadingIcon = {
                if (isSearching) {
                    Icon(
                        painter = painterResource(R.drawable.ic_arrow_left_24),
                        contentDescription = "ic_arrow_right_24",
                        tint = MaterialTheme.colorScheme.outline
                    )
                } else {
                    Icon(
                        painter = painterResource(R.drawable.ic_search_24),
                        contentDescription = "ic_search",
                        tint = MaterialTheme.colorScheme.outline
                    )
                }
            }

        ) {
            SearchedHistory(
                searchedHistory = searchedHistory,
                onClearButtonClick = onClearSearchedHistoriesClick
            )
        }
    }
}

@Composable
private fun HomeScreenContent(
    modifier: Modifier = Modifier,
    foodSuggestion: List<Food>,
    favoriteDishes: List<Food>,
    saladList: List<Food>,
    pizzaList: List<Food>,
    chineseList: List<Food>,
    onFoodCategoryItemClick: (id: Int) -> Unit,
    onAllCategoriesClick: () -> Unit,
    onMoreCategoriesButtonClick: () -> Unit,
    onFoodSuggestionClick: (foodId: Int) -> Unit,
    onTopFoodClick: (foodId: Int) -> Unit,
    onFavoriteFoodClick: (foodId: Int) -> Unit,
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 40.dp, bottom = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Food Category",
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
            items(foodCategoryItems) { items: FoodCategoryItem ->
                if (items.id <= 5) {
                    FoodCategoryCard(
                        modifier = Modifier.padding(end = 12.dp),
                        title = items.title,
                        image = items.image,
                        onClick = { onFoodCategoryItemClick(items.id) })
                }
            }

            item {
                MoreButtonCard(onClick = onMoreCategoriesButtonClick)
            }
        }

        Text(
            text = stringResource(R.string.new_in, foodNames.random()),
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
            items(foodSuggestion) { food: Food ->
                VerticalFoodCard(
                    title = food.title,
                    image = food.image,
                    calories = food.calories,
                    ingredients = food.ingredient,
                    onClick = { onFoodSuggestionClick(food.id) },
                )
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
            items(favoriteDishes) { food: Food ->
                VerticalFoodCard(
                    title = food.title,
                    image = food.image,
                    calories = food.calories,
                    ingredients = food.ingredient,
                    onClick = { onFavoriteFoodClick(food.id) }
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

@Composable
private fun SearchedHistory(
    modifier: Modifier = Modifier,
    searchedHistory: List<SearchedHistory>,
    onClearButtonClick: () -> Unit,
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(R.string.recent_searches),
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.weight(1f))

                Text(
                    text = stringResource(R.string.clear),
                    style = MaterialTheme.typography.labelMedium.copy(MaterialTheme.colorScheme.outline)
                )

                IconButton(
                    modifier = Modifier
                        .size(20.dp),
                    onClick = onClearButtonClick
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_xcross_16),
                        contentDescription = "ic_xcross_outline_16",
                        tint = MaterialTheme.colorScheme.outline
                    )
                }

            }
        }

        items(searchedHistory) { searchedText: SearchedHistory ->
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
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
                        contentDescription = "ic_time_outline_20"
                    )
                }

                Text(
                    text = searchedText.searchedText,
                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight(400)),
                    modifier = Modifier
                        .padding(start = 12.dp, end = 6.dp)
                )
                Text(
                    text = searchedText.searchTime,
                    style = MaterialTheme.typography.titleSmall.copy(
                        fontWeight = FontWeight(300),
                        color = MaterialTheme.colorScheme.outline
                    )
                )
                Spacer(modifier = Modifier.weight(1f))
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right_24),
                    contentDescription = "ic_arrow_right_outline_24",
                    tint = MaterialTheme.colorScheme.outline,
                )
            }
        }

    }
}

@Preview
@Composable
private fun PreViewHomeScreen() {
    NutriFindTheme {
        HomeScreen(
            searchText = "",
            isSearching = false,
            searchedHistories = MutableList(4) {
                SearchedHistory(id = it, searchedText = "Hot Dog", searchTime = "Today")
            },
            foodSuggestions = MutableList(4) {
                Food(
                    title = "Fresh Pasta With Buttered Tomatoes",
                    image = R.drawable.img_pasta,
                    calories = 400,
                    ingredient = 8
                )
            },
            saladList = MutableList(3) {
                Food(
                    title = "salad",
                    image = R.drawable.img_pasta,
                    calories = 400,
                    ingredient = 8
                )
            },
            pizzaList = MutableList(3) {
                Food(
                    title = "pizza",
                    image = R.drawable.img_pasta,
                    calories = 400,
                    ingredient = 8
                )
            },
            chineseList = MutableList(3) {
                Food(
                    title = "chinese",
                    image = R.drawable.img_pasta,
                    calories = 400,
                    ingredient = 8
                )
            },
            favoriteDishes = MutableList(4) {
                Food(
                    title = "Fresh Pasta With Buttered Tomatoes",
                    image = R.drawable.img_pasta,
                    calories = 400,
                    ingredient = 8
                )
            },
            onSearchTextChange = {},
            onToggleSearch = {},
            onClearSearchedHistoriesClick = {},
            onCategoryItemClick = {},
            onAllCategoriesClick = {},
            onMoreCategoriesButtonClick = {},
            onTopFoodClick = {},
            onFavoriteFoodClick = {},
            onFoodSuggestionClick = {}
        )
    }
}

/*
@Preview
@Composable
private fun PreviewHomeScreenContent() {
    NutriFindTheme {
        HomeScreenContent(
            onFoodCategoryItemClick = {},
            onViewAllFoodCategoryClick = {},
            onMoreFoodCategoryButtonClick = {},
            foodSuggestion = MutableList(4) {
                Food(
                    title = "Fresh Pasta With Buttered Tomatoes",
                    image = R.drawable.img_pasta,
                    calories = 400,
                    ingredient = 8
                )
            },
            saladList = MutableList(3) {
                Food(
                    title = "salad",
                    image = R.drawable.img_pasta,
                    calories = 400,
                    ingredient = 8
                )
            },
            pizzaList = MutableList(3) {
                Food(
                    title = "pizza",
                    image = R.drawable.img_pasta,
                    calories = 400,
                    ingredient = 8
                )
            },
            chineseList = MutableList(3) {
                Food(
                    title = "chinese",
                    image = R.drawable.img_pasta,
                    calories = 400,
                    ingredient = 8
                )
            },
            favoriteDishes = MutableList(4) {
                Food(
                    title = "Fresh Pasta With Buttered Tomatoes",
                    image = R.drawable.img_pasta,
                    calories = 400,
                    ingredient = 8
                )
            }
        )
    }
}*/
