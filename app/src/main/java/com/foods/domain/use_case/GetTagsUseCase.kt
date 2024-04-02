package com.foods.domain.use_case

import com.foods.domain.model.Tag
import com.foods.domain.repository.MenuRepository
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(
    private val repository: MenuRepository
) {
    suspend operator fun invoke(fromRemote: Boolean = false): Result<List<Tag>> {
        return repository.getTags(fromRemote = fromRemote)
    }

}