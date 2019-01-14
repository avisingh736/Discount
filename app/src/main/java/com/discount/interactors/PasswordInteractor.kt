package com.discount.interactors

import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.models.AuthenticationResponse
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

    interface OnProcessFinishedListener {
        fun onError(msg: String)
        fun onSuccess(msg: String)
    }

    fun send(email: String, mListener: OnProcessFinishedListener) {
        Discount.getApis().forgotPassword(email).enqueue(object : Callback<AuthenticationResponse>{
            override fun onResponse(call: Call<AuthenticationResponse>, response: Response<AuthenticationResponse>) {
                MyLog.i(TAG,"msg ${response.body()?.message}")
                if (response.body()?.status?.equals(Constants.SUCCESS)!!) {
                    mListener.onSuccess(response.body()?.message!!)
                } else {
                    mListener.onError(response.body()?.message!!)
                }
            }

            override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                mListener.onError(t.localizedMessage)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }
}