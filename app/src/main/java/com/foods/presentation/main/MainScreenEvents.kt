package com.foods.presentation.main

import com.foods.domain.model.Meal
import com.foods.domain.model.Tag

sealed class MainScreenEvents {
    data class ChangeTag(val tag: Tag) : MainScreenEvents()
    data class BuyMeal(val meal: Meal) : MainScreenEvents()
    data object Refresh : MainScreenEvents()
}