package com.dashkevich.domain.use_case

import com.dashkevich.domain.model.dish.Dish
import com.dashkevich.domain.repository.DishApiRepository
import com.dashkevich.util.coroutineCatching

class LoadBasketContentUseCase(private val dishApiRepository: DishApiRepository) : AsyncUseCase() {

    suspend operator fun invoke(idBasketDishes: Collection<Int>): Result<List<Dish>> = coroutineCatching(dispatcher) {
        return@coroutineCatching dishApiRepository.getDishes().dishes.filter { dish ->
            for (id in idBasketDishes) {
                if (id == dish.id && id != 0) return@filter true
            }
            return@filter false
        }
    }

}