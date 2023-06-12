package com.dashkevich.category.adapter.decor

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dashkevich.category.adapter.DishItemDelegate
import com.dashkevich.util.toPx
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder


class DishDecoration() : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            val adapter = parent.adapter ?: return
            val currentPosition =
                parent.getChildLayoutPosition(view)
                    .takeIf { it != RecyclerView.NO_POSITION } ?: return
            val vh = parent.getChildViewHolder(view)
            if (vh is AdapterDelegateViewBindingViewHolder<*, *>) {
                if (vh.item !is DishItemDelegate) return
            }

            top = 8
            bottom = 14
            right = 4
            left = 4
            val currentNumber = currentPosition + 1
            outRect.set(left.toPx, top.toPx, right.toPx, bottom.toPx)
        }
    }

}