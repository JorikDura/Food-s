package com.foods.domain.use_case

import com.foods.domain.model.Category
import com.foods.domain.repository.MenuRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: MenuRepository
) {
    suspend operator fun invoke(fromRemote: Boolean = false): Result<List<Category>> {
        return repository.getCategories(fromRemote = fromRemote)
    }

}