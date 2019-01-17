package com.discount.presenters

import android.content.Context
import com.bumptech.glide.Glide
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.models.UserDetail
import com.discount.views.BaseView
import com.discount.views.ui.activities.ProfileActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_profile.*

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
            profile.tvFirstName.text = firstName
            profile.tvLastName.text = lastName
            Glide.with(mView as Context).load(profileImage).into(profile.ivProfileImage)
        }
    }

    fun onDestroy() {
        mView = null
    }
}