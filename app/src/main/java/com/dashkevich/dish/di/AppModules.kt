package com.dashkevich.dish.di

import com.dashkevich.data.di.DataModules
import org.koin.dsl.module

val appModule = module {
    includes(DataModules)
}