package com.dashkevich.category.model

import com.dashkevich.data.api.model.dish.Dish
import com.dashkevich.util.OperationState

data class CategoryModel(
    val tegs: List<Pair<String, Boolean>> = emptyList(),
    val tegsState: OperationState = OperationState.None,
    val dishes: List<Dish> = emptyList(),
    val dishesState: OperationState = OperationState.None
)