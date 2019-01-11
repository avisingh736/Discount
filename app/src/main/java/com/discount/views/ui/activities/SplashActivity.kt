package com.discount.views.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.discount.R
import com.discount.presenters.SplashPresenter
import com.discount.views.SplashView

class SplashActivity : AppCompatActivity(),SplashView {
    private val mSplashPresenter = SplashPresenter(this)

    override fun navigateToHome() {
        /**
         *  This time we are going to Login Activity
         *  According to flow we'll check user login here and manage the logics in presenter
         * */
        window.clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN)
        startActivity(Intent(this,LoginActivity::class.java))
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        mSplashPresenter.onStart()
    }

    override fun onDestroy() {
        mSplashPresenter.onDestroy()
        super.onDestroy()
    }
}
