package com.dashkevich.domain.repository

import com.dashkevich.data.api.DishService
import com.dashkevich.data.api.model.category.DCategories
import com.dashkevich.data.api.model.dish.Dishes
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

    override suspend fun getDishes(): Result<Dishes> {
        return coroutineCatching(dispatcher){
            dishApi.getDishes()
        }
    }

    override suspend fun getTags(): Result<Set<String>> {
        return coroutineCatching(dispatcher){
            val tegs: MutableSet<String> = mutableSetOf()
            dishApi.getDishes().dishes.forEach { dish ->
                tegs += dish.tegs
            }
            tegs.toSet()
        }
    }

}