package com.discount.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Avinash Kumar on 8/2/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
data class Country(
    @SerializedName("country_name") val name: String,
    @SerializedName("code")val code: String,
    @SerializedName("phone_code")val phoneCode: Int,
    var flag: Int
): Serializable