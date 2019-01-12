package com.discount.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Avinash Kumar on 12/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
data class AuthenticationResponse(
    @SerializedName("status") var status: String,
    @SerializedName("message") var message: String,
    @SerializedName("userDetail") var userDetail: UserDetail
)