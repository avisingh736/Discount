package com.discount.interactors

import com.discount.app.Discount
import com.discount.models.Coupon
import com.discount.models.CouponResponse
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
    }

    fun getCouponListFromServer(latitude: String, longitude: String, search: String = "", limit: String = "10", offset: String = "0", mListener: OnResponseListener) {
        Discount.getApis().getCouponList(Discount.getSession().authToken,latitude,longitude,search,limit,offset).enqueue(object :
            Callback<CouponResponse>{
            override fun onResponse(call: Call<CouponResponse>, response: Response<CouponResponse>) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onFailure(call: Call<CouponResponse>, t: Throwable) {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }
        })
    }
}