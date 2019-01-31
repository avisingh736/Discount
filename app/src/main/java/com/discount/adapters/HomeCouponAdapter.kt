package com.discount.adapters

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.discount.R
import com.discount.app.config.Constants
import com.discount.models.Coupon
import com.discount.views.ui.activities.CouponDetailActivity
import com.discount.views.ui.activities.HomeActivity

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class HomeCouponAdapter(var mContext: Context, var coupons: MutableList<Coupon>): RecyclerView.Adapter<HomeCouponAdapter.CouponViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item_coupon_view,parent,false)
        return CouponViewHolder(view)
    }

    override fun getItemCount(): Int {
        return coupons.size
    }

    override fun onBindViewHolder(holder: CouponViewHolder, position: Int) {
        Glide.with(mContext).load(coupons[position].couponImage).into(holder.ivCouponImage)
        holder.tvCouponTitle.text = coupons[position].title
        holder.tvCouponDescription.text = coupons[position].description
    }


    inner class CouponViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        var ivCouponImage = itemView.findViewById<ImageView>(R.id.ivCouponImage)!!
        var tvCouponTitle = itemView.findViewById<TextView>(R.id.tvCouponTitle)!!
        var tvCouponDescription = itemView.findViewById<TextView>(R.id.tvCouponDescription)!!

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val bundle = Bundle()
            bundle.putSerializable(Constants.KEY_COUPON_EXTRA,coupons[adapterPosition])
            (mContext as HomeActivity).navigateTo(CouponDetailActivity::class.java,bundle)
        }
    }
}