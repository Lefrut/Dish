package com.dashkevich.home.model

import com.dashkevich.data.api.model.category.DCategory
import com.dashkevich.util.common.OperationState

data class HomeModel(
    val categories: List<DCategory> = emptyList(),
    val categoriesState: OperationState = OperationState.None
)

