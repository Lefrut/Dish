package com.dashkevich.dish.di

import com.dashkevich.basket.BasketViewModel
import com.dashkevich.category.CategoryViewModel
import com.dashkevich.home.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val ViewModelModules = module {

    viewModel<HomeViewModel> {
        HomeViewModel(get())
    }

    viewModel<CategoryViewModel> {
        CategoryViewModel(get())
    }

    viewModel<BasketViewModel> {
        BasketViewModel(get())
    }

}