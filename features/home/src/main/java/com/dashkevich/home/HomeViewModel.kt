package com.dashkevich.home

import androidx.lifecycle.viewModelScope
import com.dashkevich.domain.repository.DishApiRepository
import com.dashkevich.home.model.HomeModel
import com.dashkevich.util.common.BaseViewModel
import com.dashkevich.util.common.OperationState
import com.dashkevich.util.resultHandler
import kotlinx.coroutines.launch


class HomeViewModel(
    private val dishApiRepository: DishApiRepository
) : BaseViewModel<HomeModel>() {
    override fun setModel(): HomeModel = HomeModel()

    init {
        getCategories()
    }

    private fun getCategories() = viewModelScope.launch() {
        dishApiRepository.getCategories().resultHandler(
            onLoading = {
                setState {
                    copy(categoriesState = OperationState.Loading)
                }
            },
            onSuccess = { value ->
                val newCategories = value.categories
                setState {
                    copy(categories = newCategories, categoriesState = OperationState.Success)
                }
            },
            onEmptyResult = {
                setState {
                    copy(categoriesState = OperationState.EmptyResult)
                }
            },
            onError = { _ ->
                setState {
                    copy(categoriesState = OperationState.Error)
                }
            }
        )
    }
}