package com.dashkevich.category.adapter

import android.graphics.drawable.Drawable
import androidx.annotation.DrawableRes
import coil.load
import coil.request.Disposable
import com.dashkevich.category.R
import com.dashkevich.category.databinding.DishItemBinding
import com.dashkevich.category.databinding.TegItemBinding
import com.dashkevich.data.api.model.dish.Dish
import com.dashkevich.util.AdapterItemDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class DishItemDelegate(
    val id: Int,
    val description: String,
    val imageUrl: String,
    val name: String,
    val price: Int,
    val tegs: List<String>,
    val weight: Int
) : AdapterItemDelegate {
    override fun id(): Any = id

    override fun content(): Any = this

    override fun equals(other: Any?): Boolean {
        if (other is DishItemDelegate) {
            return (name == other.name && imageUrl == other.imageUrl
                    && id == other.id && price == other.price
                    && tegs == other.tegs && description == other.description
                    && weight == other.weight)
        }
        return false
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + description.hashCode()
        result = 31 * result + imageUrl.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + price
        result = 31 * result + tegs.hashCode()
        result = 31 * result + weight
        return result
    }
}

fun dishAdapterDelegates(
    itemClickedListener: (DishItemDelegate) -> Unit,
) =
    adapterDelegateViewBinding<DishItemDelegate, AdapterItemDelegate, DishItemBinding>(
        viewBinding = { layoutInflater, root ->
            DishItemBinding.inflate(layoutInflater, root, false)
        },
        on = { item, _, _ ->
            item is DishItemDelegate
        }
    ) {
        bind {
            binding.root.setOnClickListener {
                itemClickedListener(item)
            }
            val isDisposed = binding.imageDish.load(item.imageUrl).isDisposed
            if (isDisposed) binding.imageDish.setImageResource(R.drawable.food_abstarct)
            else if (item.imageUrl == "") binding.imageDish.setImageResource(R.drawable.food_abstarct)
            binding.nameDish.text = item.name
        }
    }


fun Dish.toDishItemDelegate(): DishItemDelegate {
    return DishItemDelegate(
        id = id, description = description,
        imageUrl = imageUrl ?: "", name = name,
        price = price, tegs = tegs, weight = weight
    )
}