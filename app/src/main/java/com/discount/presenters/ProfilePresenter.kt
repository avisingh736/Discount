package com.discount.presenters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import com.bumptech.glide.Glide
import com.discount.R
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.app.utils.MyLog
import com.discount.interactors.ProfileInteractor
import com.discount.models.Content
import com.discount.models.UserDetail
import com.discount.views.DiscountView
import com.discount.views.ui.activities.ProfileActivity
import com.discount.views.ui.activities.SettingActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.app_bar_profile.*
import kotlinx.android.synthetic.main.content_profile.*
import kotlinx.android.synthetic.main.dialog_change_password.view.*

/**
 * Created by Avinash Kumar on 17/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */

/**
 * This presenter is used in Setting Activity also
 * */
class ProfilePresenter(var mView: DiscountView?, var mInteractor: ProfileInteractor): ProfileInteractor.OnProfileUpdateListener {
    private val TAG = ProfilePresenter::class.java.simpleName
    var dialogView: View? = null
    var mDialog: AlertDialog? = null

    init {
        //mInteractor.getContents(this)
    }

    fun initViews(profile: ProfileActivity) {
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
            profile.tvPhoneNo.text = "$countryCode $phoneNumber"
            this
        }
    }

    override fun logout() {
        Discount.logout(mView as Context)
    }

    fun showChangePasswordDialog() {
        dialogView = LayoutInflater.from(mView as Context).inflate(R.layout.dialog_change_password,null)
        mDialog = AlertDialog.Builder(mView as Context).setView(dialogView).setCancelable(false).create()
        mDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogView?.ivClose?.setOnClickListener {
            MyLog.d(TAG,"Close dialog")
            mDialog?.dismiss()
        }
        dialogView?.btnDone?.setOnClickListener {
            MyLog.d(TAG,"Validating...")
            this@ProfilePresenter.validate(dialogView?.etCurrentPassword?.text.toString(),
                dialogView?.etNewPassword?.text.toString(),
                dialogView?.etConfirmPassword?.text.toString())
        }
        mDialog?.show()
    }

    fun validate(currentPassword: String, newPassword: String, confirmPassword: String) {
        MyLog.d(TAG,"Performing validations")
        if(currentPassword.isEmpty() || currentPassword.length < 6) {
            if (currentPassword.isEmpty()) {
                dialogView?.etCurrentPassword?.requestFocus()
                dialogView?.tilCurrentPassword?.error = (mView as Context).resources.getString(R.string.please_enter_current_password)
                return
            }
            dialogView?.tilCurrentPassword?.error = (mView as Context).resources.getString(R.string.invalid_current_password)
            return
        } else {
            dialogView?.tilCurrentPassword?.error = null
        }

        if(newPassword.isEmpty() || newPassword.length < 6) {
            if (newPassword.isEmpty()) {
                dialogView?.etNewPassword?.requestFocus()
                dialogView?.tilNewPassword?.error = (mView as Context).resources.getString(R.string.please_enter_new_password)
                return
            }
            dialogView?.tilNewPassword?.error = (mView as Context).resources.getString(R.string.invalid_new_password)
            return
        } else {
            dialogView?.tilNewPassword?.error = null
        }

        if(confirmPassword.isEmpty() || confirmPassword != newPassword) {
            if (confirmPassword.isEmpty()) {
                dialogView?.etConfirmPassword?.requestFocus()
                dialogView?.tilConfirmPassword?.error = (mView as Context).resources.getString(R.string.please_confirm_password)
                return
            }
            dialogView?.tilConfirmPassword?.error = (mView as Context).resources.getString(R.string.password_does_not_match)
            return
        } else {
            dialogView?.tilConfirmPassword?.error = null
        }

        mInteractor.changePassword(currentPassword,newPassword,confirmPassword,this)
        dialogView?.progressBar?.visibility = View.VISIBLE
    }

    override fun onError(msg: String) {
        mDialog?.dismiss()
        mView?.onErrorOrInvalid(msg)
    }

    override fun onSuccess(msg: String) {
        mDialog?.dismiss()
        mView?.onSuccess(msg)
    }

    override fun onContent(content: Content) {
        PrefHelper.instance?.savePref(Constants.URL_TERMS,content.termAndCondition)
        PrefHelper.instance?.savePref(Constants.URL_POLICY,content.policy)
        PrefHelper.instance?.savePref(Constants.URL_ABOUT,content.about)
    }

    fun onDestroy() {
        dialogView = null
        mView = null
    }
}