package com.foods.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.foods.data.database.dao.CategoryDao

@Dao
interface CategoryQuery {
    @Upsert
    suspend fun upsertCategory(category: CategoryDao)

    @Query("SELECT * FROM category ORDER BY id ASC")
    suspend fun getCategories(): List<CategoryDao>

}