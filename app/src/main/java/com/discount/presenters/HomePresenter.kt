package com.discount.presenters

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.location.Geocoder
import android.location.Location
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import com.discount.R
import com.discount.adapters.UniversityAdapter
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.app.utils.MyLog
import com.discount.interactors.HomeInteractor
import com.discount.models.Coupon
import com.discount.models.University
import com.discount.models.UserDetail
import com.discount.views.HomeView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_student.view.*
import java.lang.Exception
import java.util.*

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class HomePresenter(var mHomeView: HomeView?, var mInteractor: HomeInteractor): HomeInteractor.OnResponseListener{
    private val TAG = HomePresenter::class.java.simpleName

    private var mDialog: AlertDialog? = null
    private var selectedPosition: Int = 0
    private var universities: MutableList<University>? = null
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private var mLocation: Location? = null

    fun getLastKnownLocation() {
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(mHomeView as Context)
        try {
            mFusedLocationProviderClient?.lastLocation
                ?.addOnSuccessListener { location : Location? ->
                        if (location != null) {
                            mLocation = location
                            MyLog.d(TAG,"Successful location task: Lat=${location.latitude} Lng=${location.longitude}")
                            getCompleteAddress(location)
                            getCouponList(location)
                        } else {
                            MyLog.w(TAG,"Successful location task: This is rare case when location is null")
                        }
                }
                ?.addOnFailureListener {
                    MyLog.e(TAG,"Failed to get location: ",it)
                }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }

    fun studentDialogStatus() {
        mInteractor.getUniversityListFromServer(this)
    }

    override fun showStudentDialog(universities: MutableList<University>) {
        universities.add(0,University("","",""))
        this.universities = universities
        val dialogView = LayoutInflater.from(mHomeView as Context).inflate(R.layout.dialog_student,null)
        mDialog = AlertDialog.Builder(mHomeView as Context).setView(dialogView).create()
        mDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogView.fabClose.setOnClickListener {
            val prefHelper = PrefHelper.instance
            prefHelper?.savePref(Constants.DIALOG,false)
            mDialog?.dismiss()
        }
        dialogView.spUniversityName.adapter = UniversityAdapter(mHomeView as Context,universities)
        dialogView.spUniversityName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                selectedPosition = position
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        dialogView.btnAdd.setOnClickListener { validate(dialogView) }
        mDialog?.show()
    }

    override fun onUniversityAdded(msg: String) {
        if (mDialog != null) {
            mDialog?.dismiss()
        }
    }

    override fun onSuccessCoupons(coupons: MutableList<Coupon>) {
        mHomeView?.progress(false)
        mHomeView?.onCouponSuccess(coupons)
    }

    private fun validate(dialogView: View) {
        if (selectedPosition == 0) {
            dialogView.tilUniversityName.error = (mHomeView as Context).getString(R.string.please_select_university)
            return
        } else {
            dialogView.tilUniversityName.error = null
        }

        val mStudentId = dialogView.etStudentId.text.toString()
        if (mStudentId.isEmpty()) {
            dialogView.tilStudentId.error = (mHomeView as Context).getString(R.string.please_enter_student_id)
            dialogView.etStudentId.requestFocus()
            return
        } else {
            dialogView.tilStudentId.error = null
        }

        val prefHelper = PrefHelper.instance
        if(prefHelper?.getPref(Constants.DIALOG,false)!!) {
            val s = prefHelper.getPref(Constants.USER_DETAILS,"")
            val userDetail = Gson().fromJson(s,UserDetail::class.java)
            val uName = universities?.get(selectedPosition)?.universityName!!
            if (userDetail != null)
                mInteractor.addUserUniversityToServer(uName,mStudentId,this)
        }
    }

    override fun progress(flag: Boolean) {
        mHomeView?.progress(flag)
    }

    override fun onError(msg: String) {
        mHomeView?.progress(false)
        mHomeView?.onErrorOrInvalid(msg)
    }

    fun onDestroy() {
        mHomeView = null
    }

    fun requestForLocationAccess() {
        if (ContextCompat.checkSelfPermission(mHomeView as Context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(mHomeView as Context, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            getLastKnownLocation()
            return
        } else {
            ActivityCompat.requestPermissions(mHomeView as Activity, arrayOf(
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.ACCESS_COARSE_LOCATION),Constants.LOCATION_PERMISSION)
        }
    }

    private fun getCouponList(location: Location,search: String = "",limit: String = "10", offset: String = "0") {
        mHomeView?.progress(true)
        mInteractor.getCouponListFromServer(location,search,limit,offset,this)
    }

    private fun getCompleteAddress(location: Location) {
        try {
            val addresses = Geocoder(mHomeView as Context, Locale.getDefault()).getFromLocation(location.latitude,location.longitude,1)
            Discount.getSession().address = addresses[0].getAddressLine(0)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}