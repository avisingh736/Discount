package com.discount.views.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.SearchView
import android.widget.TextView
import com.discount.R
import com.discount.adapters.CountriesAdapter
import com.discount.app.config.Constants
import com.discount.app.utils.MyUtils
import com.discount.app.utils.listeners.CountryListener
import com.discount.models.Country
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_country.*

class CountryActivity : AppCompatActivity(), CountryListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        val tvSearch = countrySearchView.findViewById<TextView>(android.support.v7.appcompat.R.id.search_src_text)
        tvSearch.textSize = 14f
        tvSearch.typeface = ResourcesCompat.getFont(this,R.font.poppins_regular)

        ivGoToBack.setOnClickListener { finish() }

        /**
         * Preparing country list and images
         * */
        val countries = Gson().fromJson<String>(MyUtils.loadJSONFromAsset(this,"country_code.json"),object : TypeToken<List<Country>>(){}.type) as List<Country>
        val mFlags = MyUtils.countryFlags
        for ((i, country) in countries.withIndex()) {
            country.flag = mFlags[i]
        }

        rvCountries.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        val mAdapter = CountriesAdapter(this,countries.toMutableList())
        rvCountries.adapter = mAdapter

        countrySearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextChange(query: String?): Boolean {
                mAdapter.filter.filter(query)
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                mAdapter.filter.filter(query)
                return false
            }
        })
    }

    override fun onCountrySelected(country: Country) {
        val mIntent = Intent()
        val mBundle = Bundle()
        mBundle.putSerializable(Constants.KEY_RESULT_EXTRA,country)
        mIntent.putExtra(Constants.KEY_BUNDLE_PARAM,mBundle)
        setResult(Activity.RESULT_OK,mIntent)
        finish()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }

}
