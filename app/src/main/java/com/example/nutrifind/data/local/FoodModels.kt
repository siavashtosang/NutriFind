package com.example.nutrifind.data.local


import com.example.nutrifind.R
import com.example.nutrifind.data.remote.model.Ingredients


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

val topFoods = listOf(
    TopFood(title = "Salad", image = R.drawable.img_salad_120),
    TopFood(title = "Pizza", image = R.drawable.img_pizza_120),
    TopFood(title = "Chinese", image = R.drawable.img_chinese_120),
)

val dietsFilterList = listOf(
    TagFilterItem(id = 0, title = "balanced", selected = false),
    TagFilterItem(id = 1, title = "high-fiber", selected = false),
    TagFilterItem(id = 2, title = "high-protein", selected = false),
    TagFilterItem(id = 3, title = "low-fat", selected = false),
    TagFilterItem(id = 4, title = "low-carb", selected = false),
    TagFilterItem(id = 5, title = "low-sodium", selected = false),
)

val dishTypesFilterList = listOf(
    TagFilterItem(id = 0, title = "Main course", selected = false),
    TagFilterItem(id = 1, title = "Side dish", selected = false),
    TagFilterItem(id = 4, title = "Starter", selected = false),
)

val mealTypesFilterList = listOf(
    TagFilterItem(id = 0, title = "Breakfast", selected = false),
    TagFilterItem(id = 1, title = "Dinner", selected = false),
    TagFilterItem(id = 2, title = "Lunch", selected = false),
    TagFilterItem(id = 3, title = "Snack", selected = false),
    TagFilterItem(id = 4, title = "Teatime", selected = false),
)

val cuisineTypes = listOf(
    TagFilterItem(id = 0, title = "American", selected = false),
    TagFilterItem(id = 1, title = "Asian", selected = false),
    TagFilterItem(id = 2, title = "British", selected = false),
    TagFilterItem(id = 3, title = "Caribbean", selected = false),
    TagFilterItem(id = 4, title = "Central Europe", selected = false),
    TagFilterItem(id = 5, title = "Chinese", selected = false),
    TagFilterItem(id = 6, title = "Eastern Europe", selected = false),
    TagFilterItem(id = 7, title = "French", selected = false),
    TagFilterItem(id = 8, title = "Greek", selected = false),
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


data class FoodCategoryItem(
    val id: Int = -1,
    val image: Int = R.drawable.img_pizza_64,
    val title: String = ""
)

data class TagFilterItem(
    val id: Int = -1,
    val title: String = "",
    val selected: Boolean
)

data class TopFood(
    val title: String = "",
    val image: Int = R.drawable.img_pasta,
)

data class Nutrition(
    val name: String,
    val value: String,
    val unit: String
)

data class Food(
    val recipeUrl: String = "",
    val name: String = "",
    val image: String = "",
    val calories: Int = 0,
    val serving: Int = 0,
    val ingredients: List<Ingredients> = emptyList(),
    val nutrition: List<Nutrition> = emptyList(),
)