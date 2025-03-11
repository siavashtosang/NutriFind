package com.example.nutrifind.ui.features.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
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
import com.example.nutrifind.ui.theme.NutriFindTheme

@Composable
fun HomeScreenRote(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    searchText: String,
    isSearching: Boolean,
    searchedHistory: List<SearchedHistory>,
    onSearchTextChange: (String) -> Unit,
    onToggleSearch: () -> Unit,
    onClearButtonClick: () -> Unit,
    ) {
    
    val searchBarHeight = if (!isSearching) Modifier.height(44.dp) else Modifier
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
    ) { paddingValue ->

        Box(
            modifier = Modifier
                .padding(paddingValue),
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.3f),

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
                        .fillMaxHeight()
                        .padding(start = 16.dp, bottom = 33.dp),
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

            Column(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.size(276.dp))

                SearchBar(
                    modifier = Modifier
                        .then(searchBarHeight),
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
                        onClearButtonClick = onClearButtonClick
                    )
                }



            }
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
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
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
            searchedHistory = MutableList(4) {
                SearchedHistory(id = it, searchedText = "Hot Dog", searchTime = "Today")
            },
            onSearchTextChange = {},
            onToggleSearch = {},
            onClearButtonClick = {}
        )
    }
}