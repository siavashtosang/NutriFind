package com.example.nutrifind.ui.component


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.nutrifind.R
import com.example.nutrifind.ui.theme.NutriFindTheme
import com.example.nutrifind.ui.theme.outlineLight

@Composable
fun VerticalFoodCard(
    modifier: Modifier = Modifier,
    title: String,
    image: Int,
    calories: Int,
    ingredients: Int,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
            .size(width = 208.dp, height = 236.dp)
            .clip(shape = RoundedCornerShape(14.dp))
            .border(
                width = 0.5.dp,
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.outline
            )
            .background(color = MaterialTheme.colorScheme.surface)
            .clickable { onClick() },
    ) {
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .height(144.dp),
            contentScale = ContentScale.FillBounds,
            contentDescription = title,
            painter = painterResource(image),
        )

        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            textAlign = TextAlign.Start,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth()
                .height(40.dp)
        )

        Row(
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
                .height(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_calorie_16),
                contentDescription = "ic_calorie_16",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )

            Text(
                stringResource(R.string.calories, calories),
                style = MaterialTheme.typography.labelMedium
                    .copy(
                        color = MaterialTheme.colorScheme.outline,
                        fontWeight = FontWeight(weight = 400)
                    ),
                modifier = Modifier.padding(start = 2.dp)
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(R.drawable.ic_ingredient_16),
                contentDescription = "ic_ingredient_16",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )

            Text(
                stringResource(R.string.ingredient, ingredients),
                style = MaterialTheme.typography.labelMedium
                    .copy(
                        color = MaterialTheme.colorScheme.outline,
                        fontWeight = FontWeight(weight = 400)
                    ),
                modifier = Modifier.padding(start = 2.dp)
            )
        }
    }
}


@Composable
fun FoodCategoryCard(
    modifier: Modifier = Modifier,
    title: String,
    image: Int,
    onClick: () -> Unit,
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .border(
                    width = 0.5.dp,
                    color = MaterialTheme.colorScheme.outline,
                    shape = RoundedCornerShape(14.dp)
                )
                .clip(shape = RoundedCornerShape(14.dp))
                .background(color = MaterialTheme.colorScheme.surface)
                .clickable { onClick() },
            contentAlignment = Alignment.Center,
        ) {
            Image(
                painter = painterResource(image), contentDescription = title,
                modifier = Modifier
                    .aspectRatio(1f),
            )
        }

        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall.copy(MaterialTheme.colorScheme.outline)
        )
    }
}


@Composable
fun HorizontalFoodCard(
    modifier: Modifier = Modifier,
    title: String,
    image: Int,
    calories: Int,
    ingredients: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .padding(bottom = 12.dp)
            .height(80.dp)
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(shape = RoundedCornerShape(14.dp))
        ) {

            Image(
                painter = painterResource(image),
                contentDescription = title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(80.dp)
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 4.dp, bottom = 8.dp, end = 14.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = title, style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.height(40.dp),
                overflow = TextOverflow.Ellipsis
            )

            Row(
                modifier = Modifier
                    .padding(top = 14.dp)
                    .height(14.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(calories, R.string.calories),
                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline),
                )

                Icon(
                    painter = painterResource(R.drawable.ic_ellipse_3),
                    contentDescription = "ic_ellipse_3",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )


                Text(
                    text = stringResource(ingredients, R.string.ingredient),
                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline),
                )

            }

        }

    }
}


@Composable
fun MoreButtonCard(modifier: Modifier = Modifier, onClick: () -> Unit) {

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(shape = RoundedCornerShape(14.dp)),
            contentAlignment = Alignment.Center,
        ) {
            Box(
                modifier = Modifier
                    .size(52.dp)
                    .clip(CircleShape)
                    .clickable { onClick() },
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_arrow_right_24),
                    contentDescription = "ic_arrow_right_24",
                    tint = MaterialTheme.colorScheme.outline
                )
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    val canvasWidth = size.width
                    val canvasHeight = size.height
                    drawCircle(
                        color = outlineLight,
                        center = Offset(x = canvasWidth / 2, y = canvasHeight / 2),
                        radius = size.minDimension / 2,
                        style = Stroke(
                            width = 8f,
                            pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                        )
                    )
                }
            }
        }

        Text(
            text = stringResource(R.string.more),
            style = MaterialTheme.typography.titleSmall.copy(MaterialTheme.colorScheme.outline)
        )
    }
}


@Preview
@Composable
private fun PreviewVerticalFoodCard() {
    NutriFindTheme {
        VerticalFoodCard(
            title = "Fresh Pasta With Buttered Tomatoes",
            image = R.drawable.img_pasta,
            calories = 400,
            ingredients = 8,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewHorizontalFoodCard() {
    NutriFindTheme {
        HorizontalFoodCard(
            title = "Fresh Pasta With Buttered Tomatoes",
            image = R.drawable.img_pasta,
            calories = 400,
            ingredients = 8,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewCategoryCard() {
    NutriFindTheme {
        FoodCategoryCard(
            title = "pizza",
            image = R.drawable.img_pizza_64,
            onClick = {}
        )
    }
}

@Preview
@Composable
private fun PreviewMoreCategoryButton() {
    NutriFindTheme {
        MoreButtonCard(
            onClick = {}
        )
    }
}

