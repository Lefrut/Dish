package com.dashkevich.domain.model.category


import com.google.gson.annotations.SerializedName

data class DCategories(
    @SerializedName("сategories")
    val categories: List<DCategory>
)