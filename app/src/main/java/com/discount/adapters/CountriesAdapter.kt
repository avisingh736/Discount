package com.discount.adapters

import android.content.Context
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
class CountriesAdapter(var mContext: Context): RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item_country_view,parent,false);
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 10
    }

    override fun onBindViewHolder(p0: CountryViewHolder, p1: Int) {
        //ToDo: To change body of created functions use File | Settings | File Templates.
    }


    inner class CountryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}