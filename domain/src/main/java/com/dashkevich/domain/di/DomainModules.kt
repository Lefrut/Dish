package com.dashkevich.domain.di

import com.dashkevich.domain.use_case.LoadAllTegs
import com.dashkevich.domain.use_case.LoadBasketContentUseCase
import com.dashkevich.domain.use_case.LoadCategoriesUseCase
import com.dashkevich.domain.use_case.LoadDishesByTags
import org.koin.dsl.module

val domainModules = module {

    single {
        LoadCategoriesUseCase(get())
    }
    single {
        LoadBasketContentUseCase(get())
    }
    single {
        LoadDishesByTags(get())
    }
    single{
        LoadAllTegs(get())
    }

}