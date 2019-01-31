package com.discount.interactors

import android.location.Location
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.models.Coupon
import com.discount.models.CouponResponse
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
        fun onSuccessCoupons(coupons: MutableList<Coupon>)
        fun progress(flag: Boolean)
        fun onError(msg: String)
    }

    fun getUniversityListFromServer(mListener: OnResponseListener) {
        Discount.getApis().getUniversityList(Discount.getSession().authToken).enqueue(object : Callback<UniversityResponse>{
            override fun onResponse(call: Call<UniversityResponse>, response: Response<UniversityResponse>) {
                mListener.progress(false)
                MyLog.i(TAG,"Response ${response.isSuccessful}")
                if (response.isSuccessful) {
                    if (response.body()?.status.equals(Constants.KEY_SUCCESS,false)) {
                        val result: UniversityResponse.Result = response.body()?.result!!
                        mListener.showStudentDialog(result.universityList.toMutableList())
                    } else {
                        mListener.onError(response.body()?.message!!)
                    }
                } else {
                    mListener.onError("${response.message()} ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UniversityResponse>, t: Throwable) {
                mListener.progress(false)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }

    fun addUserUniversityToServer(uName: String,studentId: String,mListener: OnResponseListener) {
        Discount.getApis().addUserUniversity(Discount.getSession().authToken,uName,studentId).enqueue(object : Callback<UniversityResponse>{
            override fun onResponse(call: Call<UniversityResponse>, response: Response<UniversityResponse>) {
                mListener.progress(false)
                MyLog.i(TAG,"Response ${response.isSuccessful}")
                if (response.isSuccessful) {
                    if (response.body()?.status.equals(Constants.KEY_SUCCESS,false)) {
                        mListener.onUniversityAdded(response.body()?.message!!)
                    } else {
                        mListener.onError(response.body()?.message!!)
                    }
                } else {
                    mListener.onError("${response.message()} ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UniversityResponse>, t: Throwable) {
                mListener.progress(false)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }

    fun getCouponListFromServer(location: Location, search: String = "", limit: String = "10", offset: String = "0",mListener: OnResponseListener) {
        Discount.getApis().getCouponList(Discount.getSession().authToken,location.latitude.toString(),location.longitude.toString(),search,limit,offset).enqueue(object : Callback<CouponResponse>{
            override fun onResponse(call: Call<CouponResponse>, response: Response<CouponResponse>) {
                mListener.progress(false)
                MyLog.i(TAG,"Response ${response.isSuccessful}")
                if (response.isSuccessful && response.body() != null) {
                    if (response.body()?.status.equals(Constants.KEY_SUCCESS,false)) {
                        val result: CouponResponse.Result = response.body()?.result!!
                        mListener.onSuccessCoupons(result.couponList.toMutableList())
                    } else {
                        mListener.onError(response.body()?.message!!)
                    }
                }else {
                    mListener.onError("${response.message()} ${response.code()}")
                }
            }

            override fun onFailure(call: Call<CouponResponse>, t: Throwable) {
                mListener.progress(false)
                MyLog.e(TAG,"Error: ",t)
            }
        })
    }
}