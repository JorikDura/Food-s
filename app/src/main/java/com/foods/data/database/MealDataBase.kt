package com.foods.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.foods.data.database.dao.MealDao
import com.foods.data.database.dao.CategoryDao

@Database(
    entities = [MealDao::class, CategoryDao::class],
    version = 1
)
abstract class MealDataBase : RoomDatabase() {

    abstract val mealQuery: MealQuery
    abstract val categoryQuery: CategoryQuery

}