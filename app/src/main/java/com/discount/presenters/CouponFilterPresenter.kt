package com.discount.presenters

import android.content.Context
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.models.CategoryResponse
import com.discount.models.ErrorResponse
import com.discount.views.DiscountView
import com.discount.views.ui.activities.CouponFilterActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Avinash Kumar on 7/2/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class CouponFilterPresenter(var mDiscountView: DiscountView?) {
    private val TAG = CouponFilterPresenter::class.java.simpleName

    fun getCategoryList() {
        mDiscountView?.progress(true)
        Discount.getApis().getCategories(Discount.getSession().authToken).enqueue(object : Callback<CategoryResponse> {
            override fun onResponse(call: Call<CategoryResponse>, response: Response<CategoryResponse>) {
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
                        val result: CategoryResponse.Result = response.body()?.result!!
                        (mDiscountView as CouponFilterActivity).onSuccessCategoryList(result.categoryList.toMutableList())
                    } else {
                        mDiscountView?.onErrorOrInvalid(response.body()?.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<CategoryResponse>, t: Throwable) {
                mDiscountView?.progress(false)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }

    fun onDestroy() {
        mDiscountView = null
    }
}