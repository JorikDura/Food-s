package com.foods.domain.repository

import com.foods.domain.model.Meal
import com.foods.domain.model.Tag

interface MenuRepository {
    suspend fun getTags(fromRemote: Boolean): Result<List<Tag>>
    suspend fun getMeals(fromRemote: Boolean, tag: String?): Result<List<Meal>>
}