package com.discount.presenters

import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.models.CouponInfo
import com.discount.models.CouponInfoResponse
import com.discount.views.DiscountView
import com.discount.views.ui.activities.CouponDetailActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Avinash Kumar on 29/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class CouponDetailPresenter(var mDiscountView: DiscountView?) {
    private var TAG = CouponDetailPresenter::class.java.simpleName

    fun getCouponInfo(couponId: String) {
        mDiscountView?.progress(true)
        Discount.getApis().getCouponInfo(Discount.getSession().authToken,couponId).enqueue(object : Callback<CouponInfoResponse>{
            override fun onResponse(call: Call<CouponInfoResponse>, response: Response<CouponInfoResponse>) {
                mDiscountView?.progress(false)
                MyLog.i(TAG,"onResponse ${response.isSuccessful}")
                if(response.isSuccessful) {
                    if (response.body()?.status.equals(Constants.KEY_SUCCESS,false)) {
                        val couponInfo: CouponInfo = response.body()?.couponInfo!!
                        (mDiscountView as CouponDetailActivity).onCouponInfo(couponInfo)
                    } else {
                        mDiscountView?.onErrorOrInvalid(response.body()?.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<CouponInfoResponse>, t: Throwable) {
                mDiscountView?.progress(false)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }

    fun onDestroy() {
        mDiscountView = null
    }
}