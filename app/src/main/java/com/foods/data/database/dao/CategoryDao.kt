package com.foods.data.database.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "category")
data class CategoryDao(
    @PrimaryKey
    val id: String = "0",
    val title: String
)
