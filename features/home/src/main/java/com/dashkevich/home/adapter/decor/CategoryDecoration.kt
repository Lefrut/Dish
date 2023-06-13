package com.dashkevich.home.adapter.decor

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dashkevich.home.adapter.CategoryItemDelegate
import com.dashkevich.util.toPx
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder

class CategoryDecoration() : RecyclerView.ItemDecoration() {

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
                if (vh.item !is CategoryItemDelegate) return
            }

            top = 8
            bottom = 0
            right = 0
            left = 0
            if(currentPosition == adapter.itemCount - 1) bottom = 8
            val currentNumber = currentPosition + 1
            outRect.set(left.toPx, top.toPx, right.toPx, bottom.toPx)
        }
    }

}