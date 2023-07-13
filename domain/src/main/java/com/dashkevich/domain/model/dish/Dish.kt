package com.dashkevich.domain.model.dish


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
) {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Dish

        if (description != other.description) return false
        if (id != other.id) return false
        if (imageUrl != other.imageUrl) return false
        if (name != other.name) return false
        if (price != other.price) return false
        if (tegs != other.tegs) return false
        if (weight != other.weight) return false

        return true
    }

    override fun hashCode(): Int {
        var result = description.hashCode()
        result = 31 * result + id
        result = 31 * result + name.hashCode()
        result = 31 * result + price
        result = 31 * result + tegs.hashCode()
        result = 31 * result + weight
        return result
    }
}