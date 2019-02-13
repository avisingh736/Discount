package com.discount.presenters

import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.models.SubscriptionResponse
import com.discount.views.DiscountView
import com.discount.views.ui.activities.SubscriptionActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Avinash Kumar on 29/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class SubscriptionPresenter(var mDiscountView: DiscountView?) {
    private val TAG = SubscriptionPresenter::class.java.simpleName


    fun getSubscriptionList() {
        mDiscountView?.progress(true)
        Discount.getApis().getSubscriptionPlans(Discount.getSession().authToken).enqueue(object : Callback<SubscriptionResponse>{
            override fun onResponse(call: Call<SubscriptionResponse>, response: Response<SubscriptionResponse>) {
                mDiscountView?.progress(false)
                if (response.isSuccessful) {
                    if (response.body()?.status!!.equals(Constants.KEY_SUCCESS,false)) {
                        val plans = response.body()?.planList!!
                        (mDiscountView as SubscriptionActivity).onSuccessPlanList(plans.toMutableList())
                    } else {
                        mDiscountView?.onErrorOrInvalid(response.body()?.message!!)
                    }
                } else {
                    mDiscountView?.onErrorOrInvalid("${response.message()} ${response.code()}")
                }
            }

            override fun onFailure(call: Call<SubscriptionResponse>, t: Throwable) {
                mDiscountView?.progress(false)
                mDiscountView?.onErrorOrInvalid(t.localizedMessage)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }

    fun onDestroy() {
        mDiscountView = null
    }

}