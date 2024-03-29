package com.discount.interactors

import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.app.utils.MyLog
import com.discount.models.AuthResponse
import com.discount.models.Content
import com.discount.models.ContentResponse
import com.discount.models.ErrorResponse
import com.google.gson.Gson
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
class ProfileInteractor {
    private val TAG = ProfileInteractor::class.java.simpleName

    interface OnProfileUpdateListener{
        fun onError(msg: String)
        fun onSuccess(msg: String)
        fun onContent(content: Content)
        fun logout()
    }

    fun changePassword(oldPassword: String, newPassword: String, cPassword: String, mListener: OnProfileUpdateListener) {
        Discount.getApis().changePassword(Discount.getSession().authToken,oldPassword,newPassword,cPassword).enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                MyLog.i(TAG,"msg ${response.body()?.message}")

                if (response.code() == 400) {
                    val mError = Gson().fromJson<ErrorResponse>(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                    if (mError.responseCode == 300) {
                        mListener.logout()
                        return
                    }
                }

                if (response.isSuccessful) {
                    response.body()?.run {
                        if (status == Constants.KEY_SUCCESS) {
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

    /**
     *  This method is not getting called
     * */
    fun getContents(mListener: OnProfileUpdateListener) {
        Discount.getApis().getContents(Discount.getSession().authToken).enqueue(object : Callback<ContentResponse>{
            override fun onResponse(call: Call<ContentResponse>, response: Response<ContentResponse>) {
                MyLog.i(TAG,"onResponse ${response.isSuccessful}")

                if (response.code() == 400) {
                    val mError = Gson().fromJson<ErrorResponse>(response.errorBody()!!.charStream(), ErrorResponse::class.java)
                    if (mError.responseCode == 300) {
                        return
                    }
                }

                if(response.isSuccessful) {
                    if (response.body()?.status.equals(Constants.KEY_SUCCESS,false)) {
                        val content: Content = response.body()?.content!!
                        mListener.onContent(content)
                    } else {
                        mListener.onError(response.body()?.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<ContentResponse>, t: Throwable) {
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }

}