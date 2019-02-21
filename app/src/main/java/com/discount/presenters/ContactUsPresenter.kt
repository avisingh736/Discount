package com.discount.presenters

import android.content.Context
import android.util.Patterns
import com.discount.R
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.models.BaseResponse
import com.discount.models.ErrorResponse
import com.discount.views.DiscountView
import com.discount.views.ui.activities.ContactUsActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_contact_us.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Avinash Kumar on 13/2/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class ContactUsPresenter(var mDiscountView: DiscountView?) {
    private val TAG = ContactUsPresenter::class.java.simpleName

    private fun doContactUs(subject: String, message: String, email: String) {
        mDiscountView?.progress(true)
        Discount.getApis().contactUs(Discount.getSession().authToken, subject,message,email).enqueue(object : Callback<BaseResponse>{
            override fun onResponse(call: Call<BaseResponse>, response: Response<BaseResponse>) {
                mDiscountView?.progress(false)

                if (response.code() == 400) {
                    val mError = Gson().fromJson<ErrorResponse>(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                    if (mError.responseCode == 300) {
                        Discount.logout(mDiscountView as Context)
                        return
                    }
                }

                if (response.isSuccessful) {
                    if (response.body()?.status.equals(Constants.KEY_SUCCESS,false)) {
                        mDiscountView?.onSuccess(response.body()?.message!!)
                        (mDiscountView as ContactUsActivity).finish()
                    } else {
                        mDiscountView?.onErrorOrInvalid(response.body()?.message!!)
                    }
                } else {

                }
            }

            override fun onFailure(call: Call<BaseResponse>, t: Throwable) {
                mDiscountView?.progress(false)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }

    fun validate(email: String, subject: String, message: String) {
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            if(email.isEmpty()) {
                (mDiscountView as ContactUsActivity).etEmailId.requestFocus()
                mDiscountView?.onErrorOrInvalid((mDiscountView as Context).resources.getString(R.string.please_enter_email_id))
                return
            }
            mDiscountView?.onErrorOrInvalid((mDiscountView as Context).resources.getString(R.string.invalid_email_id))
            return
        }

        if(subject.isEmpty() || subject.length < 3) {
            if(subject.isEmpty()) {
                mDiscountView?.onErrorOrInvalid((mDiscountView as Context).resources.getString(R.string.please_enter_subject))
                (mDiscountView as ContactUsActivity).etSubject.requestFocus()
                return
            }
            mDiscountView?.onErrorOrInvalid((mDiscountView as Context).resources.getString(R.string.invalid_subject))
            return
        }

        if(message.isEmpty()) {
            mDiscountView?.onErrorOrInvalid((mDiscountView as Context).resources.getString(R.string.please_enter_message))
            (mDiscountView as ContactUsActivity).etMessage.requestFocus()
            return
        }

        doContactUs(subject,message,email)
    }

    fun onDestroy() {
        mDiscountView = null
    }
}