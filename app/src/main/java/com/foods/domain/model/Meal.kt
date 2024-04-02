package com.foods.domain.model

import kotlin.String

data class Meal(
    val id: String = UNDEFINED_ID,
    val imageUrl: String = "https://sun9-77.userapi.com/impg/kGGTQkhFoDU41wMXNMw0uUR2EOFMCXbP8L5b0A/Xs1Ayl-hB34.jpg?size=810x1080&quality=96&sign=ecef3da17eab28f4858c4cdc8db81446",
    val tag: String = "Test",
    val title: String = "Test",
    val ingredients: String = "Test, Test, Test, Test, Test, Test, Test",
    val cost: String = "от 345 рублей"
) {
    companion object {
        private const val UNDEFINED_ID: String = "-1"
    }
}
