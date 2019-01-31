package com.discount.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Avinash Kumar on 21/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
data class CouponInfoResponse(
    @SerializedName("status") var status: String,
    @SerializedName("message") var message: String,
    @SerializedName("coupon_info") var couponInfo: CouponInfo)