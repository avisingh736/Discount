package com.discount.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Avinash Kumar on 29/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
data class Store(
    @SerializedName("storeId") var storeId: String,
    @SerializedName("full_name") var fullName: String,
    @SerializedName("user_role") var userRole: String,
    @SerializedName("email") var email: String,
    @SerializedName("address") var address: String,
    @SerializedName("latitude") var latitude: String,
    @SerializedName("longitude") var longitude: String,
    @SerializedName("type") var type: String,
    @SerializedName("plan") var plan: String,
    @SerializedName("status") var status: String,
    @SerializedName("created_on") var createdOn: String,
    @SerializedName("updated_on") var updatedOn: String,
    @SerializedName("storeImage") var storeImage: String
): Serializable