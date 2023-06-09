package com.dashkevich.category

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.dashkevich.category.model.CategoryModel
import com.dashkevich.domain.repository.DishApiRepository
import com.dashkevich.util.BaseViewModel
import com.dashkevich.util.OperationState
import com.dashkevich.util.resultHandler
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val dishApiRepository: DishApiRepository
) : BaseViewModel<CategoryModel>() {

    override fun setModel(): CategoryModel = CategoryModel()

    fun getTags() = viewModelScope.launch {
        dishApiRepository.getTags().resultHandler(
            onLoading = {
                setState {
                    copy(tegsState = OperationState.Loading)
                }
            },
            onSuccess = { newTegs ->
                setState {
                    copy(tegsState = OperationState.Success, tegs = newTegs)
                }
            },
            onError = {
                setState {
                    copy(tegsState = OperationState.Error)
                }

            },
            onEmptyResult = {
                setState {
                    copy(tegsState = OperationState.EmptyResult)
                }

            }
        )
    }

    fun getDishes() = viewModelScope.launch {
        dishApiRepository.getDishes().resultHandler(
            onLoading = {
                setState {
                    copy(dishesState = OperationState.Loading)
                }
            },
            onSuccess = { newDishes ->
                setState {
                    copy(dishesState = OperationState.Success, dishes = newDishes.dishes)
                }
            },
            onError = {
                setState {
                    copy(dishesState = OperationState.Error)
                }
            },
            onEmptyResult = {
                setState {
                    copy(dishesState = OperationState.EmptyResult)
                }
            }
        )
    }
}