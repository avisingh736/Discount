package com.discount.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.discount.R

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class CouponFilterAdapter(var mContext: Context): RecyclerView.Adapter<CouponFilterAdapter.FilterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item_category_view_coupon_filter,parent,false);
        return FilterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        //ToDo: To change body of created functions use File | Settings | File Templates.
    }


    class FilterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener {

        private val ivCategoryCheckbox: ImageView? = itemView.findViewById(R.id.ivCategoryCheckbox)

        init {

            ivCategoryCheckbox?.apply {
                setOnClickListener(this@FilterViewHolder)
                ivCategoryCheckbox.isSelected = false
            }
        }

        override fun onClick(v: View?) {
            ivCategoryCheckbox?.run {
                if (isSelected) {
                    setImageResource(R.drawable.inactive_check_ico)
                    isSelected = false
                } else {
                    setImageResource(R.drawable.active_check_ico)
                    isSelected = true
                }
            }
        }
    }
}