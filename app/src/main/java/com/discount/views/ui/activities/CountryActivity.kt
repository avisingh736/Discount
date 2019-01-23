package com.discount.views.ui.activities

import android.os.Bundle
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.widget.TextView
import com.discount.R
import com.discount.adapters.CountriesAdapter
import kotlinx.android.synthetic.main.activity_country.*

class CountryActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_country)

        val tvSearch = countrySearchView.findViewById<TextView>(android.support.v7.appcompat.R.id.search_src_text)
        tvSearch.textSize = 14f
        tvSearch.typeface = ResourcesCompat.getFont(this,R.font.poppins_regular)

        ivGoToBack.setOnClickListener { finish() }

        rvCountries.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        rvCountries.adapter = CountriesAdapter(this)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }

}
