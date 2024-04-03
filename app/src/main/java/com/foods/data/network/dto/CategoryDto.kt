package com.foods.data.network.dto

import com.google.gson.annotations.SerializedName

data class CategoryDto(
    @SerializedName("idCategory") val id: String,
    @SerializedName("strCategory") val title: String
)
