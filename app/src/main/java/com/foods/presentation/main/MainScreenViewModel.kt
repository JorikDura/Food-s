package com.foods.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foods.R
import com.foods.domain.model.Category
import com.foods.domain.use_case.GetMealsUseCase
import com.foods.domain.use_case.GetCategoriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getMealsUseCase: GetMealsUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState(isLoading = true))
    val state: StateFlow<MainScreenState> = _state.asStateFlow()

    init {
        viewModelScope.launch {
            getData()
        }
    }

    /**
     * Event handler
     */
    fun onEvent(event: MainScreenEvents) {
        when (event) {
            is MainScreenEvents.BuyMeal -> {
                //Sending meal to cart.
            }

            is MainScreenEvents.ChangeCategory -> {
                _state.update {
                    it.copy(
                        mealsIsLoading = true,
                        currentCategory = event.category
                    )
                }
                viewModelScope.launch { getMealsByCategory() }
            }

            is MainScreenEvents.Refresh -> {
                _state.update {
                    it.copy(
                        isLoading = true,
                        isRefreshing = true,
                        categories = listOf(Category()),
                        meals = emptyList()
                    )
                }
                viewModelScope.launch {
                    getData(fromRemote = true)
                }
            }
        }
    }

    /**
     * Getting all data(categories, meals) from db/api
     */
    private suspend fun getData(fromRemote: Boolean = false) {
        val categories = getCategoriesUseCase(fromRemote = fromRemote)
        val meals = getMealsUseCase(
            fromRemote = fromRemote,
            category = if (_state.value.currentCategory.title == DEFAULT_CATEGORY_VALUE) null
            else _state.value.currentCategory.title
        )
        when {
            categories.isFailure -> {
                val categoryError = categories.exceptionOrNull()
                _state.update {
                    it.copy(
                        isError = true,
                        errorMessage = categoryError?.message ?: DEFAULT_ERROR
                    )
                }
            }

            meals.isFailure -> {
                val mealError = meals.exceptionOrNull()
                _state.update {
                    it.copy(
                        isError = true,
                        errorMessage = mealError?.message ?: DEFAULT_ERROR
                    )
                }
            }

            else -> {
                val categoriesResult = categories.getOrDefault(emptyList())
                val mealsResult = meals.getOrDefault(emptyList())
                _state.update {
                    it.copy(
                        isLoading = false,
                        mealsIsLoading = false,
                        isRefreshing = false,
                        isEmpty = categoriesResult.isEmpty() && mealsResult.isEmpty(),
                        categories = _state.value.categories + categoriesResult,
                        meals = mealsResult,
                        banners = getFakeBanners(),
                        isError = false,
                        errorMessage = NO_ERROR
                    )
                }
            }
        }
    }

    /**
     * Getting meals by category
     * Default category = "All"
     */
    private suspend fun getMealsByCategory() {
        val meals = getMealsUseCase(category = _state.value.currentCategory.title)

        if (meals.isFailure) {
            val mealError = meals.exceptionOrNull()
            _state.update {
                it.copy(
                    isError = true,
                    errorMessage = mealError?.message ?: DEFAULT_ERROR
                )
            }
        }

        _state.update {
            it.copy(
                mealsIsLoading = false,
                meals = meals.getOrDefault(emptyList()),
                isError = false,
                errorMessage = NO_ERROR
            )
        }
    }

    /**
     * Getting fake local banners
     */
    private fun getFakeBanners(): List<Int> {
        return mutableListOf<Int>().apply {
            add(R.drawable.banner_1)
            add(R.drawable.banner_2)
            add(R.drawable.banner_3)
        }
    }

    companion object {
        const val DEFAULT_CATEGORY_VALUE = "All"
        private const val DEFAULT_ERROR = "Something bad happened"
        private const val NO_ERROR = ""
    }
}