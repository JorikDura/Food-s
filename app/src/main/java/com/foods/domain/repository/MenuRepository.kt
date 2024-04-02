package com.foods.domain.repository

import com.foods.domain.model.Meal
import com.foods.domain.model.Tag

interface MenuRepository {
    /**
     * Returns tags from db/api
     */
    suspend fun getTags(fromRemote: Boolean): Result<List<Tag>>
    /**
     * Returns meals from db/api
     * Can pass tag
     */
    suspend fun getMeals(fromRemote: Boolean, tag: String?): Result<List<Meal>>
}