package com.dashkevich.domain.di

import android.content.Context
import com.dashkevich.domain.DishApiRepository
import com.dashkevich.domain.DishApiRepositoryImpl
import org.koin.dsl.module

val DomainModules = module {

    single<DishApiRepository> {
        DishApiRepositoryImpl(dishApi = get())
    }

}