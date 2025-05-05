package com.example.nutrifind.ui.features.food_details

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.example.nutrifind.R
import com.example.nutrifind.data.local.Food
import com.example.nutrifind.data.remote.model.Ingredients
import com.example.nutrifind.data.remote.network.DataResponse
import com.example.nutrifind.ui.component.NutriFindErrorScreen
import com.example.nutrifind.ui.component.NutriFindLoadingScreen
import com.example.nutrifind.ui.theme.NutriFindTheme
import kotlinx.coroutines.launch

@Composable
fun FoodDetailsScreenRout(
    modifier: Modifier = Modifier,
    viewModel: FoodDetailsViewModel = hiltViewModel(),
    onBackButtonClick: () -> Unit
) {

    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val intent = Intent(Intent.ACTION_VIEW)
    intent.data = Uri.parse(uiState.food.recipeUrl)


    when (uiState.results) {
        is DataResponse.Error -> {
            NutriFindErrorScreen(
                message = (uiState.results as DataResponse.Error).message,
                onRetry = viewModel::onRetry
            )
        }

        DataResponse.Loading -> {
            NutriFindLoadingScreen()
        }

        is DataResponse.Success -> {
            FoodDetailsScreen(
                modifier = modifier,
                food = uiState.food,
                onFoodRecipeClick = {
                    scope.launch {
                        context.startActivity(intent)
                    }
                },
                onBackButtonClick = onBackButtonClick
            )
        }
    }
}

@Composable
fun FoodDetailsScreen(
    modifier: Modifier = Modifier,
    food: Food,
    onFoodRecipeClick: () -> Unit,
    onBackButtonClick: () -> Unit,
) {
    Scaffold(
        modifier = modifier
    ) { paddingValue ->

        val lazyListState = rememberLazyListState()

        val scrollY by remember { derivedStateOf { lazyListState.firstVisibleItemScrollOffset } }
        val visibilityItem by remember { derivedStateOf { lazyListState.firstVisibleItemIndex } }
        var alpha by remember { mutableFloatStateOf(0f) }

        alpha = if (visibilityItem == 0) {

            ((0f + scrollY) / 200).coerceIn(0f, 1f)

        } else {
            1f
        }


        Box(
            modifier = Modifier
                .padding(paddingValue)
                .fillMaxSize()
        ) {
            LazyColumn(
                state = lazyListState,
            ) {
                item {
                    FoodDetailBanner(
                        foodName = food.name,
                        image = food.image
                    )
                }

                item {
                    Column {

                        Row(
                            modifier = Modifier
                                .padding(top = 32.dp)
                                .fillMaxWidth()
                                .height(58.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(end = 63.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = food.calories.toString(),
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight(
                                            712
                                        )
                                    )
                                )

                                Text(
                                    text = stringResource(R.string.calories),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }

                            Column(
                                modifier = Modifier
                                    .padding(start = 63.dp),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text(
                                    text = food.serving.toString(),
                                    style = MaterialTheme.typography.titleLarge.copy(
                                        fontWeight = FontWeight(
                                            712
                                        )
                                    )
                                )

                                Text(
                                    text = stringResource(R.string.serving),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.outline
                                )
                            }
                        }

                        Text(
                            text = stringResource(R.string.ingredients),
                            style = MaterialTheme.typography.titleMedium,
                            modifier = Modifier
                                .padding(start = 16.dp, top = 32.dp, bottom = 16.dp)
                        )
                    }
                }

                items(food.ingredients) { ingredient ->

                    Row(
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .fillMaxWidth()
                            .height(56.dp),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AsyncImage(
                            model = ingredient.image,
                            contentDescription = ingredient.text,
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .size(48.dp)
                                .clip(CircleShape)
                                .border(
                                    width = 2.dp,
                                    color = MaterialTheme.colorScheme.surfaceVariant,
                                    shape = CircleShape
                                )
                        )

                        Column(
                            verticalArrangement = Arrangement.spacedBy(6.dp)
                        ) {

                            Text(
                                text = ingredient.food.toString(),
                                style = MaterialTheme.typography.titleMedium,
                            )

                            Text(
                                text = ingredient.text.toString(),
                                style = MaterialTheme.typography.bodyMedium,
                                overflow = TextOverflow.Ellipsis
                            )
                        }
                    }
                }

                item {

                    Column {

                        Button(
                            onClick = onFoodRecipeClick,
                            modifier = Modifier
                                .padding(8.dp)
                                .fillMaxWidth()
                                .height(56.dp),
                            shape = RoundedCornerShape(16.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = MaterialTheme.colorScheme.primary
                            )
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.ic_file_24),
                                contentDescription = "ic_file_24",
                                modifier = Modifier
                                    .padding(horizontal = 12.dp)
                            )

                            Text(
                                text = stringResource(R.string.food_recipe),
                                style = MaterialTheme.typography.bodyMedium
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Icon(
                                painter = painterResource(R.drawable.ic_arrow_right_16),
                                contentDescription = "ic_arrow_right_16",
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                                    .size(24.dp)
                            )
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = stringResource(R.string.nutrition),
                                style = MaterialTheme.typography.titleMedium,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                            )

                            Spacer(modifier = Modifier.weight(1f))

                            Text(
                                text = stringResource(R.string.amount),
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier
                                    .padding(horizontal = 16.dp)
                            )
                        }
                    }
                }

                items(food.nutrition) { nutrition ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(64.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = nutrition.name,
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        )

                        Spacer(modifier = Modifier.weight(1f))

                        Text(
                            text = "${nutrition.value} ${nutrition.unit}",
                            style = MaterialTheme.typography.titleSmall,
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                        )
                    }
                }
            }

            FoodDetailsTopBar(
                title = food.name,
                alpha = alpha,
                onBackButtonClick = onBackButtonClick
            )
        }
    }
}


@Composable
private fun FoodDetailBanner(
    modifier: Modifier = Modifier,
    foodName: String,
    image: String,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(360.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        AsyncImage(
            model = image,
            contentDescription = foodName,
            modifier = Modifier
                .fillMaxSize(),
            contentScale = ContentScale.FillBounds
        )

        Text(
            foodName,
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 22.dp)
        )
    }
}

@Composable
private fun FoodDetailsTopBar(
    modifier: Modifier = Modifier,
    title: String,
    alpha: Float,
    onBackButtonClick: () -> Unit
) {

    Box(
        modifier = modifier
            .height(76.dp)
            .fillMaxWidth(),
    ) {
        Box(modifier = modifier
            .fillMaxSize()
            .graphicsLayer { this.alpha = alpha }
            .background(color = MaterialTheme.colorScheme.background)

        )

        Row(
            modifier = Modifier
                .fillMaxSize(),
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
                    .clickable { onBackButtonClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_left_24),
                    contentDescription = stringResource(R.string.go_back),
                    tint = MaterialTheme.colorScheme.surface
                )
            }

            AnimatedVisibility(alpha >= 0.5f) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.labelMedium,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreViewFoodDetailsScreen() {
    NutriFindTheme {
        FoodDetailsScreen(
            food = Food(
                name = "Pasta alla Gracia Recipe",
                image = "",
                calories = 50,
                ingredients = MutableList(4) {
                    Ingredients(
                        text = "salt",
                        measure = "g",
                        quantity = 0.5
                    )
                },
                nutrition = emptyList(),
            ),
            onFoodRecipeClick = {},
            onBackButtonClick = {}
        )
    }
}
