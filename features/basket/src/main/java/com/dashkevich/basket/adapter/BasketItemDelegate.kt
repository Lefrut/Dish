package com.dashkevich.basket.adapter

import coil.load
import com.dashkevich.basket.databinding.BasketItemBinding
import com.dashkevich.domain.model.dish.Dish
import com.dashkevich.util.common.AdapterItemDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class BasketItemDelegate(val dish: Dish, val countDish: Int) : AdapterItemDelegate {
    override fun id(): Any = dish.id

    override fun content(): Any = this

    override fun hashCode(): Int {
        return dish.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BasketItemDelegate

        if (dish != other.dish) return false

        return true
    }
}

fun basketAdapterDelegates(
    itemClickedListener: (BasketItemDelegate) -> Unit,
    onPlus: (Int, Int) -> Unit,
    onMinus: (Int) -> Unit
) =
    adapterDelegateViewBinding<BasketItemDelegate, AdapterItemDelegate, BasketItemBinding>(
        viewBinding = { layoutInflater, root ->
            BasketItemBinding.inflate(layoutInflater, root, false)
        },
        on = { item, _, _ ->
            item is BasketItemDelegate
        }
    ) {
        bind {
            val dish = item.dish
            with(binding) {
                root.setOnClickListener {
                    itemClickedListener(item)
                }
                basketPlus.setOnClickListener {
                    onPlus(dish.id, dish.price)
                }
                basketMinus.setOnClickListener {
                    onMinus(dish.id)
                }
                basketCounter.text = item.countDish.toString()
                basketImage.load(dish.imageUrl)
                basketTitle.text = dish.name
                basketPrice.text = getString(com.dashkevich.ui.R.string.ru_currency, dish.price)
                basketWeight.text = getString(com.dashkevich.ui.R.string.weight_product_dialog,dish.weight)

            }
        }
    }