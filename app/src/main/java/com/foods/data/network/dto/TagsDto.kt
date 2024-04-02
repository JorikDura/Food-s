package com.foods.data.network.dto

import com.google.gson.annotations.SerializedName

data class TagsDto (
    @SerializedName("categories") val tags: List<TagDto>
)