package com.dashkevich.domain.repository

import android.util.Log
import com.dashkevich.data.api.DishService
import com.dashkevich.data.api.model.category.DCategories
import com.dashkevich.data.api.model.dish.Dish
import com.dashkevich.data.api.model.dish.Dishes
import com.dashkevich.domain.util.coroutineCatching
import kotlinx.coroutines.*

class DishApiRepositoryImpl(
    private val dishApi: DishService,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : DishApiRepository {

    override suspend fun getCategories(): Result<DCategories> {
        return coroutineCatching(dispatcher) {
            dishApi.getCategories()
        }
    }

    override suspend fun getDishes(tegs: List<String>): Result<Dishes> {
        return coroutineCatching(dispatcher) {
            val resultDishes = mutableListOf<Dish>()
            val dishes = dishApi.getDishes()
            if (tegs.isEmpty()) {
                return@coroutineCatching dishes
            }

            val coroutineScope = CoroutineScope(Dispatchers.IO)
            val jobs: MutableList<Job> = mutableListOf()

            dishes.dishes.forEach { dish ->
                    dish.tegs.forEach { dishTeg ->
                        tegs.forEach { teg ->
                            if (dishTeg == teg) {
                                synchronized(this) {
                                    resultDishes += dish
                                }
                            }
                        }
                    }
            }
            jobs.forEach { job ->
                job.join()
            }
            Dishes(resultDishes)
        }
    }

    override suspend fun getTags(): Result<Set<String>> {
        return coroutineCatching(dispatcher) {
            val tegs: MutableSet<String> = mutableSetOf()
            dishApi.getDishes().dishes.forEach { dish ->
                tegs += dish.tegs
            }
            tegs.toSet()
        }
    }

    override suspend fun getBasketDishes(idBasketDishes: List<Int>): Result<List<Dish>> {
        return coroutineCatching(dispatcher) {
            val dishes = dishApi.getDishes().dishes.filter { dish ->
                for (id in idBasketDishes) {
                    if (id == dish.id && id != 0) return@filter true
                }
                return@filter false
            }
            dishes
        }
    }

}