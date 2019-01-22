package com.discount.interactors

import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.models.University
import com.discount.models.UniversityResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Created by Avinash Kumar on 21/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class HomeInteractor {
    private val TAG = HomeInteractor::class.java.simpleName

    interface OnResponseListener{
        fun onUniversityAdded(msg: String)
        fun showStudentDialog(universities: MutableList<University>)
        fun onError(msg: String)
    }

    fun getUniversityListFromServer(authToken: String,mListener: OnResponseListener) {
        Discount.getApis().getUniversityList(authToken).enqueue(object : Callback<UniversityResponse>{
            override fun onResponse(call: Call<UniversityResponse>, response: Response<UniversityResponse>) {
                if (response.isSuccessful) {
                    if (response.body()?.status.equals(Constants.SUCCESS,false)) {
                        val result: UniversityResponse.Result = response.body()?.result!!
                        mListener.showStudentDialog(result.universityList.toMutableList())
                    } else {
                        mListener.onError(response.body()?.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<UniversityResponse>, t: Throwable) {
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }

    fun addUserUniversityToServer(authToken: String,uName: String,studentId: String,mListener: OnResponseListener) {
        Discount.getApis().addUserUniversity(authToken,uName,studentId).enqueue(object : Callback<UniversityResponse>{
            override fun onResponse(call: Call<UniversityResponse>, response: Response<UniversityResponse>) {
                MyLog.i(TAG,"onResponse ${response.isSuccessful}")
                if (response.isSuccessful) {
                    if (response.body()?.status.equals(Constants.SUCCESS,false)) {
                        mListener.onUniversityAdded(response.body()?.message!!)
                    } else {
                        mListener.onError(response.body()?.message!!)
                    }
                }
            }

            override fun onFailure(call: Call<UniversityResponse>, t: Throwable) {
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }
}