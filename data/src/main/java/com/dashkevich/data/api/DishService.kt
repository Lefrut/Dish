package com.dashkevich.data.api

import com.dashkevich.data.api.model.category.DCategories
import com.dashkevich.data.api.model.dish.Dishes
import retrofit2.http.GET


interface DishService {

    @GET(Endpoints.GET_CATEGORIES)
    suspend fun getCategories() : DCategories

    @GET(Endpoints.GET_DISHES)
    suspend fun getDishes() : Dishes

}