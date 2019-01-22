package com.discount.adapters

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.discount.R
import com.discount.models.University

/**
 * Created by Avinash Kumar on 21/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class UniversityAdapter: ArrayAdapter<University>{
    val mUniversities: MutableList<University>?
    val mInflater: LayoutInflater?
    constructor(context: Context,universities: MutableList<University>) :
            super(context,R.layout.single_item_university_view,universities) {
        this.mUniversities = universities
        this.mInflater = LayoutInflater.from(context)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val mView = super.getDropDownView(position, convertView, parent)
        val tvUniversityName = mView.findViewById<TextView>(R.id.tvUniversityName)
        tvUniversityName?.text = mUniversities?.get(position)?.universityName
        if (position == 0) {
            tvUniversityName?.text = context.getString(R.string.select_university)
            tvUniversityName.setTextColor(ContextCompat.getColor(parent.context,R.color.colorHintText))
        } else {
            tvUniversityName.setTextColor(ContextCompat.getColor(parent.context,R.color.colorText))
        }
        return mView
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val mView = super.getView(position, convertView, parent)
        val tvUniversityName = mView.findViewById<TextView>(R.id.tvUniversityName)
        tvUniversityName.setPadding(0,0,0,0)
        tvUniversityName?.text = mUniversities?.get(position)?.universityName
        if (position == 0) {
            tvUniversityName?.text = context.getString(R.string.university_name)
            tvUniversityName.setTextColor(ContextCompat.getColor(parent.context,R.color.colorHintText))
        } else {
            tvUniversityName.setTextColor(ContextCompat.getColor(parent.context,R.color.colorText))
        }
        return mView
    }

}