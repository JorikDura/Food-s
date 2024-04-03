package com.foods.domain.use_case

import com.foods.domain.model.Meal
import com.foods.domain.repository.MenuRepository
import javax.inject.Inject

class GetMealsUseCase @Inject constructor(
    private val repository: MenuRepository
) {

    suspend operator fun invoke(
        fromRemote: Boolean = false,
        category: String? = null
    ): Result<List<Meal>> {
        return repository.getMeals(fromRemote = fromRemote, category = category)
    }

}