package com.discount.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Avinash Kumar on 21/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
data class SubscriptionResponse(
    @SerializedName("status") var status: String,
    @SerializedName("message") var message: String,
    @SerializedName("is_subscribe") var isSubscribe: String,
    @SerializedName("plan_list") var planList: List<Subscription>)