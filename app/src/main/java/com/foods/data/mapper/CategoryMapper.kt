package com.foods.data.mapper

import com.foods.data.database.dao.CategoryDao
import com.foods.data.network.dto.CategoryDto
import com.foods.domain.model.Category

fun CategoryDto.toCategoryDao(): CategoryDao {
    return CategoryDao(
        id = id,
        title = title
    )
}

fun CategoryDao.toCategory(): Category {
    return Category(
        id = id,
        title = title
    )
}