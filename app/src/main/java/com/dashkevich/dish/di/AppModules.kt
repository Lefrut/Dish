package com.dashkevich.dish.di

import com.dashkevich.data.di.DataModules
import com.dashkevich.domain.di.DomainModules
import org.koin.dsl.module

val appModule = module {
    includes(DataModules, DomainModules, ViewModelModules)
}