package com.dashkevich.domain.repository

import com.dashkevich.data.api.model.category.DCategories
import com.dashkevich.data.api.model.dish.Dishes

interface DishApiRepository {

    suspend fun getCategories() : Result<DCategories>

    suspend fun getDishes(tegs: List<String> = emptyList()): Result<Dishes>

    suspend fun getTags() : Result<Set<String>>

}