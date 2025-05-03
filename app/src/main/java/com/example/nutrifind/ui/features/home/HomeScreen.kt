package com.example.nutrifind.ui.features.home

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDrawerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.nutrifind.R
import com.example.nutrifind.data.network.DataResponse
import com.example.nutrifind.ui.component.CuisineTypesSheet
import com.example.nutrifind.ui.component.FiltersSheet
import com.example.nutrifind.ui.component.FoodCategoryCard
import com.example.nutrifind.ui.component.MoreButtonCard
import com.example.nutrifind.ui.component.NutriFindErrorScreen
import com.example.nutrifind.ui.component.NutriFindLoadingScreen
import com.example.nutrifind.ui.component.SearchResults
import com.example.nutrifind.ui.component.SearchedHistoryList
import com.example.nutrifind.ui.component.TopFoodCard
import com.example.nutrifind.ui.component.VerticalFoodCard
import com.example.nutrifind.ui.theme.NutriFindTheme
import com.example.nutrifind.utils.Food
import com.example.nutrifind.utils.FoodCategoryItem
import com.example.nutrifind.utils.TagFilterItem
import com.example.nutrifind.utils.foodCategoryItems
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeScreenRote(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
    onCategoryItemClick: (String) -> Unit,
    onFoodCardClick: (String) -> Unit,
    onAllCategoriesClick: () -> Unit,
    onMoreCategoriesButtonClick: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    when (uiState.results) {
        is DataResponse.Error -> {
            NutriFindErrorScreen(
                onRetry = viewModel::onRetryHome
            )
        }

        DataResponse.Loading -> {
            NutriFindLoadingScreen()
        }

        is DataResponse.Success -> {
            HomeScreen(
                modifier = modifier,
                isDarkTheme = uiState.isDarkTheme,
                searchText = uiState.searchText,
                foodsSuggestionTitle = uiState.foodSuggestionTitle,
                isActiveSearchBar = uiState.isActiveSearchBar,
                searchedHistories = uiState.searchHistory,
                foodsSuggestion = uiState.foodsSuggestion,
                searchedResultsState = uiState.searchedResultsState,
                searchedResults = uiState.searchedResults,
                saladList = uiState.salads,
                pizzaList = uiState.pizzas,
                chineseList = uiState.chinese,
                dietsFilterList = uiState.dietsFilters,
                dishTypesFilterList = uiState.dishTypesFilters,
                mealTypesFilterList = uiState.mealTypesFilters,
                cuisineTypeList = uiState.cuisineTypeFilters,
                selectedCuisineType = uiState.selectedCuisineType,
                filterItems = uiState.filterItems,
                onDietFilterClick = viewModel::onDietFilterClick,
                onDishFilterClick = viewModel::onDishFilterClick,
                onMealFilterCLick = viewModel::onMealFilterCLick,
                onCuisineTypeClick = viewModel::onCuisineTypeFilterClick,
                onClearAllFilterClick = viewModel::onClearAllFilterClick,
                onApplyFilterClick = viewModel::onApplyFilterClick,
                onSearchTextChange = viewModel::onSearchTextChange,
                onActiveSearchBarChange = viewModel::onActiveSearchBarChange,
                onSearchTriggered = viewModel::onSearchTriggered,
                onClearSearchedHistoriesClick = viewModel::onClearSearchedHistoriesClick,
                onDarkThemeChange = viewModel::onDarkThemeChange,
                onCategoryItemClick = onCategoryItemClick,
                onAllCategoriesClick = onAllCategoriesClick,
                onMoreCategoriesButtonClick = onMoreCategoriesButtonClick,
                onFoodCardClick = onFoodCardClick,
                onReUseSearchedHistory = viewModel::onReUseSearchedHistory,
                onSearchRetry = viewModel::onSearchTriggered
            )

        }
    }
}

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    searchText: String,
    isDarkTheme: Boolean,
    foodsSuggestionTitle: String,
    selectedCuisineType: String,
    isActiveSearchBar: Boolean,
    filterItems: Int?,
    searchedHistories: List<SearchedHistory>,
    foodsSuggestion: List<Food>,
    searchedResultsState: DataResponse,
    searchedResults: List<Food>,
    saladList: List<Food>,
    pizzaList: List<Food>,
    chineseList: List<Food>,
    dietsFilterList: List<TagFilterItem>,
    dishTypesFilterList: List<TagFilterItem>,
    mealTypesFilterList: List<TagFilterItem>,
    cuisineTypeList: List<TagFilterItem>,
    onDietFilterClick: (TagFilterItem) -> Unit,
    onDishFilterClick: (TagFilterItem) -> Unit,
    onMealFilterCLick: (TagFilterItem) -> Unit,
    onCuisineTypeClick: (TagFilterItem) -> Unit,
    onApplyFilterClick: () -> Unit,
    onClearAllFilterClick: () -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    onReUseSearchedHistory: (String) -> Unit,
    onFoodCardClick: (String) -> Unit,
    onActiveSearchBarChange: (Boolean) -> Unit,
    onClearSearchedHistoriesClick: () -> Unit,
    onCategoryItemClick: (categoryTitle: String) -> Unit,
    onDarkThemeChange: (Boolean) -> Unit,
    onAllCategoriesClick: () -> Unit,
    onMoreCategoriesButtonClick: () -> Unit,
    onSearchRetry: () -> Unit,
) {

    val lazyListState = rememberLazyListState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val scrollOffset by remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }
    val visibilityItem by remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }


    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Text(
                    text = stringResource(R.string.appearance),
                    style = MaterialTheme.typography.titleLarge,
                    modifier = Modifier
                        .padding(
                            start = 16.dp, top = 36.dp, bottom = 8.dp
                        )
                )

                HorizontalDivider()

                Row(
                    modifier = Modifier
                        .padding(top = 4.dp, start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        stringResource(R.string.dark_mode)
                    )

                    Checkbox(
                        checked = isDarkTheme,
                        onCheckedChange = onDarkThemeChange,
                        modifier = Modifier.padding(horizontal = 2.dp)
                    )
                }
            }
        }
    ) {
        Scaffold(
            modifier = modifier,
            containerColor = MaterialTheme.colorScheme.background,
        ) { paddingValues ->


            BoxWithConstraints(
                modifier = modifier
                    .padding(paddingValues)
                    .fillMaxSize(),
                contentAlignment = Alignment.TopStart
            ) {
                val searchBarPadding = if (isActiveSearchBar) 0f else (maxHeight.value / 3f) * 0.89f
                val bannerHeight = (maxHeight.value / 3f)
                val contentPadding = (maxHeight.value / 3f) * 1.1f

                val topBarAlpha: Float by animateFloatAsState(
                    targetValue = if (visibilityItem == 0) (scrollOffset / bannerHeight).coerceIn(
                        0f,
                        1f
                    ) else 1f
                )


                val bannerAlpha: Float by animateFloatAsState(
                    targetValue = if (visibilityItem == 0) (1f - scrollOffset / bannerHeight).coerceIn(
                        0f,
                        1f
                    ) else 0f
                )

                TopScreenBanner(
                    modifier = Modifier
                        .height(bannerHeight.dp)
                        .graphicsLayer { this.alpha = bannerAlpha }
                )


                LazyColumn(
                    state = lazyListState
                ) {

                    item {
                        Spacer(
                            modifier = Modifier
                                .height(contentPadding.dp)
                        )
                    }

                    item {
                        HomeScreenContent(
                            modifier = Modifier,
                            foodsSuggestionTitle = foodsSuggestionTitle,
                            onCategoryItemClick = onCategoryItemClick,
                            onAllCategoriesClick = onAllCategoriesClick,
                            onMoreCategoriesButtonClick = onMoreCategoriesButtonClick,
                            foodsSuggestion = foodsSuggestion,
                            saladList = saladList,
                            pizzaList = pizzaList,
                            chineseList = chineseList,
                            onFoodCardClick = onFoodCardClick,
                        )
                    }
                }

                AnimatedVisibility(
                    visible = scrollOffset < 10,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    HomeScreenSearchBar(
                        modifier = Modifier
                            .padding(top = searchBarPadding.dp)
                            .graphicsLayer {
                                this.translationY -= scrollOffset / 3
                            },
                        searchText = searchText,
                        isActiveSearchBar = isActiveSearchBar,
                        searchedHistory = searchedHistories,
                        filterItems = filterItems,
                        searchedResultsState = searchedResultsState,
                        searchResults = searchedResults,
                        onSearchTextChange = onSearchTextChange,
                        onSearchTriggered = onSearchTriggered,
                        onFoodCardClick = onFoodCardClick,
                        onActiveSearchBarChange = onActiveSearchBarChange,
                        onClearSearchedHistoriesClick = onClearSearchedHistoriesClick,
                        onReUseSearchedHistory = onReUseSearchedHistory,
                        selectedCuisineType = selectedCuisineType,
                        dietsFilterList = dietsFilterList,
                        dishTypesFilterList = dishTypesFilterList,
                        mealTypesFilterList = mealTypesFilterList,
                        cuisineTypeList = cuisineTypeList,
                        onDietFilterClick = onDietFilterClick,
                        onDishFilterClick = onDishFilterClick,
                        onMealFilterCLick = onMealFilterCLick,
                        onCuisineTypeClick = onCuisineTypeClick,
                        onApplyFilterClick = onApplyFilterClick,
                        onClearAllFilterClick = onClearAllFilterClick,
                        onSearchRetry = onSearchRetry
                    )
                }

                if (!isActiveSearchBar)
                    Row(
                        modifier = modifier
                            .height(76.dp)
                            .fillMaxWidth()
                            .background(color = MaterialTheme.colorScheme.background.copy(alpha = topBarAlpha)),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .size(44.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 1.dp,
                                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.4f),
                                    shape = CircleShape
                                )
                                .background(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f))
                                .clickable {
                                    scope.launch {
                                        drawerState.apply {
                                            if (isClosed) open() else close()
                                        }
                                    }
                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = (Icons.Filled.Menu),
                                contentDescription = stringResource(R.string.go_back),
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                    }
            }
        }
    }
}

@Composable
private fun TopScreenBanner(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
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
                .padding(start = 16.dp, bottom = 32.dp)
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
    selectedCuisineType: String,
    filterItems: Int?,
    searchedResultsState: DataResponse,
    searchResults: List<Food>,
    searchedHistory: List<SearchedHistory>,
    dietsFilterList: List<TagFilterItem>,
    dishTypesFilterList: List<TagFilterItem>,
    mealTypesFilterList: List<TagFilterItem>,
    cuisineTypeList: List<TagFilterItem>,
    onDietFilterClick: (TagFilterItem) -> Unit,
    onDishFilterClick: (TagFilterItem) -> Unit,
    onMealFilterCLick: (TagFilterItem) -> Unit,
    onCuisineTypeClick: (TagFilterItem) -> Unit,
    onApplyFilterClick: () -> Unit,
    onClearAllFilterClick: () -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearchTriggered: () -> Unit,
    onActiveSearchBarChange: (Boolean) -> Unit,
    onFoodCardClick: (String) -> Unit,
    onClearSearchedHistoriesClick: () -> Unit,
    onReUseSearchedHistory: (String) -> Unit,
    onSearchRetry: () -> Unit,
) {

    var showSearchResult by remember { mutableStateOf(false) }
    var showFilterSheet by remember { mutableStateOf(false) }
    var showCuisineTypeSheet by remember { mutableStateOf(false) }

    val filterSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val cuisineSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

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
                        onClearAllFilterClick()
                        onActiveSearchBarChange(false)
                        showSearchResult = false
                    }
                )
            }

        ) {
            if (isActiveSearchBar) {
                if (showSearchResult) {

                    when (searchedResultsState) {
                        is DataResponse.Error -> {
                            NutriFindErrorScreen(
                                message = searchedResultsState.message,
                                onRetry = onSearchRetry
                            )
                        }

                        DataResponse.Loading -> {
                            NutriFindLoadingScreen()
                        }

                        is DataResponse.Success -> {
                            Box {
                                SearchResults(
                                    searchedFood = searchText,
                                    searchResults = searchResults,
                                    filterItems = filterItems,
                                    onSearchedFoodCardClick = onFoodCardClick,
                                    onFilterButtonClick = {
                                        scope.launch {
                                            showFilterSheet = true
                                        }
                                    }
                                )

                                if (showFilterSheet) {
                                    ModalBottomSheet(
                                        onDismissRequest = { showFilterSheet = false },
                                        sheetState = filterSheetState
                                    ) {
                                        FiltersSheet(
                                            dietsFilterList = dietsFilterList,
                                            dishTypesFilterList = dishTypesFilterList,
                                            mealTypesFilterList = mealTypesFilterList,
                                            selectedCuisineType = selectedCuisineType,
                                            onDietFilterClick = onDietFilterClick,
                                            onDishFilterClick = onDishFilterClick,
                                            onMealFilterCLick = onMealFilterCLick,
                                            onApplyFilterClick = {
                                                scope.launch {
                                                    onApplyFilterClick()
                                                    filterSheetState.hide()
                                                }.invokeOnCompletion {
                                                    if (!filterSheetState.isVisible) {
                                                        showFilterSheet = false
                                                    }
                                                }
                                            },
                                            onClearAllFilterClick = onClearAllFilterClick,
                                            onCuisineTypesClick = {
                                                showCuisineTypeSheet = true
                                            },
                                            onCloseFilterSheet = {
                                                scope.launch {
                                                    filterSheetState.hide()
                                                }.invokeOnCompletion {
                                                    if (!filterSheetState.isVisible) {
                                                        showFilterSheet = false
                                                    }
                                                }
                                            }
                                        )
                                    }
                                }

                                if (showCuisineTypeSheet) {
                                    ModalBottomSheet(
                                        onDismissRequest = { showCuisineTypeSheet = false },
                                        sheetState = cuisineSheetState
                                    ) {
                                        CuisineTypesSheet(
                                            cuisineTypeList = cuisineTypeList,
                                            onCuisineTypeClick = {
                                                scope.launch {
                                                    onCuisineTypeClick(it)
                                                    delay(500)
                                                    cuisineSheetState.hide()
                                                }.invokeOnCompletion {
                                                    if (!cuisineSheetState.isVisible) {
                                                        showCuisineTypeSheet = false
                                                    }
                                                }
                                            }
                                        )
                                    }
                                }
                            }
                        }
                    }


                } else {
                    SearchedHistoryList(
                        searchedHistory = searchedHistory,
                        onClearButtonClick = onClearSearchedHistoriesClick,
                        onReUseSearchedHistory = {
                            onReUseSearchedHistory(it)
                            showSearchResult = true
                        }
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
    foodsSuggestion: List<Food>,
    saladList: List<Food>,
    pizzaList: List<Food>,
    chineseList: List<Food>,
    onAllCategoriesClick: () -> Unit,
    onMoreCategoriesButtonClick: () -> Unit,
    onCategoryItemClick: (categoryTitle: String) -> Unit,
    onFoodCardClick: (String) -> Unit,
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

            items(foodsSuggestion.take(5)) { food: Food ->
                VerticalFoodCard(
                    title = food.name,
                    image = food.image,
                    calories = food.calories,
                    ingredients = food.ingredients.size,
                    onClick = { onFoodCardClick(food.name) },
                )
            }
        }

        TopFoodCard(
            modifier = Modifier
                .padding(top = 55.dp, bottom = 16.dp),
            saladList = saladList,
            pizzaList = pizzaList,
            chineseList = chineseList,
            onClick = onFoodCardClick
        )
    }
}


@Preview
@Composable
private fun PreViewHomeScreen() {
    NutriFindTheme {
        HomeScreen(
            modifier = Modifier,
            isDarkTheme = false,
            searchText = "",
            foodsSuggestionTitle = "random food name",
            isActiveSearchBar = false,
            searchedHistories = MutableList(4) {
                SearchedHistory(searchedText = "Hot Dog", searchTime = "Today")
            },
            foodsSuggestion = emptyList(),
            searchedResultsState = DataResponse.Loading,
            searchedResults = MutableList(3){
                Food()
            },
            saladList = emptyList(),
            pizzaList = emptyList(),
            chineseList = emptyList(),
            dietsFilterList = emptyList(),
            dishTypesFilterList = emptyList(),
            mealTypesFilterList = emptyList(),
            cuisineTypeList = emptyList(),
            selectedCuisineType = "",
            filterItems = null,
            onSearchTextChange = {},
            onActiveSearchBarChange = {},
            onClearSearchedHistoriesClick = {},
            onCategoryItemClick = {},
            onAllCategoriesClick = {},
            onMoreCategoriesButtonClick = {},
            onSearchTriggered = {},
            onReUseSearchedHistory = {},
            onFoodCardClick = {},
            onDishFilterClick = {},
            onMealFilterCLick = {},
            onApplyFilterClick = {},
            onDietFilterClick = {},
            onClearAllFilterClick = {},
            onCuisineTypeClick = {},
            onDarkThemeChange = {},
            onSearchRetry = {}
        )
    }
}