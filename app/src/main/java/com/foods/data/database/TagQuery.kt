package com.foods.data.database

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.foods.data.database.dao.TagDao

@Dao
interface TagQuery {
    @Upsert
    suspend fun upsertTag(tagDao: TagDao)

    @Query("DELETE FROM tag")
    suspend fun deleteAllTags()

    @Query("SELECT * FROM tag ORDER BY id ASC")
    suspend fun getTags(): List<TagDao>

}