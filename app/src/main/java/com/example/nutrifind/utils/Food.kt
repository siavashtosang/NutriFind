package com.example.nutrifind.utils

import com.example.nutrifind.R
import com.example.nutrifind.data.model.ApiEdamam
import com.example.nutrifind.data.model.Hits
import com.example.nutrifind.data.model.Ingredients


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

fun ApiEdamam.convertToFoodClass(): List<Food> {

    var foodList = emptyList<Food>()

    this.hits.forEach { hits: Hits ->

        foodList = foodList.plus(
            element =
            Food(
                recipeUrl = hits.recipe?.url ?: "",
                name = hits.recipe?.label ?: "",
                image = hits.recipe?.images?.regular?.url ?: "",
                calories = hits.recipe?.calories?.toInt() ?: 0,
                serving = hits.recipe?.yield?.toInt() ?: 0,
                ingredients = hits.recipe?.ingredients ?: emptyList(),
                nutrition = createNutritionList(hits)
            )

        )
    }

    return foodList
}

private fun createNutritionList(hits: Hits?): List<Nutrition> {

    val totalNutrients = hits?.recipe?.totalNutrients ?: return emptyList()

    return listOfNotNull(
        totalNutrients.procnt?.let {
            Nutrition(
                name = it.label.toString(),
                value = it.quantity?.toInt().toString(),
                unit = it.unit.toString()
            )
        },

        totalNutrients.chocdf?.let {
            Nutrition(
                name = it.label.toString(),
                value = it.quantity?.toInt().toString(),
                unit = it.unit.toString()
            )
        },

        totalNutrients.fat?.let {
            Nutrition(
                name = it.label.toString(),
                value = it.quantity?.toInt().toString(),
                unit = it.unit.toString()
            )
        },

        totalNutrients.chole?.let {
            Nutrition(
                name = it.label.toString(),
                value = it.quantity?.toInt().toString(),
                unit = it.unit.toString()
            )
        },

        totalNutrients.sugar?.let {
            Nutrition(
                name = it.label.toString(),
                value = it.quantity?.toInt().toString(),
                unit = it.unit.toString()
            )
        },

        totalNutrients.fibtg?.let {
            Nutrition(
                name = it.label.toString(),
                value = it.quantity?.toInt().toString(),
                unit = it.unit.toString()
            )
        },

        totalNutrients.ca?.let {
            Nutrition(
                name = it.label.toString(),
                value = it.quantity?.toInt().toString(),
                unit = it.unit.toString()
            )
        },

        totalNutrients.mg?.let {
            Nutrition(
                name = it.label.toString(),
                value = it.quantity?.toInt().toString(),
                unit = it.unit.toString()
            )
        }
    )
}
