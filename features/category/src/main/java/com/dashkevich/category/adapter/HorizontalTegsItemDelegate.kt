package com.dashkevich.category.adapter

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dashkevich.category.databinding.HorizontalTegsItemBinding
import com.dashkevich.util.common.AdapterItemDelegate
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class HorizontalTegsItemDelegate(
    val tegs: List<TegItemDelegate>
) : AdapterItemDelegate {
    override fun id(): Any = tegs

    override fun content(): Any = this

    override fun hashCode(): Int {
        return tegs.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as HorizontalTegsItemDelegate
        if (tegs != other.tegs) return false
        return true
    }
}

fun horizontalTegsAdapterDelegates(
    itemClickedListener: (HorizontalTegsItemDelegate) -> Unit,
    tegsAdapter: ListDelegationAdapter<List<AdapterItemDelegate>>,
    decorations: List<RecyclerView.ItemDecoration> = emptyList()
) =
    adapterDelegateViewBinding<HorizontalTegsItemDelegate, AdapterItemDelegate, HorizontalTegsItemBinding>(
        viewBinding = { layoutInflater, root ->
            HorizontalTegsItemBinding.inflate(layoutInflater, root, false)
        },
        on = { item, _, _ ->
            item is HorizontalTegsItemDelegate
        }
    ) {
        bind {
            val tegsRv = binding.tegsRv
            tegsAdapter.items = item.tegs
            with(tegsRv) {
                adapter = tegsAdapter
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
                setOnClickListener {
                    itemClickedListener(item)
                }
                decorations.forEach{ decoration ->
                    addItemDecoration(decoration)
                }
            }
        }
    }


