package com.foods.presentation.main

import com.foods.domain.model.Meal
import com.foods.domain.model.Category

data class MainScreenState(
    val isLoading: Boolean = false,
    val mealsIsLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isEmpty: Boolean = false,
    val isError: Boolean = false,
    val categories: List<Category> = listOf(Category()),
    val banners: List<Int> = listOf(),
    val meals: List<Meal> = listOf(),
    val currentCategory: Category = Category(),
    val errorMessage: String = ""
)
