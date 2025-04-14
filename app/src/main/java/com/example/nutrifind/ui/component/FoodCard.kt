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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
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
import coil.compose.AsyncImage
import com.example.nutrifind.R
import com.example.nutrifind.ui.theme.NutriFindTheme
import com.example.nutrifind.ui.theme.outlineLight

@Composable
fun VerticalFoodCard(
    modifier: Modifier = Modifier,
    title: String,
    image: String,
    calories: Int,
    ingredients: Int,
    onClick: () -> Unit
) {

    Column(
        modifier = modifier
            .size(width = 215.dp, height = 240.dp)
            .clip(shape = RoundedCornerShape(14.dp))
            .border(
                width = 0.5.dp,
                shape = RoundedCornerShape(14.dp),
                color = MaterialTheme.colorScheme.outline
            )
            .background(color = MaterialTheme.colorScheme.surface)
            .clickable { onClick() },
    ) {
        AsyncImage(
            model = image,
            contentDescription = title,
            modifier = Modifier
                .fillMaxWidth()
                .height(144.dp),
            alignment = Alignment.Center,
            contentScale = ContentScale.FillBounds
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
                text = stringResource(R.string.calories, calories),
                style = MaterialTheme.typography.labelSmall
                    .copy(
                        color = MaterialTheme.colorScheme.outline,
                        fontWeight = FontWeight(weight = 400)
                    ),
                modifier = Modifier
                    .padding(start = 2.dp)
                    .width(76.dp),
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.weight(1f))

            Icon(
                painter = painterResource(R.drawable.ic_ingredient_16),
                contentDescription = "ic_ingredient_16",
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(16.dp)
            )

            Text(
                text = stringResource(R.string.ingredients, ingredients),
                style = MaterialTheme.typography.labelSmall
                    .copy(
                        color = MaterialTheme.colorScheme.outline,
                        fontWeight = FontWeight(weight = 400)
                    ),
                modifier = Modifier
                    .padding(start = 2.dp)
                    .width(76.dp),
                overflow = TextOverflow.Ellipsis
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
    image: String,
    calories: Int,
    ingredients: Int,
    onClick: () -> Unit,
) {
    Row(
        modifier = modifier
            .height(80.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(14.dp))
            .clickable { onClick() },
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(80.dp)
                .clip(shape = RoundedCornerShape(14.dp))
        ) {

            AsyncImage(
                model = image,
                contentDescription = title,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .size(80.dp)
            )
        }

        Column(
            modifier = Modifier
                .padding(top = 4.dp, end = 14.dp),
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
                    .height(16.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = stringResource(calories, R.string.calories),
                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline),
                    textAlign = TextAlign.Center
                )

                Icon(
                    painter = painterResource(R.drawable.ic_ellipse_3),
                    contentDescription = "ic_ellipse_3",
                    tint = MaterialTheme.colorScheme.outline,
                    modifier = Modifier.padding(horizontal = 6.dp)
                )


                Text(
                    text = stringResource(R.string.ingredient, ingredients),
                    style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.outline),
                    textAlign = TextAlign.Center
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
            image = "https://edamam-product-images.s3.amazonaws.com/web-img/bb5/bb5bad0cbcb94ad2ef0895d444f30291.jpg?X-Amz-Security-Token=IQoJb3JpZ2luX2VjEBgaCXVzLWVhc3QtMSJHMEUCIQDbdebv5mL8hldZVomrKpJZ0zgBcnuf83UMwxU6mIEQsAIgPoRVCFpf1PZ4%2FKNY6GeLrBa6sL9EPFVWIzN6eJ5CqPwqzAQIcRAAGgwxODcwMTcxNTA5ODYiDCeHqtumAv4IYeqmwyqpBMnv1qb%2FAb0DWVI%2FJY1us4oiPItd0%2B%2FqkfF1Epaub31q7norr%2B8uklZsre0L0iJijmlcdVaHRnTX7lBCZLy9bmullakA94617dr9sQm298nwdnymWDjLjzyAMn6TM83W%2FedVHjof5cINRGNfvJOY11sbc4JF8Pt3r1jH9ZQkWe2MKal0iRXTTQhnH2iEh5YZEy4kmdpaMBqlFX8B0tcLN%2F6fiILVzGrUPHFF99skddQwm%2BsgAZ1HNqqGWtOqA0YYdXDvauy4q6wbc2C2j21%2B1vKyNNDgwOjQPp5pz2pLuYccLPuiM8rzeh2EkK6BHzOTh8Mpwk2CbSg6i3Q4bv8D6TfL2DOK4lyh%2B3sWN%2F1yvujFtXXBLk69hivtFWvofRQMNBr7wdDODU%2FIfkbHGQ%2F%2BaFxrtoR8mxuxVRsMc9jQRJJfPH9S3BvDSqH%2F0iD7Jh6DQ4hyDNSfuhKNdyQxbiQYpt%2FrWeDlDlxtIdllGoKObCu55QbYh4ZHQIsM6XtSgRtWJmYrX5AiYtJe5WWo9Tw6vvvrQOyKopkN7%2F8hpG4OebzxnaLVz3r9lnjoqVfQXuNYKV17rvrg8ILZm0uFW2dd%2BPj1BNckCnzdsX9xOcPLSvpFAnsUrV8BLQh%2FH%2BbZcj7zhLXuqDIGZmwlEpltYHTSHG1oJ5X2lJI%2BsiHV4A9TAYRqbKo9%2FtNvLPHCEoHRJLyNQRo0RiWb8uMsqdxJC0hK7Q2rLBwJe93DKOYw75DvnQY6qQH5hiBp0ofAmzUg0K9MVkb%2FGOObJZzCE%2Bd4iTSE6AD1jpy%2B%2BOvkKOlt2e0tv7QWaiQCa%2B4kRlOjQAWNfPe9uxdMf5Jy5533MKJGHan8ZNDf60jUMkUHxTV3kNZytQTZ614O%2FGEVndsKpqjce4Oy12gDqJcMy%2FOdgPw%2B%2B5fT7PA%2BghB4JcC%2FLSSlmc%2BHkQT4A9qGcrRz1mVqGTxwoGE3YNnp3ckG2LDQ9WRn&X-Amz-Algorithm=AWS4-HMAC-SHA256&X-Amz-Date=20230109T084759Z&X-Amz-SignedHeaders=host&X-Amz-Expires=3600&X-Amz-Credential=ASIASXCYXIIFGRAHKGNA%2F20230109%2Fus-east-1%2Fs3%2Faws4_request&X-Amz-Signature=8a9da1b51f0018a5d8eefab81ee6d669ea3a3cf47af1530c57d862fcdbe29be3",
            calories = 300000,
            ingredients = 88,
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
            image = "",
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

