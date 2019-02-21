package com.discount.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Avinash Kumar on 29/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
data class Content(
    @SerializedName("term_and_condition") var termAndCondition: String,
    @SerializedName("policy") var policy: String,
    @SerializedName("about") var about: String
): Serializable