package com.dashkevich.home

import android.annotation.SuppressLint
import androidx.lifecycle.viewModelScope
import com.dashkevich.domain.repository.DishApiRepository
import com.dashkevich.home.model.HomeModel
import com.dashkevich.util.BaseViewModel
import com.dashkevich.util.OperationState
import com.dashkevich.util.resultHandler
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*


class HomeViewModel(
    private val dishApiRepository: DishApiRepository
) : BaseViewModel<HomeModel>() {
    override fun setModel(): HomeModel = HomeModel()

    init {
        getCategories()
    }

    fun getCategories() = viewModelScope.launch() {
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
            onError = { error ->
                setState {
                    copy(categoriesState = OperationState.Error)
                }
            }
        )
    }
}