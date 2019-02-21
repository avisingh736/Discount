package com.discount.presenters

import android.content.Context
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.models.BaseResponse
import com.discount.models.CouponInfo
import com.discount.models.CouponInfoResponse
import com.discount.models.ErrorResponse
import com.discount.views.DiscountView
import com.discount.views.ui.activities.CouponDetailActivity
import com.google.gson.Gson
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

                if (response.code() == 400) {
                    val mError = Gson().fromJson<ErrorResponse>(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                    if (mError.responseCode == 300) {
                        Discount.logout(mDiscountView as Context)
                        return
                    }
                }

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

    fun redeemCoupon(couponId: String, storeId: String) {
        mDiscountView?.progress(true)
        Discount.getApis().redeemCoupon(Discount.getSession().authToken,couponId,storeId).enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                mDiscountView?.progress(false)

                if (response.code() == 400) {
                    val mError = Gson().fromJson<ErrorResponse>(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                    if (mError.responseCode == 300) {
                        Discount.logout(mDiscountView as Context)
                        return
                    }
                }

                if(response.isSuccessful) {
                    if (response.body()?.status.equals(Constants.KEY_SUCCESS,true)) {
                        mDiscountView?.onSuccess(response.body()?.message!!)
                    } else {
                        mDiscountView?.onErrorOrInvalid(response.body()?.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                mDiscountView?.progress(false)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }

    fun onDestroy() {
        mDiscountView = null
    }
}