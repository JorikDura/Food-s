package com.foods.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.foods.data.database.dao.MealDao
import com.foods.data.database.dao.TagDao

@Database(
    entities = [MealDao::class, TagDao::class],
    version = 1
)
abstract class MealDataBase : RoomDatabase() {

    abstract val mealDao: MealQuery
    abstract val tagDao: TagQuery

}