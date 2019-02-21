package com.discount.presenters

import android.content.Context
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.models.ErrorResponse
import com.discount.models.StoreInfoResponse
import com.discount.views.DiscountView
import com.discount.views.ui.activities.StoreDetailActivity
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Avinash Kumar on 29/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class StoreDetailPresenter(var mDiscountView: DiscountView?) {
    private var TAG = StoreDetailPresenter::class.java.simpleName

    fun getStoreInfo(storeId: String,limit: String = "6", offset: String = "0") {
        mDiscountView?.progress(true)
        Discount.getApis().getStoreInfo(Discount.getSession().authToken,storeId,limit,offset).enqueue(object : Callback<StoreInfoResponse>{
            override fun onResponse(call: Call<StoreInfoResponse>, response: Response<StoreInfoResponse>) {
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
                        val result: StoreInfoResponse.Result = response.body()?.result!!
                        if (offset.toInt() == 0) {
                            (mDiscountView as StoreDetailActivity).onStoreInfo(result.storeInfo,result.couponList.toMutableList())
                        } else if (offset.toInt() > 0) {
                            (mDiscountView as StoreDetailActivity).onStoreInfo(null,result.couponList.toMutableList())
                        }
                    } else {
                        mDiscountView?.onErrorOrInvalid(response.body()?.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<StoreInfoResponse>, t: Throwable) {
                mDiscountView?.progress(false)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }

    fun onDestroy() {
        mDiscountView = null
    }
}