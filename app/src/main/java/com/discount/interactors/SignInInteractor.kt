package com.discount.interactors

import com.discount.app.Discount
import com.discount.app.utils.MyLog
import com.discount.models.AuthenticationResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Avinash Kumar on 11/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class SignInInteractor {
    private val TAG = SignInInteractor::class.java.simpleName

    interface OnLoginFinishedListener{
        fun onError(msg: String)
        fun onSuccess(msg: String)
    }

    fun login(email: String, password: String, deviceType: String = "2", mListener: OnLoginFinishedListener) {
        Discount.getApis().login(email,password,deviceType).enqueue(object : Callback<AuthenticationResponse> {
            override fun onResponse(call: Call<AuthenticationResponse>, response: Response<AuthenticationResponse>) {
                MyLog.i(TAG,"msg ${response.body()?.message}")
                mListener.onSuccess(response.body()?.message!!)
            }

            override fun onFailure(call: Call<AuthenticationResponse>, t: Throwable) {
                mListener.onError(t.localizedMessage)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }
}