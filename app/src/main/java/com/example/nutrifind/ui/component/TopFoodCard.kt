package com.example.nutrifind.ui.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.util.lerp
import com.example.nutrifind.data.network.DataResponse
import com.example.nutrifind.data.offline.fakeFoodData
import com.example.nutrifind.data.offline.topFoods
import com.example.nutrifind.ui.theme.NutriFindTheme
import kotlin.math.absoluteValue
import kotlin.math.roundToInt


@Composable
fun TopFoodCard(
    modifier: Modifier = Modifier,
    saladList: DataResponse,
    pizzaList: DataResponse,
    chineseList: DataResponse,
    onClick: (String) -> Unit,
) {
    val pagerState = rememberPagerState(pageCount = { 3 })

    HorizontalPager(
        state = pagerState,
        modifier = modifier
            .background(color = MaterialTheme.colorScheme.surfaceVariant),
        contentPadding = PaddingValues(horizontal = 16.dp),
    ) { page: Int ->

        val pageOffset = (
                (pagerState.currentPage - page)
                        + pagerState.currentPageOffsetFraction
                ).absoluteValue

        Column(
            modifier = Modifier
                .graphicsLayer {
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }
        ) {
            Column(
                modifier = Modifier
                    .padding(bottom = 16.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Image(
                    painter = painterResource(topFoods[page].image),
                    contentDescription = topFoods[page].title,
                    modifier = Modifier
                        .size(width = 117.dp, height = 112.dp)
                        .graphicsLayer {
                            rotationZ = lerp(
                                start = 50f,
                                stop = 1f,
                                fraction = 1f - pageOffset.coerceIn(0f, 1f)
                            )
                        }
                )

                Text(
                    text = "Top in ${topFoods[page].title}",
                    style = MaterialTheme.typography.titleMedium
                )
            }

            when (page) {
                0 -> {
                    when (saladList) {
                        is DataResponse.Error -> {
                            Text("Error")
                        }

                        DataResponse.Loading -> {
                            Text("Loading")
                        }

                        is DataResponse.Success -> {
                            saladList.apiEdamam?.hits?.take(3)?.forEach { food ->
                                HorizontalFoodCard(
                                    modifier = Modifier.padding(bottom = 12.dp),
                                    title = food.recipe?.label ?: "",
                                    image = food.recipe?.images?.thumbnail?.url ?: "",
                                    calories = food.recipe?.calories?.roundToInt() ?: 0,
                                    ingredients = food.recipe?.ingredientLines?.size ?: 0,
                                    onClick = { onClick(food.recipe?.label!!) }
                                )

                            }
                        }
                    }
                }

                1 -> {
                    when (pizzaList) {

                        is DataResponse.Error -> {
                            Text("Error")
                        }

                        DataResponse.Loading -> {
                            Text("Loading")
                        }

                        is DataResponse.Success -> {
                            pizzaList.apiEdamam?.hits?.take(3)?.forEach { food ->
                                HorizontalFoodCard(
                                    modifier = Modifier.padding(bottom = 12.dp),
                                    title = food.recipe?.label ?: "",
                                    image = food.recipe?.images?.thumbnail?.url ?: "",
                                    calories = food.recipe?.calories?.roundToInt() ?: 0,
                                    ingredients = food.recipe?.ingredientLines?.size ?: 0,
                                    onClick = { onClick(food.recipe?.label!!) }
                                )
                            }
                        }
                    }
                }

                2 -> {
                    when (chineseList) {

                        is DataResponse.Error -> {
                            Text("Error")
                        }

                        DataResponse.Loading -> {
                            Text("Loading")
                        }

                        is DataResponse.Success -> {
                            chineseList.apiEdamam?.hits?.take(3)?.forEach { food ->

                                HorizontalFoodCard(
                                    modifier = Modifier.padding(bottom = 12.dp),
                                    title = food.recipe?.label ?: "",
                                    image = food.recipe?.images?.thumbnail?.url ?: "",
                                    calories = food.recipe?.calories?.roundToInt() ?: 0,
                                    ingredients = food.recipe?.ingredientLines?.size ?: 0,
                                    onClick = { onClick(food.recipe?.label!!) }
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun PreviewTopFoodCard() {
    MaterialTheme {
        NutriFindTheme {
            TopFoodCard(
                saladList = DataResponse.Success(fakeFoodData),
                pizzaList = DataResponse.Success(fakeFoodData),
                chineseList = DataResponse.Success(fakeFoodData),
                onClick = {}
            )
        }
    }
}