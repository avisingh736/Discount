package com.discount.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Avinash Kumar on 21/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
data class University(
    @SerializedName("university_name") var universityName: String,
    @SerializedName("created_on") var createdOn: String,
    @SerializedName("updated_on") var updatedOn: String
): Serializable