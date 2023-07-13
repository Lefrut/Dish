package com.dashkevich.domain.use_case

import com.dashkevich.domain.model.dish.Dish
import com.dashkevich.domain.repository.DishApiRepository
import com.dashkevich.util.coroutineCatching
import kotlinx.coroutines.Dispatchers

class LoadDishesByTags(private val dishApiRepository: DishApiRepository) : AsyncUseCase() {

    suspend operator fun invoke(tegs: List<String>): Result<List<Dish>> =
        coroutineCatching(dispatcher) {
            val resultDishes = mutableListOf<Dish>()
            val dishes = dishApiRepository.getDishes().dishes
            if (tegs.isEmpty()) {
                return@coroutineCatching emptyList<Dish>()
            }
            
            with(Dispatchers.Default) {
                resultDishes.searchAndAddDishes(dishes, tegs)
            }
            return@coroutineCatching resultDishes
        }

    private fun MutableCollection<Dish>.searchAndAddDishes(
        dishes: Collection<Dish>, tegs: List<String>
    ) {
        dishes.forEach { dish ->
            run {
                dish.tegs.forEach { dishTeg ->
                    tegs.forEach { teg ->
                        if (dishTeg == teg) {
                            add(dish)
                            return@run
                        }
                    }
                }
            }
        }
        //TODO - Улучшить реализацию
    }


}