package com.discount.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Avinash Kumar on 29/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
data class Category(
    @SerializedName("categoryId") var categoryId: String,
    @SerializedName("category_name") var categoryName: String,
    @SerializedName("created_on") var createdOn: String,
    @SerializedName("image") var image: String,
    var isSelected: Boolean = false
): Serializable