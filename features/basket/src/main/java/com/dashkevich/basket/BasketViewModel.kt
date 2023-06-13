package com.dashkevich.basket

import androidx.lifecycle.viewModelScope
import com.dashkevich.basket.model.BasketModel
import com.dashkevich.domain.repository.DishApiRepository
import com.dashkevich.util.basket.Basket
import com.dashkevich.util.common.BaseViewModel
import com.dashkevich.util.resultHandler
import kotlinx.coroutines.launch

class BasketViewModel(private val dishApiRepository: DishApiRepository) :
    BaseViewModel<BasketModel>() {
    override fun setModel(): BasketModel = BasketModel()

    fun getProductInBasket() = viewModelScope.launch {
        val basketProducts = Basket.getProducts().keys
        dishApiRepository.getBasketDishes(basketProducts.toList()).resultHandler(
            onLoading = {},
            onSuccess = { dishes ->
                setState {
                    copy(
                        basketDishes = dishes.filter { dish ->
                            Basket.getProducts().forEach {
                                if (it.key == dish.id) {
                                    if (it.value > 0) return@filter true
                                }
                            }
                            return@filter false
                        },
                        dishesCount = Basket.getProducts(),
                        price = Basket.getPrice()
                    )
                }
            },
            onEmptyResult = {},
            onError = {}
        )
    }

    fun reduceDish(id: Int) {
        Basket.reduceProduct(id)
        updateBasketDishes(id)
    }

    fun addDish(id: Int, price: Int) {
        Basket.addProduct(id, price)
        updateBasketDishes(id)
    }

    private fun updateBasketDishes(id: Int) {
        val mutableDishes = viewState.value.basketDishes.toMutableList()
        if (Basket.getProducts()[id] == 0) {
            val deletedDish = mutableDishes.first { it.id == id }
            mutableDishes.remove(deletedDish)
        }
        setState {
            copy(
                basketDishes = mutableDishes.toList(),
                dishesCount = Basket.getProducts().toMap(),
                price = Basket.getPrice()
            )
        }
    }
}