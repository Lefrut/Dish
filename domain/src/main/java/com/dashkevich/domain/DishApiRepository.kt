package com.dashkevich.domain

import com.dashkevich.data.api.model.DCategories

interface DishApiRepository {

    suspend fun getCategories() : Result<DCategories>

}