package com.foods.data.repository

import android.util.Log
import com.foods.data.database.MealDataBase
import com.foods.data.mapper.toMeal
import com.foods.data.mapper.toMealDao
import com.foods.data.mapper.toTag
import com.foods.data.network.ApiService
import com.foods.domain.model.Meal
import com.foods.domain.model.Tag
import com.foods.domain.repository.MenuRepository
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class MenuRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val mealDB: MealDataBase
) : MenuRepository {

    override suspend fun getTags(fromRemote: Boolean): Result<List<Tag>> {
        try {
            var tagsFromDb = mealDB.tagDao.getTags()
            if (tagsFromDb.isEmpty() || fromRemote) {
                val remoteTags = apiService.getTags().tags.map { it.toTag() }
                remoteTags.forEach { tag ->
                    mealDB.tagDao.upsertTag(tag)
                }
                tagsFromDb = mealDB.tagDao.getTags()
            }
            val tags = tagsFromDb.map { it.toTag() }
            return Result.success(tags)
        } catch (e: HttpException) {
            Log.e("HttpException", e.message ?: "")
            return Result.failure(e)
        } catch (e: IOException) {
            Log.e("IOException", e.message ?: "")
            return Result.failure(e)
        }
    }

    override suspend fun getMeals(fromRemote: Boolean, tag: String?): Result<List<Meal>> {
        try {
            var mealFromDB =
                if (tag.isNullOrBlank() || tag == DEFAULT_TAG_VALUE) mealDB.mealDao.getMeals()
                else mealDB.mealDao.getMealsByTag(tag)

            if (mealFromDB.isEmpty() && tag.isNullOrBlank() || fromRemote) {
                val remoteMeals = apiService.getMeals().meals.map { it.toMealDao() }
                remoteMeals.forEach { meal ->
                    mealDB.mealDao.upsertMeal(meal)
                }
                mealFromDB =
                    if (tag.isNullOrBlank() || tag == DEFAULT_TAG_VALUE) mealDB.mealDao.getMeals()
                    else mealDB.mealDao.getMealsByTag(tag)
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
        const val DEFAULT_TAG_VALUE = "All"
    }
}