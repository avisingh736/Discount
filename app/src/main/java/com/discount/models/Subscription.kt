package com.discount.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Avinash Kumar on 11/2/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
data class Subscription(
    @SerializedName("subscriptionId") var subscriptionId: String,
    @SerializedName("plan_name") var planName: String,
    @SerializedName("plan_stripe_id") var planStripeId: String,
    @SerializedName("plan_product_stripe_id") var planProductStripeId: String,
    @SerializedName("plan_currency") var planCurrency: String,
    @SerializedName("plan_amount") var planAmount: String,
    @SerializedName("plan_interval") var planInterval: String,
    @SerializedName("plan_interval_count") var planIntervalCount: String,
    @SerializedName("coupon_redeem_for_student") var couponRedeemForStudent: String,
    @SerializedName("coupon_redeem_for_normal_user") var couponRedeemForNormalUser: String,
    @SerializedName("description") var description: String,
    @SerializedName("plan_type") var planType: String,
    @SerializedName("updated_on") var updatedOn: String
): Serializable