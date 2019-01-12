package com.discount.presenters

import android.content.Context
import android.util.Patterns
import com.discount.R
import com.discount.interactors.SignInInteractor
import com.discount.views.AuthenticationView

/**
 * Created by Avinash Kumar on 11/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class SignInPresenter(var mView: AuthenticationView?, var mInteractor: SignInInteractor): SignInInteractor.OnLoginFinishedListener {
    val TAG = SignInPresenter::class.java.simpleName


    override fun onError(msg: String) {
        mView?.onErrorOrInvalid(msg)
    }

    override fun onSuccess(msg: String) {
        mView?.onSuccess(msg)
    }


    fun onDestroy() {
        mView = null
    }

    fun goToSignUp() {
        mView?.navigateTo()
    }

    fun validate(email: String, password: String) {
        if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            mView?.onErrorOrInvalid((mView as Context).resources.getString(R.string.invalid_email_id))
            return
        }

        if(password.isEmpty() || password.length < 6) {
            mView?.onErrorOrInvalid((mView as Context).resources.getString(R.string.invalid_password))
            return
        }

        mInteractor.login(email = email,password = password,mListener = this)

    }
}