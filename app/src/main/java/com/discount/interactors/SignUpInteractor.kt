package com.discount.interactors

import com.discount.app.Discount
import com.discount.app.utils.MyLog
import com.discount.models.AuthenticationResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Avinash Kumar on 12/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class SignUpInteractor {
    private val TAG = SignUpInteractor::class.java.simpleName

    interface OnRegistrationFinishedListener{
        fun onError(msg: String)
        fun onSuccess(msg: String)
    }

    fun register(firstName: RequestBody, lastName: RequestBody, email: RequestBody,
                 password: RequestBody, cPassword: RequestBody, profileImage: MultipartBody.Part,
                 deviceType: RequestBody, socialId: RequestBody, socialType: RequestBody,
                 mListener: OnRegistrationFinishedListener) {
        Discount.getApis().register(firstName,lastName,email,password,
            cPassword,profileImage,deviceType,socialId,socialType).enqueue(object : Callback<AuthenticationResponse>{
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