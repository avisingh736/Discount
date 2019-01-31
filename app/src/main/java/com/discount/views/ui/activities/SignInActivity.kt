package com.discount.views.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.discount.R
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.app.utils.RememberLestener
import com.discount.interactors.SignInInteractor
import com.discount.presenters.SignInPresenter
import com.discount.views.DiscountView
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(),DiscountView, RememberLestener {
    val TAG = SignInActivity::class.java.simpleName

    private var mSignInPresenter: SignInPresenter = SignInPresenter(this, SignInInteractor(),this)

    /**
     * This method will navigate to SignUpActivity
     * */
    override fun <T> navigateTo(clazz: Class<T>, bundle: Bundle?) {
        val mIntent = Intent(this,clazz)
        if (bundle != null) mIntent.putExtra(Constants.KEY_BUNDLE_PARAM,bundle)
        startActivity(mIntent)
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
        if (clazz == HomeActivity::class.java) finish()
    }

    override fun onErrorOrInvalid(msg: String) {
        Snackbar.make(signInActivityRoot,msg,Snackbar.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Snackbar.make(signInActivityRoot,msg,Snackbar.LENGTH_SHORT).show()
    }

    override fun progress(flag: Boolean) {
        if (flag) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }

    override fun onRemember(email: String, password: String) {
        etEmailId.setText(email)
        etPassword.setText(password)
        ivRememberMe.setImageResource(R.drawable.ic_active_check_box)
        ivRememberMe.isSelected = true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        tvGoToSignUp.setOnClickListener { mSignInPresenter.goToSignUp() }
        tvForgotPassword.setOnClickListener { mSignInPresenter.goToForgotPassword()}
        btnSignIn.setOnClickListener{mSignInPresenter.validate(etEmailId.text.toString(),etPassword.text.toString())}
        ivRememberMe.setOnClickListener {
            if (it.isSelected) {
                ivRememberMe.setImageResource(R.drawable.ic_inactive_check_box_ico)
                it.isSelected = false
                MyLog.d(TAG, "Not selected")
            } else {
                ivRememberMe.setImageResource(R.drawable.ic_active_check_box)
                it.isSelected = true
                MyLog.d(TAG, "Selected")
            }

            mSignInPresenter.setRememberMe(it.isSelected)
        }

        mSignInPresenter.checkRemembered()
    }

    override fun onDestroy() {
        mSignInPresenter.onDestroy()
        super.onDestroy()
    }

}
