package com.discount.presenters

import android.app.Activity
import android.content.Context
import android.util.Patterns
import com.discount.R
import com.discount.interactors.PasswordInteractor
import com.discount.views.DiscountView

/**
 * Created by Avinash Kumar on 14/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class PasswordPresenter(var mDiscountView: DiscountView?, var mPasswordInteractor: PasswordInteractor): PasswordInteractor.OnProcessFinishedListener {
    override fun onError(msg: String) {
        mDiscountView?.let {
            it.progress(false)
            it.onErrorOrInvalid(msg)
        }
    }

    override fun onSuccess(msg: String) {
        mDiscountView?.let {
            it.progress(false)
            it.onSuccess(msg)
            (it as Activity).finish()
        }
    }

    fun validate(email: String) {
        mDiscountView?.let {
            if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (email.isEmpty()) {
                    it.onErrorOrInvalid((it as Context).resources.getString(R.string.please_enter_email_id))
                    return
                }
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.invalid_email_id))
                return
            }


            mPasswordInteractor.send(email,this)
            it.progress(true)
        }
    }

    fun onDestroy() {
        mDiscountView = null
    }
}