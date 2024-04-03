package com.foods.data.database.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "meal")
data class MealDao(
    @PrimaryKey
    val id: String = "0",
    val imageUrl: String,
    val category: String,
    val title: String,
    val ingredients: String
)
