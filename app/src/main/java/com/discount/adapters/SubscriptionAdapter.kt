package com.discount.adapters

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.discount.R
import com.discount.models.Subscription
import com.discount.views.ui.activities.SubscriptionDetailActivity

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class SubscriptionAdapter(var mContext: Context,var plans: MutableList<Subscription>): RecyclerView.Adapter<SubscriptionAdapter.CouponViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CouponViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item_subscription_view,parent,false)
        return CouponViewHolder(view)
    }

    override fun getItemCount(): Int {
        return plans.size
    }

    override fun onBindViewHolder(holder: CouponViewHolder, position: Int) {
        holder.tvPlanName.text = plans[position].planName
        holder.tvPlanAmount.text = "${plans[position].planAmount}/"
        holder.tvPlanInterval.text = "${plans[position].planIntervalCount} ${plans[position].planInterval}"

        //ToDo: Make a logic hare to differentiate normal and students
        holder.tvPlanCouponRedeemPoints.text = plans[position].couponRedeemForNormalUser

        holder.tvPlanDescription.text = plans[position].description
    }


    inner class CouponViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val tvPlanName = itemView.findViewById<TextView>(R.id.tvPlanName)!!
        val tvPlanAmount = itemView.findViewById<TextView>(R.id.tvPlanAmount)!!
        val tvPlanInterval = itemView.findViewById<TextView>(R.id.tvPlanInterval)!!
        val tvPlanCouponRedeemPoints = itemView.findViewById<TextView>(R.id.tvPlanCouponRedeemPoints)!!
        val tvPlanDescription = itemView.findViewById<TextView>(R.id.tvPlanDescription)!!
        val btnSubscribe = itemView.findViewById<Button>(R.id.btnSubscribe)!!

        init {
            itemView.setOnClickListener { mContext.startActivity(Intent(mContext,SubscriptionDetailActivity::class.java))}
            btnSubscribe.setOnClickListener(this)
        }

        override fun onClick(v: View?) {

        }
    }
}