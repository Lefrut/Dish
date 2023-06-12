package com.dashkevich.basket.model

import com.dashkevich.data.api.model.dish.Dish

data class BasketModel(
    val dishes: List<Dish> = emptyList()
)
