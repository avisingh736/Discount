package com.discount.models

import com.google.gson.annotations.SerializedName

/**
 * Created by Avinash Kumar on 21/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
data class StoreResponse(
    @SerializedName("status") var status: String,
    @SerializedName("message") var message: String,
    @SerializedName("result") var result: Result){
    data class Result(
        @SerializedName("total_records") var totalRecords: String,
        @SerializedName("store_list") var storeList: List<Store>
    )
}