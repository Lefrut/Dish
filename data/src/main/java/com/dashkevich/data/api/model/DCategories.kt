package com.dashkevich.data.api.model


import com.google.gson.annotations.SerializedName

data class DCategories(
    @SerializedName("сategories")
    val categories: List<DCategory>
)