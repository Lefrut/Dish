package com.dashkevich.util.basket

object Basket {

    private val products: MutableMap<Int, Int> = mutableMapOf()
    private val productsPrice: MutableMap<Int, Int> = mutableMapOf()

    fun getProducts() = products.toMap()

    fun reduceProduct(id: Int): Boolean {
        val product = products[id] ?: return false
        products[id] = products[id]!! - 1
        if (product <= 0) {
            products.remove(id)
            productsPrice.remove(id)
        }
        return true
    }

    fun addProduct(id: Int, price: Int = 0) {
        val product = products[id]
        if (product == null) {
            products[id] = 1
            productsPrice[id] = price
        } else {
            products[id] = products[id]!! + 1
        }

    }

    fun getPrice(): Int {
        var result = 0
        productsPrice.forEach { productPrice ->
            val count = products[productPrice.key] ?: 1
            result += count * productPrice.value
        }
        return result
    }

    fun isEmpty(): Boolean = products.isEmpty()

    override fun toString(): String {
        return "${products.keys}"
    }

}