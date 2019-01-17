package com.discount.app.utils

import android.graphics.Rect
import android.support.v7.widget.RecyclerView
import android.view.View

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class GridItemDecoration(var spanCount: Int): RecyclerView.ItemDecoration() {
    private val TAG = GridItemDecoration::class.java.simpleName

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)
        outRect.top = 16
        outRect.left = 16
        outRect.right = 0
        outRect.bottom = 0

        if (parent.getChildAdapterPosition(view)%spanCount != 0) {
            MyLog.d(TAG,parent.getChildAdapterPosition(view).toString()+" % $spanCount != 0")
            outRect.right = 16
        }

        if (parent.getChildAdapterPosition(view) > parent.adapter?.itemCount!!.minus(spanCount+1)) {
            outRect.bottom = 16
        }

        /*if(parent.getChildAdapterPosition(view) < spanCount) {
            outRect.top = -200
        }*/
    }
}