package com.discount.interactors

import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.app.utils.MyLog
import com.discount.models.AuthResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.Part

/**
 * Created by Avinash Kumar on 12/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class EditProfileInteractor {
    private val TAG = EditProfileInteractor::class.java.simpleName

    interface OnProfileUpdateListener{
        fun onError(msg: String)
        fun onSuccess(msg: String)
    }

    fun updateUserProfile(firstName: RequestBody, lastName: RequestBody, email: RequestBody, phoneNumber: RequestBody,
                          countryCode: RequestBody, dateOfBirth: RequestBody, gender: RequestBody,
                          @Part profileImage: MultipartBody.Part, mListener: OnProfileUpdateListener) {
        Discount.getApis().updateUserProfile(Discount.getSession().authToken,firstName,lastName,email,phoneNumber,
            countryCode,gender,dateOfBirth,profileImage).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                MyLog.i(TAG,"msg ${response.body()?.message}")
                if (response.isSuccessful) {
                    response.body()?.run {
                        if (status == Constants.KEY_SUCCESS) {
                            val prefHelper = PrefHelper.instance
                            prefHelper?.run {
                                savePref(Constants.USER_DETAILS,PrefHelper.encodeProfile(userDetail))
                            }
                            mListener.onSuccess(message)
                        } else {
                            mListener.onError(message)
                        }
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