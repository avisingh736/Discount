package com.discount.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Avinash Kumar on 29/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
data class Coupon(
    @SerializedName("couponId") var couponId: String,
    @SerializedName("voucher_code") var voucherCode: String,
    @SerializedName("title") var title: String,
    @SerializedName("valid_date") var validDate: String,
    @SerializedName("description") var description: String,
    @SerializedName("coupon_image") var image: String,
    @SerializedName("user_id") var userId: String,
    @SerializedName("created_on") var createdOn: String,
    @SerializedName("updated_on") var updatedOn: String,
    @SerializedName("latitude") var latitude: String,
    @SerializedName("longitude") var longitude: String,
    @SerializedName("category") var category: String,
    @SerializedName("image") var couponImage: String,
    @SerializedName("couponImage") var storeCouponImage: String,
    @SerializedName("distance_in_km") var distanceInKm: String
): Serializable