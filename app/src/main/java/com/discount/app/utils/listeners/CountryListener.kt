package com.discount.app.utils.listeners

import com.discount.models.Country

/**
 * Created by Avinash Kumar on 8/2/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
interface CountryListener {
    fun onCountrySelected(country: Country)
}