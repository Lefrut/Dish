package com.dashkevich.data.repository

import com.dashkevich.data.api.DishService
import com.dashkevich.domain.model.category.DCategories
import com.dashkevich.domain.model.dish.Dish
import com.dashkevich.domain.model.dish.Dishes
import com.dashkevich.domain.repository.DishApiRepository
import kotlinx.coroutines.*

class DishApiRepositoryImpl(
    private val dishApi: DishService,
) : DishApiRepository {

    override suspend fun getCategories(): DCategories {
        return dishApi.getCategories()
    }

    override suspend fun getDishes(): Dishes {
        return dishApi.getDishes()
    }



}