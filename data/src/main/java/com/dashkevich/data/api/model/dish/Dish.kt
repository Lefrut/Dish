package com.dashkevich.data.api.model.dish


import com.google.gson.annotations.SerializedName

data class Dish(
    @SerializedName("description")
    val description: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("price")
    val price: Int,
    @SerializedName("tegs")
    val tegs: List<String>,
    @SerializedName("weight")
    val weight: Int
)