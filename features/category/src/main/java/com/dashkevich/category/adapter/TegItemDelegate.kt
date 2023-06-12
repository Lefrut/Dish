package com.dashkevich.category.adapter

import android.content.res.ColorStateList
import com.dashkevich.category.R
import com.dashkevich.category.databinding.TegItemBinding
import com.dashkevich.util.AdapterItemDelegate
import com.dashkevich.util.getColorFromAttr
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding

class TegItemDelegate(val teg: String, val checked: Boolean) : AdapterItemDelegate {
    override fun id(): Any = teg

    override fun content(): Any = this

    override fun equals(other: Any?): Boolean {
        if (other is TegItemDelegate) {
            return (teg == other.teg || checked == other.checked)
        }
        return false
    }

    override fun hashCode(): Int {
        var result = teg.hashCode()
        result = 31 * result + checked.hashCode()
        return result
    }

}

fun tegAdapterDelegates(
    itemClickedListener: (TegItemDelegate) -> Unit
) =
    adapterDelegateViewBinding<TegItemDelegate, AdapterItemDelegate, TegItemBinding>(
        viewBinding = { layoutInflater, root ->
            TegItemBinding.inflate(layoutInflater, root, false)
        },
        on = { item, _, _ ->
            item is TegItemDelegate
        }
    ) {
        bind {

            val title = binding.nameTeg
            title.text = item.teg
            val card = binding.root
            card.setOnClickListener {
                itemClickedListener(item)
            }
            if (item.checked) {
                title.setTextColor(
                    context.getColorFromAttr(
                        com.google.android.material.R.attr.colorSurface
                    )
                )
                card.setCardBackgroundColor(context.getColorFromAttr(com.dashkevich.ui.R.attr.buttonPrimary))
            } else {
                title.setTextColor(context.getColorFromAttr(com.google.android.material.R.attr.colorOnSurface))
                card.setCardBackgroundColor(context.getColorFromAttr(com.google.android.material.R.attr.colorPrimary))
            }
        }
    }