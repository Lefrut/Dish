package com.dashkevich.data.di

import com.dashkevich.data.api.DishService
import com.dashkevich.data.api.Endpoints
import com.dashkevich.data.repository.DishApiRepositoryImpl
import com.dashkevich.domain.repository.DishApiRepository
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


val retrofitModule = module {
    single<Retrofit> {
        Retrofit.Builder().baseUrl(Endpoints.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    single<DishService> {
        get<Retrofit>().create(DishService::class.java)
    }
}

val repositoryModule = module {

    single<DishApiRepository> {
        DishApiRepositoryImpl(dishApi = get())
    }

}

val dataModules = module {
    includes(retrofitModule, repositoryModule)
}