package com.discount.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.discount.R
import com.discount.app.utils.listeners.CountryListener
import com.discount.models.Country
import com.discount.views.ui.activities.CouponActivity
import com.discount.views.ui.activities.CouponDetailActivity

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class CountriesAdapter(var mContext: Context,var countries: MutableList<Country>):
    RecyclerView.Adapter<CountriesAdapter.CountryViewHolder>(), Filterable {
    var filteredCountries = mutableListOf<Country>()
    val mListener: CountryListener

    init {
        filteredCountries = countries
        mListener = mContext as CountryListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.single_item_country_view,parent,false)
        return CountryViewHolder(view)
    }

    override fun getItemCount(): Int {
        return filteredCountries.size
    }

    override fun onBindViewHolder(holder: CountryViewHolder, position: Int) {
        Glide.with(mContext).load(filteredCountries[position].flag).into(holder.ivCountryImage)
        holder.tvCountryTitle.text = filteredCountries[position].name
        holder.tvCountryCode.text = "+${filteredCountries[position].phoneCode}"
    }


    inner class CountryViewHolder(itemView: View): RecyclerView.ViewHolder(itemView),View.OnClickListener {
        val ivCountryImage = itemView.findViewById<ImageView>(R.id.ivCountryImage)!!
        val tvCountryTitle = itemView.findViewById<TextView>(R.id.tvCountryTitle)!!
        val tvCountryCode = itemView.findViewById<TextView>(R.id.tvCountryCode)!!

        init {
            itemView.setOnClickListener(this@CountryViewHolder)
        }

        override fun onClick(view: View?) {
            mListener.onCountrySelected(filteredCountries[adapterPosition])
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val query = charSequence.toString()
                filteredCountries = if (query.isEmpty()) {
                    countries
                } else {
                    val queryList = mutableListOf<Country>()
                    for (country in countries) {
                        if (country.name.contains(query,true)) {
                            queryList.add(country)
                        }
                    }
                    queryList
                }

                return FilterResults().apply {
                    values = filteredCountries
                    count = filteredCountries.size
                }
            }

            override fun publishResults(charSequence: CharSequence?, result: FilterResults?) {
                filteredCountries = result?.values as MutableList<Country>
                notifyDataSetChanged()
            }
        }
    }
}