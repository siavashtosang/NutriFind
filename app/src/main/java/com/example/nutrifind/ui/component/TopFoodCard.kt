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
import com.example.nutrifind.R
import com.example.nutrifind.data.offline.Food
import com.example.nutrifind.data.offline.topFoods
import com.example.nutrifind.ui.theme.NutriFindTheme
import kotlin.math.absoluteValue


@Composable
fun TopFoodCard(
    modifier: Modifier = Modifier,
    saladList: List<Food> = emptyList(),
    pizzaList: List<Food> = emptyList(),
    chineseList: List<Food> = emptyList(),
    onClick: (foodId: Int) -> Unit,
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
                    contentDescription = "",
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
                    text = topFoods[page].title,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            when (page) {
                0 -> {
                    saladList.forEach { food ->
                        HorizontalFoodCard(
                            title = food.title,
                            image = food.image,
                            calories = food.calories,
                            ingredients = food.ingredient,
                            onClick = { onClick(food.id) }
                        )
                    }
                }

                1 -> {
                    pizzaList.forEach { food ->
                        HorizontalFoodCard(
                            title = food.title,
                            image = food.image,
                            calories = food.calories,
                            ingredients = food.ingredient,
                            onClick = { onClick(food.id) }
                        )
                    }
                }

                2 -> {
                    chineseList.forEach { food ->
                        HorizontalFoodCard(
                            title = food.title,
                            image = food.image,
                            calories = food.calories,
                            ingredients = food.ingredient,
                            onClick = { onClick(food.id) }
                        )
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
                onClick = {}
            )
        }

    }
}