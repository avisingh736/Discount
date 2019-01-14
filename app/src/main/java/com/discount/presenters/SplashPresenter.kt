package com.discount.presenters

import android.os.Handler
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.views.BaseView
import com.discount.views.ui.activities.HomeActivity
import com.discount.views.ui.activities.SignInActivity

/**
 * Created by Avinash Kumar on 11/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class SplashPresenter(var mView: BaseView?) {

    fun onStart() {
        Handler().postDelayed({
            val prefHelper = PrefHelper.instance
            prefHelper?.run {
                if (getPref(Constants.IS_USER_LOGGED_IN,false)) {
                    mView?.navigateTo(HomeActivity::class.java)
                } else {
                    mView?.navigateTo(SignInActivity::class.java)
                }
            }
        },1000)
    }

    fun onDestroy() {
        mView = null
    }
}