package com.discount.interactors

import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.app.utils.MyLog
import com.discount.models.AuthResponse
import com.discount.models.ErrorResponse
import com.google.gson.Gson
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
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
        Discount.getApis().login(email,password,deviceType, PrefHelper.instance?.getPref(Constants.TOKEN,"")!!).enqueue(object : Callback<AuthResponse> {
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                MyLog.i(TAG,"msg ${response.body()?.message}")

                if (response.isSuccessful) {
                    response.body()?.run {
                        if (status == Constants.KEY_SUCCESS) {
                            val prefHelper = PrefHelper.instance
                            prefHelper?.run {
                                savePref(Constants.IS_USER_LOGGED_IN,true)
                                remove(Constants.USER_DETAILS)
                                savePref(Constants.USER_DETAILS,PrefHelper.encodeProfile(userDetail))
                                savePref(Constants.URL_ABOUT,response.body()?.aboutUrl)
                                savePref(Constants.URL_POLICY,response.body()?.policyUrl)
                                savePref(Constants.URL_TERMS,response.body()?.termsUrl)
                                if (getPref(Constants.REMEMBER_ME,false)) {
                                    savePref(Constants.EMAIL,email)
                                    savePref(Constants.PASSWORD,password)
                                }
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

    fun continueWithFacebook(firstName: RequestBody, lastName: RequestBody, email: RequestBody,
                             password: RequestBody? = null, cPassword: RequestBody? = null, profileImage: MultipartBody.Part? = null,
                             deviceType: RequestBody, socialId: RequestBody, socialType: RequestBody,
                             mListener: OnLoginFinishedListener) { Discount.getApis().register(firstName,lastName,email,password,
            cPassword,profileImage,deviceType,socialId,socialType, RequestBody.create(MediaType.parse("text/plain"),PrefHelper.instance?.getPref(Constants.TOKEN,"")!!)).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                MyLog.i(TAG,"msg ${response.body()?.message}")
                if (response.isSuccessful) {
                    response.body()?.run {
                        if (status == Constants.KEY_SUCCESS) {
                            val prefHelper = PrefHelper.instance
                            prefHelper?.run {
                                savePref(Constants.IS_USER_LOGGED_IN,true)
                                remove(Constants.USER_DETAILS)
                                savePref(Constants.USER_DETAILS,PrefHelper.encodeProfile(userDetail))
                                savePref(Constants.URL_ABOUT,response.body()?.aboutUrl)
                                savePref(Constants.URL_POLICY,response.body()?.policyUrl)
                                savePref(Constants.URL_TERMS,response.body()?.termsUrl)
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