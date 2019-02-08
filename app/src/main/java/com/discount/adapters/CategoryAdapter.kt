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
import com.discount.models.Category
import com.discount.views.ui.activities.CategoryActivity
import com.discount.views.ui.activities.CouponActivity

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class CategoryAdapter(var mContext: Context, var categories: MutableList<Category>): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item_category_view,parent,false);
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return categories.size
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        Glide.with(mContext).load(categories[position].image).into(holder.ivCategoryImage)
        holder.tvCategoryTitle.text = categories[position].categoryName
    }


    inner class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val ivCategoryImage: ImageView = itemView.findViewById(R.id.ivCategoryImage)
        val tvCategoryTitle: TextView = itemView.findViewById(R.id.tvCategoryTitle)

        init {
            itemView.setOnClickListener(this@CategoryViewHolder)
        }
        override fun onClick(view: View?) {
            val bundle = Bundle()
            bundle.putString(Constants.KEY_FROM_WHERE,Constants.KEY_FROM_CATEGORY)
            bundle.putSerializable(Constants.KEY_CATEGORY_EXTRA,categories[adapterPosition])
            (mContext as CategoryActivity).navigateTo(CouponActivity::class.java,bundle)
        }
    }
}