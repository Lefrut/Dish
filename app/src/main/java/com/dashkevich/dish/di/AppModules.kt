package com.dashkevich.dish.di

import com.dashkevich.data.di.dataModules
import com.dashkevich.domain.di.domainModules
import org.koin.dsl.module

val appModule = module {
    includes(dataModules, domainModules, viewModelModules)
}