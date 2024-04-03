package com.foods.presentation.main

import com.foods.domain.model.Meal
import com.foods.domain.model.Category

sealed class MainScreenEvents {
    data class ChangeCategory(val category: Category) : MainScreenEvents()
    data class BuyMeal(val meal: Meal) : MainScreenEvents()
    data object Refresh : MainScreenEvents()
}