package com.dashkevich.data.di

import com.dashkevich.data.api.DishService
import com.dashkevich.data.api.Endpoints
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val DataModules = module {

    single<Retrofit> {
        Retrofit.Builder().baseUrl(Endpoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single<DishService> {
        get<Retrofit>().create(DishService::class.java)
    }

}