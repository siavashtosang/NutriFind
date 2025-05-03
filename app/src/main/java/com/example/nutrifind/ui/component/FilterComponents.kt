package com.example.nutrifind.ui.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nutrifind.R
import com.example.nutrifind.utils.TagFilterItem
import com.example.nutrifind.utils.cuisineTypes
import com.example.nutrifind.utils.dietsFilterList
import com.example.nutrifind.utils.dishTypesFilterList
import com.example.nutrifind.utils.mealTypesFilterList
import com.example.nutrifind.ui.theme.NutriFindTheme


@Composable
fun FiltersSheet(
    modifier: Modifier = Modifier,
    dietsFilterList: List<TagFilterItem>,
    dishTypesFilterList: List<TagFilterItem>,
    mealTypesFilterList: List<TagFilterItem>,
    selectedCuisineType: String,
    onDietFilterClick: (TagFilterItem) -> Unit,
    onDishFilterClick: (TagFilterItem) -> Unit,
    onMealFilterCLick: (TagFilterItem) -> Unit,
    onClearAllFilterClick: () -> Unit,
    onCloseFilterSheet: () -> Unit,
    onCuisineTypesClick: () -> Unit,
    onApplyFilterClick: () -> Unit,
) {


    Column(
        modifier = modifier
            .fillMaxSize()
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            IconButton(
                onClick = onCloseFilterSheet,
                modifier = Modifier.padding(start = 8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left_24),
                    contentDescription = "ic_arrow_left_24",
                    tint = MaterialTheme.colorScheme.outline
                )
            }

            IconButton(
                onClick = onClearAllFilterClick,
                modifier = Modifier.padding(end = 8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_xcross_16),
                    contentDescription = "ic_xcross_16",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(16.dp)
                )

            }

        }

        Text(
            text = stringResource(R.string.filters),
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.padding(start = 28.dp, bottom = 16.dp)
        )

        Text(
            text = stringResource(R.string.diets),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(start = 16.dp, bottom = 10.dp)
        )

        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier
                .height(68.dp),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp, alignment = Alignment.Start),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dietsFilterList) { filterItem ->
                FilterButton(
                    title = filterItem.title,
                    selected = filterItem.selected,
                    onClick = { onDietFilterClick(filterItem) }
                )
            }
        }

        Text(
            text = stringResource(R.string.dish_type),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(start = 16.dp, bottom = 10.dp, top = 26.dp)
        )

        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier
                .height(68.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(dishTypesFilterList) { filterItem ->
                FilterButton(
                    title = filterItem.title,
                    selected = filterItem.selected,
                    onClick = { onDishFilterClick(filterItem) }
                )
            }
        }

        Text(
            text = stringResource(R.string.meal_types),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.outline,
            modifier = Modifier.padding(start = 16.dp, bottom = 10.dp, top = 26.dp)
        )


        LazyHorizontalGrid(
            rows = GridCells.Fixed(2),
            modifier = Modifier
                .height(68.dp)
                .fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(mealTypesFilterList) { filterItem ->
                FilterButton(
                    title = filterItem.title,
                    selected = filterItem.selected,
                    onClick = { onMealFilterCLick(filterItem) }
                )
            }
        }

        Row(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 16.dp)
                .fillMaxWidth()
                .height(48.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = stringResource(R.string.cuisine_types),
                style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.outline,
            )

            Button(
                onClick = onCuisineTypesClick,
                modifier = Modifier
                    .height(48.dp)
                    .width(164.dp),
                shape = RoundedCornerShape(24.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    contentColor = MaterialTheme.colorScheme.onBackground
                ),
                border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
            ) {
                Text(
                    text = selectedCuisineType,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(end = 4.dp)
                )

                Icon(
                    painterResource(R.drawable.ic_arrow_bottom_8),
                    contentDescription = "ic_arrow_bottom_8",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.size(8.dp)
                )

            }
        }


        Spacer(modifier = Modifier.weight(1f))

        Button(
            onClick = onApplyFilterClick,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 16.dp)
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(24.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surface,
                contentColor = MaterialTheme.colorScheme.onBackground
            ),
            border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
        ) {
            Text(
                stringResource(R.string.filter),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.outline
            )
        }

        Spacer(modifier = Modifier.windowInsetsPadding(insets = WindowInsets.systemBars))
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CuisineTypesSheet(
    modifier: Modifier = Modifier,
    cuisineTypeList: List<TagFilterItem>,
    onCuisineTypeClick: (TagFilterItem) -> Unit
) {

    Column {
        LazyColumn(
            modifier = modifier,
        ) {

            stickyHeader {
                Text(
                    text = stringResource(R.string.select_cuisine),
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(start = 16.dp)
                )
            }

            items(cuisineTypeList) { cuisineType ->

                val color by animateColorAsState(targetValue = if (cuisineType.selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onBackground)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(56.dp)
                        .clickable { onCuisineTypeClick(cuisineType) },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = cuisineType.title,
                        style = MaterialTheme.typography.titleMedium,
                        color = color,
                        modifier = Modifier
                            .padding(start = 16.dp)
                    )

                    AnimatedVisibility(cuisineType.selected) {
                        Icon(
                            painterResource(R.drawable.ic_check_24),
                            contentDescription = "ic_check_24",
                            tint = color,
                            modifier = Modifier
                                .padding(end = 16.dp)
                                .size(24.dp)

                        )
                    }
                }
            }
        }
        Spacer(modifier = Modifier.windowInsetsPadding(insets = WindowInsets.systemBars))
    }
}


@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    title: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    val backgroundColor: Color by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface
    )

    val textColor: Color by animateColorAsState(
        if (selected) MaterialTheme.colorScheme.surface else Color.Unspecified
    )

    Row(
        modifier = modifier
            .height(30.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .background(color = backgroundColor),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AnimatedVisibility(selected) {
            Icon(
                painter = painterResource(R.drawable.ic_check_24),
                contentDescription = "ic_check_24",
                tint = MaterialTheme.colorScheme.surface,
                modifier = Modifier.padding(start = 6.dp, end = 4.dp)
            )
        }

        if (!selected) {
            Spacer(modifier = Modifier.width(10.dp))
        }

        Text(
            text = title,
            style = MaterialTheme.typography.bodyMedium,
            color = textColor,
            modifier = Modifier
                .padding(end = 10.dp)
        )
    }
}

@Composable
fun FilterButton(
    modifier: Modifier = Modifier,
    filterItems: Int? = null,
    onClick: () -> Unit
) {

    Button(
        onClick = onClick,
        modifier = modifier
            .height(40.dp),
        shape = RoundedCornerShape(24.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.onBackground
        ),
        border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.outline)
    ) {
        Box(
            modifier = Modifier
                .size(16.dp),
            contentAlignment = Alignment.TopEnd
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_filter_24),
                contentDescription = "ic_filter_24",
                tint = MaterialTheme.colorScheme.onBackground,
            )

            if (filterItems != null) {
                Image(
                    painter = painterResource(R.drawable.ic_ellipse_4),
                    contentDescription = "ic_ellipse_4"
                )
            }
        }

        if (filterItems != null) {

            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .clip(shape = RoundedCornerShape(9.dp))
                    .background(color = MaterialTheme.colorScheme.inversePrimary),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$filterItems",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(horizontal = 8.dp, vertical = 2.dp)
                )
            }

            Text(
                stringResource(R.string.filters),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground
            )

        } else {

            Text(
                stringResource(R.string.filter),
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

    }

}


@Preview(showBackground = true)
@Composable
private fun PreViewFilterButton() {
    NutriFindTheme {
        FilterButton(
            title = "Protein",
            selected = false,
            onClick = { }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewFilterScreen() {
    NutriFindTheme {
        FiltersSheet(
            dietsFilterList = dietsFilterList,
            dishTypesFilterList = dishTypesFilterList,
            mealTypesFilterList = mealTypesFilterList,
            onMealFilterCLick = {},
            onApplyFilterClick = {},
            onClearAllFilterClick = {},
            onDietFilterClick = {},
            onDishFilterClick = {},
            onCuisineTypesClick = {},
            selectedCuisineType = "American",
            onCloseFilterSheet = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewCuisineTypesSheet() {
    NutriFindTheme {
        CuisineTypesSheet(
            cuisineTypeList = cuisineTypes,
            onCuisineTypeClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewFilterButton() {
    NutriFindTheme {
        FilterButton(
            filterItems = 1,
            onClick = {}
        )
    }
}

