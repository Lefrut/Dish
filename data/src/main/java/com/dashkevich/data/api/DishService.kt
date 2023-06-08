package com.dashkevich.data.api

import com.dashkevich.data.api.model.Category
import retrofit2.http.GET


interface DishService {

    @GET(Endpoints.GET_CATEGORIES)
    suspend fun getCategories() : List<Category>

}