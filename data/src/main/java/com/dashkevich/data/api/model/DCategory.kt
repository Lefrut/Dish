package com.dashkevich.data.api.model


import com.google.gson.annotations.SerializedName

data class DCategory(
    @SerializedName("id")
    val id: Int,
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("name")
    val name: String
)