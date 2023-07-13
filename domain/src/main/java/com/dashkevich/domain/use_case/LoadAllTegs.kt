package com.dashkevich.domain.use_case

import com.dashkevich.domain.repository.DishApiRepository
import com.dashkevich.util.coroutineCatching

class LoadAllTegs(private val dishApiRepository: DishApiRepository): AsyncUseCase() {

    suspend operator fun invoke(): Result<Set<String>> = coroutineCatching(dispatcher) {
        val tegs: MutableSet<String> = mutableSetOf()
        dishApiRepository.getDishes().dishes.forEach { dish ->
            tegs += dish.tegs
        }
        return@coroutineCatching tegs.toSet()
    }

}