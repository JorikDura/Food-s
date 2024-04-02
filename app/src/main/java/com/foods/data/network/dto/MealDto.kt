package com.foods.data.network.dto

import com.google.gson.annotations.SerializedName

data class MealDto(
    @SerializedName("idMeal") val id: String,
    @SerializedName("strMealThumb") val imageUrl: String,
    @SerializedName("strCategory") val tag: String,
    @SerializedName("strMeal") val title: String,
    @SerializedName("strIngredient1") val ingredient1: String,
    @SerializedName("strIngredient2") val ingredient2: String,
    @SerializedName("strIngredient3") val ingredient3: String,
    @SerializedName("strIngredient4") val ingredient4: String,
    @SerializedName("strIngredient5") val ingredient5: String,
    @SerializedName("strIngredient6") val ingredient6: String
)
