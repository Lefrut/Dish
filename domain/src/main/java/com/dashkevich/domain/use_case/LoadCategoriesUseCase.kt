package com.dashkevich.domain.use_case

import com.dashkevich.domain.model.category.DCategories
import com.dashkevich.domain.repository.DishApiRepository
import com.dashkevich.util.coroutineCatching

class LoadCategoriesUseCase(private val dishApiRepository: DishApiRepository) : AsyncUseCase() {

    suspend operator fun invoke(): Result<DCategories> = coroutineCatching(dispatcher) {
        return@coroutineCatching dishApiRepository.getCategories()
    }

}