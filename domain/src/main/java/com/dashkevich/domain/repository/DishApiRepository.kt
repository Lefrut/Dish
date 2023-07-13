package com.dashkevich.domain.repository

import com.dashkevich.domain.model.category.DCategories
import com.dashkevich.domain.model.dish.Dishes

interface DishApiRepository {

    suspend fun getCategories() : DCategories


    suspend fun getDishes() : Dishes

}