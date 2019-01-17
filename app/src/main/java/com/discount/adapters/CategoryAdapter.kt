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
class CategoryAdapter(var mContext: Context): RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item_category_view,parent,false);
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return 20
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        //ToDo: To change body of created functions use File | Settings | File Templates.
    }


    class CategoryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)
}