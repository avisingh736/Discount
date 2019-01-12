package com.discount.views.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.discount.R
import com.discount.interactors.SignInInteractor
import com.discount.presenters.SignInPresenter
import com.discount.views.AuthenticationView
import kotlinx.android.synthetic.main.activity_sign_in.*

class SignInActivity : AppCompatActivity(),AuthenticationView {
    val TAG = SignInActivity::class.java.simpleName

    private var mSignInPresenter: SignInPresenter = SignInPresenter(this, SignInInteractor())

    /**
     * This method will navigate to SignUpActivity
     * */
    override fun navigateTo() {
        startActivity(Intent(this,SignUpActivity::class.java))
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
    }

    override fun onErrorOrInvalid(msg: String) {
        Snackbar.make(signInActivityRoot,msg,Snackbar.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Snackbar.make(signInActivityRoot,msg,Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        tvGoToSignUp.setOnClickListener { mSignInPresenter.goToSignUp() }
        btnSignIn.setOnClickListener{mSignInPresenter.validate(etEmailId.text.toString(),etPassword.text.toString())}
    }

    override fun onDestroy() {
        mSignInPresenter.onDestroy()
        super.onDestroy()
    }
}
