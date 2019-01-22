package com.discount.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.discount.R

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class StoreCouponAdapter(var mContext: Context): RecyclerView.Adapter<StoreCouponAdapter.CouponViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item_coupon_view_store,parent,false);
        return CouponViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: CouponViewHolder, position: Int) {
        //ToDo: To change body of created functions use File | Settings | File Templates.
    }


    class CouponViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}