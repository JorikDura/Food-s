package com.foods.data.database.dao

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tag")
data class TagDao(
    @PrimaryKey
    val id: String = "0",
    val title: String
)
