package com.foods.presentation.main

import com.foods.domain.model.Meal
import com.foods.domain.model.Tag

data class MainScreenState(
    val isLoading: Boolean = false,
    val mealsIsLoading: Boolean = false,
    val isRefreshing: Boolean = false,
    val isEmpty: Boolean = false,
    val isError: Boolean = false,
    val tags: List<Tag> = listOf(Tag()),
    val banners: List<Int> = listOf(),
    val meals: List<Meal> = listOf(),
    val currentTag: Tag = Tag(),
    val errorMessage: String = ""
)
