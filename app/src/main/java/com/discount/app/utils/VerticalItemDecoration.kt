package com.discount.app.utils

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class VerticalItemDecoration: RecyclerView.ItemDecoration() {

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 16
        outRect.left = 16
        outRect.right = 16
        outRect.bottom = 0

        if (parent.getChildAdapterPosition(view) == parent.adapter?.itemCount!!.minus(1)) {
            outRect.bottom = 16
        }
    }
}