package com.foods.data.repository

import android.util.Log
import com.foods.data.database.MealDataBase
import com.foods.data.mapper.toMeal
import com.foods.data.mapper.toMealDao
import com.foods.data.mapper.toCategoryDao
import com.foods.data.mapper.toCategory
import com.foods.data.network.ApiService
import com.foods.domain.model.Meal
import com.foods.domain.model.Category
import com.foods.domain.repository.MenuRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mealDB: MealDataBase
) : MenuRepository {

    override suspend fun getCategories(fromRemote: Boolean): Result<List<Category>> {
        try {
            var categoriesFromDb = mealDB.categoryQuery.getCategories()
            if (categoriesFromDb.isEmpty() || fromRemote) {
                val remoteCategories = apiService.getCategories().categories.map { it.toCategoryDao() }
                remoteCategories.forEach { category ->
                    mealDB.categoryQuery.upsertCategory(category)
                }
                categoriesFromDb = mealDB.categoryQuery.getCategories()
            }
            val categories = categoriesFromDb.map { it.toCategory() }
            return Result.success(categories)
        } catch (e: HttpException) {
            Log.e("HttpException", e.message ?: "")
            return Result.failure(e)
        } catch (e: IOException) {
            Log.e("IOException", e.message ?: "")
            return Result.failure(e)
        }
    }

    override suspend fun getMeals(fromRemote: Boolean, category: String?): Result<List<Meal>> {
        try {
            var mealFromDB =
                if (category.isNullOrBlank() || category == DEFAULT_CATEGORY_VALUE) mealDB.mealQuery.getMeals()
                else mealDB.mealQuery.getMealsByCategory(category)

            if (mealFromDB.isEmpty() && category.isNullOrBlank() || fromRemote) {
                val remoteMeals = apiService.getMeals().meals.map { it.toMealDao() }
                remoteMeals.forEach { meal ->
                    mealDB.mealQuery.upsertMeal(meal)
                }
                mealFromDB =
                    if (category.isNullOrBlank() || category == DEFAULT_CATEGORY_VALUE) mealDB.mealQuery.getMeals()
                    else mealDB.mealQuery.getMealsByCategory(category)
            }
            val meals = mealFromDB.map { it.toMeal() }
            return Result.success(meals)
        } catch (e: HttpException) {
            Log.e("HttpException", e.message ?: "")
            return Result.failure(e)
        } catch (e: IOException) {
            Log.e("IOException", e.message ?: "")
            return Result.failure(e)
        }
    }

    companion object {
        const val DEFAULT_CATEGORY_VALUE = "All"
    }
}