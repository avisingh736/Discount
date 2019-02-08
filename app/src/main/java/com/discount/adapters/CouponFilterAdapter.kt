package com.discount.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.discount.R
import com.discount.models.Category

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class CouponFilterAdapter(var mContext: Context, var categories: MutableList<Category>): RecyclerView.Adapter<CouponFilterAdapter.FilterViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item_category_view_coupon_filter,parent,false);
        return FilterViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: FilterViewHolder, position: Int) {
        Glide.with(mContext).load(categories[position].image).into(holder.ivCategoryImage)
        holder.tvCategoryTitle.text = categories[position].categoryName

        if (categories[position].isSelected) {
            holder.ivCategoryCheckbox.setImageResource(R.drawable.active_check_ico)
        } else {
            holder.ivCategoryCheckbox.setImageResource(R.drawable.inactive_check_ico)
        }
    }


    inner class FilterViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener {

        val ivCategoryCheckbox: ImageView = itemView.findViewById(R.id.ivCategoryCheckbox)
        val ivCategoryImage: ImageView = itemView.findViewById(R.id.ivCategoryImage)
        val tvCategoryTitle: TextView = itemView.findViewById(R.id.tvCategoryTitle)

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
                    categories[adapterPosition].isSelected = isSelected
                } else {
                    setImageResource(R.drawable.active_check_ico)
                    isSelected = true
                    categories[adapterPosition].isSelected = isSelected
                }
            }
        }
    }
}