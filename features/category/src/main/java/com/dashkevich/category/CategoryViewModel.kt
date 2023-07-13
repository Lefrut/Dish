package com.dashkevich.category

import androidx.lifecycle.viewModelScope
import com.dashkevich.category.model.CategoryModel
import com.dashkevich.domain.use_case.LoadAllTegs
import com.dashkevich.domain.use_case.LoadDishesByTags
import com.dashkevich.util.common.BaseViewModel
import com.dashkevich.util.common.OperationState
import com.dashkevich.util.resultHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CategoryViewModel(
    private val loadDishesByTags: LoadDishesByTags, private val loadAllTegs: LoadAllTegs
) : BaseViewModel<CategoryModel>() {

    override fun setModel() : CategoryModel = CategoryModel()

    init {
        getTags()
    }

    private fun getTags() = viewModelScope.launch {
        loadAllTegs().resultHandler(
            onLoading = {
                setState {
                    copy(tegsState = OperationState.Loading)
                }
            },
            onSuccess = { newTegs ->
                val tegsList = newTegs.map { Pair(it, false) }.toMutableList()
                val index = 0
                tegsList[index] = tegsList[index].copy(second = true)
                setState {
                    copy(tegsState = OperationState.Success, tegs = tegsList.toList())
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

    fun getDishes(tegs: List<String> = emptyList()) = viewModelScope.launch(Dispatchers.IO) {
        loadDishesByTags(tegs).resultHandler(
            onLoading = {
                setState {
                    copy(dishesState = OperationState.Loading)
                }
            },
            onSuccess = { newDishes ->
                setState {
                    copy(dishesState = OperationState.Success, dishes = newDishes.toSet().toList())
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

    fun clickTeg(title: String) {
        val newTegs = viewState.value.tegs.map {
            if (it.first == title) it.copy(second = !it.second)
            else it
        }
        setState {
            copy(tegs = newTegs)
        }
    }
}