package com.dashkevich.basket.model

import com.dashkevich.data.api.model.dish.Dish
import com.dashkevich.util.Basket

data class BasketModel(
    val basketDishes: List<Dish> = emptyList(),
    val dishesCount: Map<Int, Int> = mapOf(),
    val price: Int = Basket.getPrice()
)
