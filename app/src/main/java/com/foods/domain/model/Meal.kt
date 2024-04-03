package com.foods.domain.model

import kotlin.String

data class Meal(
    val id: String = UNDEFINED_ID,
    val imageUrl: String,
    val category: String,
    val title: String,
    val ingredients: String,
    val cost: String = "от 345 рублей"
) {
    companion object {
        private const val UNDEFINED_ID: String = "-1"
    }
}
