package com.foods.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.foods.data.database.dao.MealDao

@Dao
interface MealQuery {
    @Upsert
    suspend fun upsertMeal(meal: MealDao)

    @Query("DELETE FROM meal")
    suspend fun deleteAllMeals()

    @Query("SELECT * FROM meal ORDER BY id ASC")
    suspend fun getMeals(): List<MealDao>

    @Query("SELECT * FROM meal WHERE tag LIKE :tag ORDER BY id ASC")
    suspend fun getMealsByTag(tag: String): List<MealDao>
}