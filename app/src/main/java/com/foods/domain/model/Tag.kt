package com.foods.domain.model

import kotlin.String

data class Tag(
    val id: String = UNDEFINED_ID,
    val title: String = "All"
) {
    companion object {
        private const val UNDEFINED_ID: String = "-1"
    }
}
