package com.dashkevich.domain.model.dish


import com.google.gson.annotations.SerializedName

data class Dishes(
    @SerializedName("dishes")
    val dishes: List<Dish>
)