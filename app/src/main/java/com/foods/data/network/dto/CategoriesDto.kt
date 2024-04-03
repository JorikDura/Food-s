package com.foods.data.network.dto

import com.google.gson.annotations.SerializedName

data class CategoriesDto (
    @SerializedName("categories") val categories: List<CategoryDto>
)