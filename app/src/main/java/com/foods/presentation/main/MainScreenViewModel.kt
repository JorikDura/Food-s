package com.foods.presentation.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.foods.R
import com.foods.domain.model.Tag
import com.foods.domain.use_case.GetMealsUseCase
import com.foods.domain.use_case.GetTagsUseCase
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
    private val getTagsUseCase: GetTagsUseCase
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

            is MainScreenEvents.ChangeTag -> {
                _state.update {
                    it.copy(
                        mealsIsLoading = true,
                        currentTag = event.tag
                    )
                }
                viewModelScope.launch { getMealsByTag() }
            }

            is MainScreenEvents.Refresh -> {
                _state.update {
                    it.copy(
                        isLoading = true,
                        isRefreshing = true,
                        tags = listOf(Tag()),
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
     * Getting all data(tags, meals) from db/api
     */
    private suspend fun getData(fromRemote: Boolean = false) {
        val tags = getTagsUseCase(fromRemote = fromRemote)
        val meals = getMealsUseCase(fromRemote = fromRemote, tag = _state.value.currentTag.title)
        when {
            tags.isFailure -> {
                val tagError = tags.exceptionOrNull()
                _state.update {
                    it.copy(
                        isError = true,
                        errorMessage = tagError?.message ?: DEFAULT_ERROR
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
                val tagsResult = tags.getOrDefault(emptyList())
                val mealsResult = meals.getOrDefault(emptyList())
                _state.update {
                    it.copy(
                        isLoading = false,
                        mealsIsLoading = false,
                        isRefreshing = false,
                        isEmpty = tagsResult.isEmpty() && mealsResult.isEmpty(),
                        tags = _state.value.tags + tagsResult,
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
     * Getting meals by tag
     * Default tag is "All"
     */
    private suspend fun getMealsByTag() {
        val meals = getMealsUseCase(tag = _state.value.currentTag.title)

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
        private const val DEFAULT_ERROR = "Something bad happened"
        private const val NO_ERROR = ""
    }
}