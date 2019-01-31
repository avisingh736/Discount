package com.discount.interactors

import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.models.Coupon
import com.discount.models.CouponResponse
import com.discount.models.Store
import com.discount.models.StoreResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Avinash Kumar on 29/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class StoreInteractor {
    interface OnResponseListener{
        fun onSuccessStoreList(stores: MutableList<Store>)
        fun onSuccess(msg: String)
        fun progress(flag: Boolean)
        fun onError(msg: String)
    }
    val TAG: String = StoreInteractor::class.java.simpleName

    fun getStoreListFromServer(mListener: OnResponseListener) {
        Discount.getApis().getStores(Discount.getSession().authToken).enqueue(object :
            Callback<StoreResponse>{
            override fun onResponse(call: Call<StoreResponse>, response: Response<StoreResponse>) {
                if (response.isSuccessful) {
                    if (response.body()?.status!!.equals(Constants.KEY_SUCCESS,false)) {
                        val stores = response.body()?.result?.storeList!!
                        mListener.onSuccessStoreList(stores.toMutableList())
                    } else {
                        mListener.onError(response.body()?.message!!)
                    }
                } else {
                    mListener.onError("${response.message()} ${response.code()}")
                }
            }

            override fun onFailure(call: Call<StoreResponse>, t: Throwable) {
                mListener.onError(t.localizedMessage)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }
}