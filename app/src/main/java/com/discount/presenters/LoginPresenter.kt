package com.discount.presenters

import com.discount.interactors.LoginInteractor
import com.discount.views.LoginView

/**
 * Created by Avinash Kumar on 11/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class LoginPresenter(var mView: LoginView?, var interactor: LoginInteractor) {



    fun onDestroy() {
        mView = null
    }

    fun goToSignUp() {
        mView?.navigateToSignUp()
    }
}