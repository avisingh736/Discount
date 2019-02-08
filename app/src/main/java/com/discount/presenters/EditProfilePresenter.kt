package com.discount.presenters

import android.app.DatePickerDialog
import android.content.Context
import com.bumptech.glide.Glide
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.models.UserDetail
import com.discount.views.BaseView
import com.discount.views.ui.activities.EditProfileActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.app_bar_edit_profile.*
import kotlinx.android.synthetic.main.content_edit_profile.*
import java.util.*

/**
 * Created by Avinash Kumar on 17/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class EditProfilePresenter(var mView: BaseView?) {
    private var mCalender = Calendar.getInstance()
    private var mDatePickerDialog: DatePickerDialog? = null
    private var mListener: DatePickerDialog.OnDateSetListener? = null

    init {
        mListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            mCalender.set(year,monthOfYear,dayOfMonth)
            val month = if (monthOfYear < 10) "0${monthOfYear+1}" else (monthOfYear+1).toString()
            val day = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
            val mSelectedDate = "$day/$month/$year"
            (mView as EditProfileActivity).onDateSelected(mSelectedDate)
        }
    }

    fun onCreate(profile: EditProfileActivity) {
        val prefHelper = PrefHelper.instance
        val data = prefHelper?.getPref(Constants.USER_DETAILS,"")
        val userDetails = Gson().fromJson(data,UserDetail::class.java)
        userDetails?.run {
            Glide.with(mView as Context).load(profileImage).into(profile.ivProfileImage)
            profile.etFirstName.setText(firstName)
            profile.etLastName.setText(lastName)
            profile.etEmailId.setText(email)
            profile.tvDateOfBirth.text = dateOfBirth
            profile.etPhoneNo.setText(phoneNumber)
            this
        }
    }

    fun showDatePicker() {
        mDatePickerDialog = DatePickerDialog(mView as EditProfileActivity,mListener,mCalender.get(Calendar.YEAR),mCalender.get(Calendar.MONTH),mCalender.get(Calendar.DAY_OF_MONTH))
        mDatePickerDialog?.show()
    }

    fun onDestroy() {
        mView = null
    }
}