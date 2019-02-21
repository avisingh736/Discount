package com.discount.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Avinash Kumar on 20/2/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
data class ErrorResponse(
    @SerializedName("message") var message: String,
    @SerializedName("auth_token") var authToken: String,
    @SerializedName("responseCode") var responseCode: Int,
    @SerializedName("isActive") var isActive: Int
)