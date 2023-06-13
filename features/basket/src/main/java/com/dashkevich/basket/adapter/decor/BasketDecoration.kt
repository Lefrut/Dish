package com.dashkevich.basket.adapter.decor

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.dashkevich.basket.adapter.BasketItemDelegate
import com.dashkevich.util.toPx
import com.hannesdorfmann.adapterdelegates4.dsl.AdapterDelegateViewBindingViewHolder


class BasketDecoration() : RecyclerView.ItemDecoration() {

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
                if (vh.item !is BasketItemDelegate) return
            }

            top = 8
            bottom = 8
            right = 16
            left = 16
            if(currentPosition==0){
                top = 16
            }else if(currentPosition == adapter.itemCount - 1){
                bottom = 16
            }
            outRect.set(left.toPx, top.toPx, right.toPx, bottom.toPx)
        }
    }

}