package com.foods.data.network

import com.foods.data.network.dto.MealsDto
import com.foods.data.network.dto.TagsDto
import retrofit2.http.GET

interface ApiService {

    @GET("/api/json/v1/1/search.php?s")
    suspend fun getMeals(): MealsDto

    @GET("/api/json/v1/1/categories.php")
    suspend fun getTags(): TagsDto

}