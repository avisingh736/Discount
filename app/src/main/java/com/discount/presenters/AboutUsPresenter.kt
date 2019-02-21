package com.discount.presenters

import android.content.Context
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.models.Content
import com.discount.models.ContentResponse
import com.discount.models.ErrorResponse
import com.discount.models.StoreInfoResponse
import com.discount.views.DiscountView
import com.discount.views.ui.activities.AboutUsActivity
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
class AboutUsPresenter(var mDiscountView: DiscountView?) {
    private var TAG = AboutUsPresenter::class.java.simpleName

    fun getContents() {
        mDiscountView?.progress(true)
        Discount.getApis().getContents(Discount.getSession().authToken).enqueue(object : Callback<ContentResponse>{
            override fun onResponse(call: Call<ContentResponse>, response: Response<ContentResponse>) {
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
                        val content: Content = response.body()?.content!!
                        (mDiscountView as AboutUsActivity).onContent(content)
                    } else {
                        mDiscountView?.onErrorOrInvalid(response.body()?.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<ContentResponse>, t: Throwable) {
                mDiscountView?.progress(false)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }

    fun onDestroy() {
        mDiscountView = null
    }
}