package com.example.nutrifind.utils

import com.example.nutrifind.R

data class FoodCategoryItem(
    val id: Int = -1,
    val image: Int = R.drawable.img_pizza_64,
    val title: String = ""
)

val foodCategoryItems: List<FoodCategoryItem> = listOf(
    FoodCategoryItem(id = 0, image = R.drawable.img_pizza_64, title = "Pizza"),
    FoodCategoryItem(id = 1, image = R.drawable.img_burger_64, title = "Burger"),
    FoodCategoryItem(id = 2, image = R.drawable.img_salad_64, title = "Salad"),
    FoodCategoryItem(id = 3, image = R.drawable.img_drink_64, title = "Drinks"),
    FoodCategoryItem(id = 4, image = R.drawable.img_sandwich_64, title = "Sandwich"),
    FoodCategoryItem(id = 5, image = R.drawable.img_pasta_64, title = "Pasta"),
    FoodCategoryItem(id = 6, image = R.drawable.img_cake_64, title = "Cake"),
    FoodCategoryItem(id = 7, image = R.drawable.img_burrito_64, title = "Burrito"),
    FoodCategoryItem(id = 8, image = R.drawable.img_chinese_64, title = "Chinese"),
    FoodCategoryItem(id = 9, image = R.drawable.img_desserts_64, title = "Desserts"),
    FoodCategoryItem(id = 10, image = R.drawable.img_kebab_64, title = "Kebab"),
    FoodCategoryItem(id = 11, image = R.drawable.img_pie_64, title = "Pie"),
    FoodCategoryItem(id = 12, image = R.drawable.img_soup_64, title = "Soup"),
    FoodCategoryItem(id = 13, image = R.drawable.img_sushi_64, title = "Sushi"),
    FoodCategoryItem(id = 14, image = R.drawable.img_steak_64, title = "Steak"),
    FoodCategoryItem(id = 15, image = R.drawable.img_tacos_64, title = "Tacos"),
    FoodCategoryItem(id = 16, image = R.drawable.img_vegan_64, title = "Vegan"),
    FoodCategoryItem(id = 17, image = R.drawable.img_waffle_64, title = "Waffle"),
)