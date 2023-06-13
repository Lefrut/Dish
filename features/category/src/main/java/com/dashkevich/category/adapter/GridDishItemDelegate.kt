package com.dashkevich.category.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dashkevich.category.databinding.GridDishesItemBinding
import com.dashkevich.util.common.AdapterItemDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class GridDishItemDelegate(
    val dishes: List<DishItemDelegate>
) : AdapterItemDelegate {
    override fun id(): Any = dishes

    override fun content(): Any = this

    override fun hashCode(): Int {
        return dishes.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as GridDishItemDelegate

        if (dishes != other.dishes) return false

        return true
    }

}

fun gridDishAdapterDelegates(
    onClick: (GridDishItemDelegate) -> Unit,
    dishAdapter: ListDelegationAdapter<List<AdapterItemDelegate>>,
    decorations: List<RecyclerView.ItemDecoration> = emptyList()
) =
    adapterDelegateViewBinding<GridDishItemDelegate, AdapterItemDelegate, GridDishesItemBinding>(
        viewBinding = { layoutInflater, root ->
            GridDishesItemBinding.inflate(layoutInflater, root, false)
        },
        on = { item, _, _ ->
            item is GridDishItemDelegate
        }
    ) {
        bind {
            val tegsRv = binding.dishesRv
            dishAdapter.items = item.dishes
            with(tegsRv) {
                adapter = dishAdapter
                layoutManager = GridLayoutManager(context, 3)
                setOnClickListener {
                    onClick(item)
                }
                decorations.forEach{ decoration ->
                    addItemDecoration(decoration)
                }
            }
        }
    }