package com.discount.views.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.discount.R
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.presenters.ProfilePresenter
import com.discount.views.BaseView
import kotlinx.android.synthetic.main.activity_profile.*


class ProfileActivity : AppCompatActivity(),BaseView {

    private val mPresenter = ProfilePresenter(this)

    override fun <T> navigateTo(clazz: Class<T>) {
        //TODO: To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        ivGoToBack.setOnClickListener { finish() }

        btnLogout.setOnClickListener{
            val prefHelper = PrefHelper.instance
            prefHelper?.run {
                savePref(Constants.IS_USER_LOGGED_IN,false)
                remove(Constants.USER_DETAILS)
            }
            val mIntent = Intent(this,SignInActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(mIntent)
            overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
        }

        mPresenter.onCreate(this)
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }
}
