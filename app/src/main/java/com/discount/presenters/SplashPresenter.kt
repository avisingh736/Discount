package com.discount.presenters

import android.os.Handler
import com.discount.views.SplashView

/**
 * Created by Avinash Kumar on 11/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class SplashPresenter(var mView: SplashView?) {

    fun onStart() {
        Handler().postDelayed({mView?.navigateToHome()},1000)
    }

    fun onDestroy() {
        mView = null
    }
}