package com.dashkevich.domain

import com.dashkevich.data.api.model.Category

interface DishApiRepository {

    suspend fun getCategories() : Result<List<Category>>

}