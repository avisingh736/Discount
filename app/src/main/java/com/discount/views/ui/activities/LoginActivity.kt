package com.discount.views.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.discount.R
import com.discount.interactors.LoginInteractor
import com.discount.presenters.LoginPresenter
import com.discount.views.LoginView
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity(),LoginView {

    private var mLoginPresenter: LoginPresenter = LoginPresenter(this, LoginInteractor())

    override fun navigateToSignUp() {
        startActivity(Intent(this,SignUpActivity::class.java))
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnGoToSignUp.setOnClickListener { mLoginPresenter.goToSignUp() }
    }

    override fun onDestroy() {
        mLoginPresenter.onDestroy()
        super.onDestroy()
    }
}
