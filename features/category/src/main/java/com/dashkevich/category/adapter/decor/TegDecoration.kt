package com.dashkevich.category.adapter.decor

import android.graphics.Rect
import android.os.Build
import android.util.Log
import android.view.View
import androidx.core.view.get
import androidx.core.view.size
import androidx.recyclerview.widget.RecyclerView
import com.dashkevich.category.adapter.TegItemDelegate
import com.dashkevich.category.databinding.TegItemBinding
import com.dashkevich.util.toDp
import com.dashkevich.util.toPx
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder

class TegDecoration() : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        with(outRect) {
            val adapter = parent.adapter ?: return
            val currentPosition =
                parent.getChildAdapterPosition(view)
                    .takeIf { it != RecyclerView.NO_POSITION } ?: return
            val vh = parent.getChildViewHolder(view)
            if (vh is AdapterDelegateViewBindingViewHolder<*, *>) {
                if (vh.item !is TegItemDelegate) return
            }

            top = 8
            bottom = 8
            right = 0
            when (currentPosition) {
                0 -> {
                    left = 16
                }
                adapter.itemCount - 1 -> {
                    left = 8
                    right = 16
                }
                else -> {
                    left = 8
                }
            }
            outRect.set(left.toPx, top.toPx, right.toPx, bottom.toPx)
        }
    }

}