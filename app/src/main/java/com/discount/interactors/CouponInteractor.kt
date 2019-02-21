package com.discount.interactors

import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.models.Coupon
import com.discount.models.CouponResponse
import com.discount.models.ErrorResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Avinash Kumar on 29/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class CouponInteractor {
    interface OnResponseListener{
        fun onSuccessCouponList(coupons: MutableList<Coupon>)
        fun onSuccess(msg: String)
        fun progress(flag: Boolean)
        fun onError(msg: String)
        fun logout()
    }
    val TAG = CouponInteractor::class.java.simpleName

    fun getCouponListFromServer(latitude: String, longitude: String, search: String = "", limit: String = "10", offset: String = "0", mListener: OnResponseListener) {
        mListener.progress(true)
        Discount.getApis().getCouponList(Discount.getSession().authToken,latitude,longitude,search,limit,offset).enqueue(object :
            Callback<CouponResponse>{
            override fun onResponse(call: Call<CouponResponse>, response: Response<CouponResponse>) {
                mListener.progress(false)
                MyLog.d(TAG,"Response ${response.isSuccessful}")

                if (response.code() == 400) {
                    val mError = Gson().fromJson<ErrorResponse>(response.errorBody()!!.charStream(),ErrorResponse::class.java)
                    if (mError.responseCode == 300) {
                        mListener.logout()
                        return
                    }
                }

                if (response.isSuccessful) {
                    if (response.body()?.status.equals(Constants.KEY_SUCCESS,false)) {
                        val result: CouponResponse.Result = response.body()?.result!!
                        mListener.onSuccessCouponList(result.couponList.toMutableList())
                    } else {
                        mListener.onError(response.body()?.message!!)
                    }
                } else {
                    mListener.onError("${response.message()} ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CouponResponse>, t: Throwable) {
                mListener.progress(false)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }
}