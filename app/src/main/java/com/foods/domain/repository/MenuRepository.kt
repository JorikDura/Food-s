package com.foods.domain.repository

import com.foods.domain.model.Meal
import com.foods.domain.model.Category

interface MenuRepository {
    /**
     * Returns categories from db/api
     */
    suspend fun getCategories(fromRemote: Boolean): Result<List<Category>>
    /**
     * Returns meals from db/api
     * Can find by category
     */
    suspend fun getMeals(fromRemote: Boolean, category: String?): Result<List<Meal>>
}