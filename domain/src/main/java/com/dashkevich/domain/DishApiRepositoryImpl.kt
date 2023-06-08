package com.dashkevich.domain

import com.dashkevich.data.api.DishService
import com.dashkevich.data.api.model.DCategories
import com.dashkevich.domain.util.coroutineCatching
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

class DishApiRepositoryImpl(
    private val dishApi: DishService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DishApiRepository {

    override suspend fun getCategories(): Result<DCategories> {
        return coroutineCatching(dispatcher) {
            dishApi.getCategories()
        }
    }

}