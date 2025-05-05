package com.example.nutrifind.utils

import com.example.nutrifind.data.local.offline.Food
import com.example.nutrifind.data.local.offline.Nutrition
import com.example.nutrifind.data.remote.model.ApiEdamam
import com.example.nutrifind.data.remote.model.Hits

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

