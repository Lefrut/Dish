package com.dashkevich.home.model

import com.dashkevich.data.api.model.DCategory
import com.dashkevich.util.OperationState

data class HomeModel(
    val categories: List<DCategory> = emptyList(),
    val categoriesState: OperationState = OperationState.None
)

