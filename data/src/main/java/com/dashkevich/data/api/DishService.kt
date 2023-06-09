package com.dashkevich.data.api

import com.dashkevich.data.api.model.DCategories
import retrofit2.http.GET


interface DishService {

    @GET(Endpoints.GET_CATEGORIES)
    suspend fun getCategories() : DCategories

}