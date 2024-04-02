package com.foods.data.mapper

import com.foods.data.database.dao.TagDao
import com.foods.data.network.dto.TagDto
import com.foods.domain.model.Tag

fun TagDto.toTag(): TagDao {
    return TagDao(
        id = id,
        title = title
    )
}

fun TagDao.toTag(): Tag {
    return Tag(
        id = id,
        title = title
    )
}