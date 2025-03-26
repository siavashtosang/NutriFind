package com.example.nutrifind.data.offline

import com.example.nutrifind.R


val foodNames: List<String> =
    listOf(
        "Pasta",
        "Spaghetti",
        "Breakfast",
        "Burger",
        "Pizza",
        "Salads",
        "Drinks",
        "Snacks",
        "Sea food",
        "Sandwiches",
        "Ice Cream",
        "Pie",
        "Steak",
        "Fried chicken",
        "Smoothie",
        "Cookie",
        "Fried chicken",
        "Lasagna",
        "Omelet",
        "Muffin"
    )

val topFoods = listOf<TopFood>(
    TopFood(title = "Salad", image = R.drawable.img_salad_120),
    TopFood(title = "Pizza", image = R.drawable.img_pizza_120),
    TopFood(title = "Chinese", image = R.drawable.img_chinese_120),
)

data class Food(
    val id: Int = -1,
    val image: Int = R.drawable.img_pasta,
    val title: String = "",
    val calories: Int = 0,
    val ingredient: Int = 0,
)

data class TopFood(
    val title: String = "",
    val image: Int = R.drawable.img_pasta,
)