package com.example.nutrifind.utils

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


data class TagFilterItem(
    val id: Int = -1,
    val title: String = "",
    val selected: Boolean
)