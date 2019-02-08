package com.discount.presenters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.discount.R
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.models.UserDetail
import com.discount.views.BaseView
import com.discount.views.ui.activities.ProfileActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.app_bar_profile.*
import kotlinx.android.synthetic.main.content_profile.*
import kotlinx.android.synthetic.main.dialog_change_password.view.*

/**
 * Created by Avinash Kumar on 17/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class ProfilePresenter(var mView: BaseView?) {

    fun onCreate(profile: ProfileActivity) {
        val prefHelper = PrefHelper.instance
        val data = prefHelper?.getPref(Constants.USER_DETAILS,"")
        val userDetails = Gson().fromJson(data,UserDetail::class.java)
        userDetails?.run {
            Glide.with(mView as Context).load(profileImage).into(profile.ivProfileImage)
            profile.tvFirstName.text = firstName
            profile.tvLastName.text = lastName
            profile.tvEmailId.text = email
            profile.tvDateOfBirth.text = dateOfBirth
            profile.tvGender.text = gender
            profile.tvPhoneNo.text = phoneNumber
            this
        }
    }

    fun showChangePasswordDialog() {
        val dialogView = LayoutInflater.from(mView as Context).inflate(R.layout.dialog_change_password,null)
        val mDialog = AlertDialog.Builder(mView as Context).setView(dialogView).setCancelable(false).create()
        mDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogView.ivClose.setOnClickListener { mDialog?.dismiss() }
        mDialog?.show()
    }

    fun onDestroy() {
        mView = null
    }
}