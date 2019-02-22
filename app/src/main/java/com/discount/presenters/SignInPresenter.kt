package com.discount.presenters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import com.discount.R
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.app.utils.MyLog
import com.discount.app.utils.listeners.RememberListener
import com.discount.interactors.SignInInteractor
import com.discount.views.DiscountView
import com.discount.views.ui.activities.ForgotPasswordActivity
import com.discount.views.ui.activities.HomeActivity
import com.discount.views.ui.activities.SignInActivity
import com.discount.views.ui.activities.SignUpActivity
import com.facebook.CallbackManager
import com.facebook.FacebookCallback
import com.facebook.FacebookException
import com.facebook.GraphRequest
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import kotlinx.android.synthetic.main.activity_sign_in.*
import okhttp3.MediaType
import okhttp3.RequestBody

/**
 * Created by Avinash Kumar on 11/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class SignInPresenter(var mDiscountView: DiscountView?, var mInteractor: SignInInteractor,
                      var listenet: RememberListener): SignInInteractor.OnLoginFinishedListener {
    val TAG = SignInPresenter::class.java.simpleName

    private var fbButton: LoginButton? = null
    private var callbackManager: CallbackManager? = null

    init {
        callbackManager = CallbackManager.Factory.create()
    }


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
            it.navigateTo(HomeActivity::class.java)
        }
    }

    fun onDestroy() {
        mDiscountView = null
    }

    fun goToSignUp() {
        mDiscountView?.navigateTo(SignUpActivity::class.java)
    }

    fun goToForgotPassword() {
        mDiscountView?.navigateTo(ForgotPasswordActivity::class.java)
    }

    fun validate(email: String, password: String) {
        mDiscountView?.let {
            if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if (email.isEmpty()) {
                    (mDiscountView as SignInActivity).etEmailId.requestFocus()
                    it.onErrorOrInvalid((it as Context).resources.getString(R.string.please_enter_email_id))
                    return
                }
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.invalid_email_id))
                return
            }

            if(password.isEmpty() || password.length < 6) {
                if (password.isEmpty()) {
                    (mDiscountView as SignInActivity).etPassword.requestFocus()
                    it.onErrorOrInvalid((it as Context).resources.getString(R.string.please_enter_password))
                    return
                }
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.invalid_password))
                return
            }

            mInteractor.login(email = email,password = password,mListener = this)
            it.progress(true)
        }

    }

    fun setRememberMe(isRememberMe: Boolean) {
        PrefHelper.instance?.savePref(Constants.REMEMBER_ME,isRememberMe)
    }

    fun checkRemembered() {
        PrefHelper.instance?.run {
            if (getPref(Constants.REMEMBER_ME,false)) {
                val email = getPref(Constants.EMAIL,"")
                val password = getPref(Constants.PASSWORD,"")
                listenet.onRemember(email,password)
            }
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        callbackManager?.onActivityResult(requestCode,resultCode,data)
    }

    fun initFacebookButton(mButton: LoginButton) {
        fbButton = mButton
        fbButton?.setReadPermissions(mutableListOf("user_photos", "email", "user_birthday", "public_profile"))
        fbButton?.registerCallback(callbackManager,object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                val mRequest = GraphRequest.newMeRequest(result?.accessToken) { `object`, response ->
                    MyLog.i(TAG,response.toString())

                    val fName = RequestBody.create(MediaType.parse("text/plain"),`object`?.getString("first_name")!!)
                    val lName = RequestBody.create(MediaType.parse("text/plain"),`object`?.getString("last_name")!!)
                    val mail = RequestBody.create(MediaType.parse("text/plain"),`object`?.getString("email")!!)
                    val dType = RequestBody.create(MediaType.parse("text/plain"),"2")
                    val sId = RequestBody.create(MediaType.parse("text/plain"),`object`?.getString("id"))
                    val sType = RequestBody.create(MediaType.parse("text/plain"),"facebook")
                    mInteractor.continueWithFacebook(firstName = fName,lastName =  lName,email =  mail,deviceType = dType,
                        socialId = sId, socialType = sType,mListener = this@SignInPresenter)
                    mDiscountView?.progress(true)
                }

                val mBundle = Bundle()
                mBundle.putString("fields","id,first_name,last_name,email,gender,birthday")
                mRequest.parameters = mBundle
                mRequest.executeAsync()
            }

            override fun onError(error: FacebookException?) {
                MyLog.e(TAG,"Error with facebook login",error!!)
            }

            override fun onCancel() {
                MyLog.i(TAG, "Login cancelled")
            }
        })
    }

    fun continueWithFacebook() {
        fbButton?.performClick()
    }
}