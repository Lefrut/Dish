package com.dashkevich.basket

import androidx.lifecycle.viewModelScope
import com.dashkevich.basket.model.BasketModel
import com.dashkevich.domain.repository.DishApiRepository
import com.dashkevich.util.BaseViewModel
import com.dashkevich.util.Basket
import com.dashkevich.util.resultHandler
import kotlinx.coroutines.launch

class BasketViewModel(private val dishApiRepository: DishApiRepository): BaseViewModel<BasketModel>() {
    override fun setModel(): BasketModel = BasketModel()

    init {
        getProductInBasket()
    }

    private fun getProductInBasket()= viewModelScope.launch{
        val idProducts = Basket.getProducts().keys
        dishApiRepository.getDishes().resultHandler(
            onLoading = {

            },
            onSuccess = { dishes ->
                val newDishes = dishes.dishes.filter { dish ->
                    for(id in idProducts){
                        if(dish.id == id) return@filter true
                    }
                    return@filter false
                }
                setState {
                    copy(basketDishes = newDishes, dishesCount = Basket.getProducts().toMap())
                }
            },
            onEmptyResult = {

            },
            onError = {

            }
        )
    }

    fun updateDishesCount(){
        setState {
            copy(dishesCount = Basket.getProducts().toMap(), price = Basket.getPrice())
        }
    }
}