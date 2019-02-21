package com.discount.interactors

import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.models.AuthResponse
import com.discount.models.ErrorResponse
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Avinash Kumar on 14/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class PasswordInteractor {
    private val TAG = PasswordInteractor::class.java.simpleName

    interface OnResponseListener {
        fun onError(msg: String)
        fun onSuccess(msg: String)
        fun logout()
    }

    fun send(email: String, mListener: OnResponseListener) {
        Discount.getApis().forgotPassword(email).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                MyLog.i(TAG,"Response ${response.isSuccessful}")

                if (response.code() == 400) {
                    val mError = Gson().fromJson<ErrorResponse>(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                    if (mError.responseCode == 300) {
                        mListener.logout()
                        return
                    }
                }

                if (response.isSuccessful) {
                    if (response.body()?.status?.equals(Constants.KEY_SUCCESS)!!) {
                        mListener.onSuccess(response.body()?.message!!)
                    } else {
                        mListener.onError(response.body()?.message!!)
                    }
                } else {
                    mListener.onError("${response.message()} ${response.code()}")
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                mListener.onError(t.localizedMessage)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }
}