package com.dashkevich.home.adapter

import coil.load
import com.dashkevich.home.databinding.CategoriesItemBinding
import com.dashkevich.util.common.AdapterItemDelegate
import com.hannesdorfmann.adapterdelegates4.AdapterDelegate
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class CategoryItemDelegate(
    val id: Int,
    val name: String,
    val imageUrl: String
) : AdapterItemDelegate {
    override fun id(): Any = id

    override fun content(): Any = this

    override fun equals(other: Any?): Boolean {
        if (other is CategoryItemDelegate) {
            return (name == other.name && imageUrl == other.imageUrl && id == other.id)
        }
        return false
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + name.hashCode()
        result = 31 * result + imageUrl.hashCode()
        return result
    }
}

internal fun categoryAdapterDelegates(itemClickedListener : (CategoryItemDelegate) -> Unit)
        : AdapterDelegate<List<AdapterItemDelegate>> {
    return adapterDelegateViewBinding<CategoryItemDelegate, AdapterItemDelegate, CategoriesItemBinding>(
        viewBinding = { layoutInflater, root ->
            CategoriesItemBinding.inflate(layoutInflater, root, false)
        },
        on = { item, _, _ ->
            item is CategoryItemDelegate
        }
    ) {
        binding.root.setOnClickListener {
            itemClickedListener(item)
        }
        bind {
            binding.nameCategory.text = item.name
            binding.imageCategory.load(item.imageUrl)
            binding.root.setOnClickListener {
                itemClickedListener(item)
            }
        }
    }
}