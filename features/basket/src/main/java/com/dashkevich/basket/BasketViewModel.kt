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
        val basketProducts = Basket.getProducts()
        dishApiRepository.getDishes().resultHandler(
            onLoading = {

            },
            onSuccess = { dishes ->
                //Todo repository
                val basketDishes = dishes.dishes.filter { dish ->
                    for (product in basketProducts) {
                        if (product.key == dish.id && product.value != 0) return@filter true
                    }
                    return@filter false
                }
                setState {
                    copy(
                        basketDishes = basketDishes,
                        dishesCount = basketProducts,
                        price = Basket.getPrice()
                    )
                }
            },
            onEmptyResult = {

            },
            onError = {

            }
        )
    }

    fun updateDishesCount(id: Int) {
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