package com.foods.data.mapper

import com.foods.data.database.dao.MealDao
import com.foods.data.network.dto.MealDto
import com.foods.domain.model.Meal


fun MealDto.toMealDao(): MealDao {
    return MealDao(
        id = id,
        title = title,
        imageUrl = imageUrl,
        category = category,
        ingredients = "$ingredient1, $ingredient2, $ingredient3, $ingredient4, $ingredient5, $ingredient6"
    )
}

fun MealDao.toMeal(): Meal {
    return Meal(
        id = id,
        title = title,
        imageUrl = imageUrl,
        category = category,
        ingredients = ingredients
    )
}