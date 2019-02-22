package com.discount.views.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.discount.R
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.interactors.ProfileInteractor
import com.discount.presenters.ProfilePresenter
import com.discount.views.DiscountView
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.app_bar_profile.*
import kotlinx.android.synthetic.main.content_profile.*


class ProfileActivity : AppCompatActivity(),DiscountView {
    private val mPresenter = ProfilePresenter(this,ProfileInteractor())

    override fun <T> navigateTo(clazz: Class<T>, bundle: Bundle?) {
        val mIntent = Intent(this,clazz)
        if (bundle != null) mIntent.putExtra(Constants.KEY_BUNDLE_PARAM,bundle)
        startActivity(mIntent)
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
    }

    override fun onErrorOrInvalid(msg: String) {
        Snackbar.make(alertRootLayoutProfile,msg,Snackbar.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Snackbar.make(alertRootLayoutProfile,msg,Snackbar.LENGTH_SHORT).show()
    }

    override fun progress(flag: Boolean) {
        progressBar.visibility = if (flag) View.VISIBLE else View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_bar_profile)
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN

        ivGoToBack.setOnClickListener { finish() }
        ivEditProfile.setOnClickListener {
            navigateTo(EditProfileActivity::class.java)
        }

        btnLogout.setOnClickListener{
            Discount.removeSession()
            val prefHelper = PrefHelper.instance
            prefHelper?.run {
                savePref(Constants.IS_USER_LOGGED_IN,false)
                remove(Constants.USER_DETAILS)
            }
            LoginManager.getInstance().logOut()
            val mIntent = Intent(this,SignInActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(mIntent)
            overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
        }

        llSubscriptions.setOnClickListener { navigateTo(SubscriptionDetailActivity::class.java) }
        llChangePassword.setOnClickListener { mPresenter.showChangePasswordDialog() }
        mPresenter.initViews(this)
    }

    override fun onResume() {
        super.onResume()
        mPresenter.initViews(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.onDestroy()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }
}
