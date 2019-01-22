package com.discount.interactors

import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.models.AuthResponse
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
    }

    fun send(email: String, mListener: OnResponseListener) {
        Discount.getApis().forgotPassword(email).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                MyLog.i(TAG,"msg ${response.body()?.message}")
                if (response.body()?.status?.equals(Constants.SUCCESS)!!) {
                    mListener.onSuccess(response.body()?.message!!)
                } else {
                    mListener.onError(response.body()?.message!!)
                }
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                mListener.onError(t.localizedMessage)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }
}