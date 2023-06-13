package com.dashkevich.domain.di

import com.dashkevich.domain.repository.DishApiRepository
import com.dashkevich.domain.repository.DishApiRepositoryImpl
import org.koin.dsl.module

val DomainModules = module {

    single<DishApiRepository> {
        DishApiRepositoryImpl(dishApi = get())
    }

}