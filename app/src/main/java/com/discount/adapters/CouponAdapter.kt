package com.discount.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.discount.R
import com.discount.views.ui.activities.CouponActivity
import com.discount.views.ui.activities.CouponDetailActivity

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class CouponAdapter(var mContext: Context): RecyclerView.Adapter<CouponAdapter.CouponViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item_coupon_view_main,parent,false);
        return CouponViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(p0: CouponViewHolder, p1: Int) {
        //ToDo: To change body of created functions use File | Settings | File Templates.
    }


    inner class CouponViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener {
        init {
            itemView.setOnClickListener(this@CouponViewHolder)
        }
        override fun onClick(view: View?) {
            (mContext as CouponActivity).navigateTo(CouponDetailActivity::class.java)
        }
    }
}