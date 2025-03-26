package com.example.nutrifind.data.offline

val dietFilterList = listOf(
    TagFilterItem(id = 0, title = "Vegetarian"),
    TagFilterItem(id = 1, title = "Vegan"),
    TagFilterItem(id = 2, title = "Paleo"),
    TagFilterItem(id = 3, title = "High-Fiber"),
    TagFilterItem(id = 4, title = "High-Protein"),
    TagFilterItem(id = 5, title = "Low-Carb"),
    TagFilterItem(id = 6, title = "Low-Fat"),
    TagFilterItem(id = 7, title = "Low-Sodium"),
    TagFilterItem(id = 8, title = "Low-Sugar"),
    TagFilterItem(id = 9, title = "Alcohol-Free"),
    TagFilterItem(id = 10, title = "Balanced"),
    TagFilterItem(id = 11, title = "Immunity"),
)


data class TagFilterItem(
    val id: Int = -1,
    val title: String = ""
)