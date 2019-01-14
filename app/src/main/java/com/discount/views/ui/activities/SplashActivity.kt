package com.discount.views.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.discount.R
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.presenters.SplashPresenter
import com.discount.views.BaseView

class SplashActivity : AppCompatActivity(),BaseView {
    private val mSplashPresenter = SplashPresenter(this)

    override fun <T> navigateTo(clazz: Class<T>) {
        startActivity(Intent(this,clazz))
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
