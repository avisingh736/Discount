package com.discount.views.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import com.discount.R
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.interactors.ProfileInteractor
import com.discount.presenters.ProfilePresenter
import com.discount.views.DiscountView
import com.facebook.login.LoginManager
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity(),DiscountView {
    private val mPresenter = ProfilePresenter(this, ProfileInteractor())
    override fun onErrorOrInvalid(msg: String) {
        Snackbar.make(alertRootLayoutSettings,msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Snackbar.make(alertRootLayoutSettings,msg,Snackbar.LENGTH_SHORT).show()
    }

    override fun progress(flag: Boolean) {
        //ToDo: To change body of created functions use File | Settings | File Templates.
    }

    override fun <T> navigateTo(clazz: Class<T>, bundle: Bundle?) {
        val mIntent = Intent(this,clazz)
        if (bundle != null) mIntent.putExtra(Constants.KEY_BUNDLE_PARAM,bundle)
        startActivity(mIntent)
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        ivGoToBack.setOnClickListener { finish() }
        tvLogout.setOnClickListener  {
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
        llChangePassword.setOnClickListener { mPresenter.showChangePasswordDialog() }
        llAboutUs.setOnClickListener {
            val mBundle = Bundle()
            mBundle.putString(Constants.KEY_TO_WHERE,Constants.URL_ABOUT)
            navigateTo(WebActivity::class.java,mBundle) }
        llTermsAndConditions.setOnClickListener {
            val mBundle = Bundle()
            mBundle.putString(Constants.KEY_TO_WHERE,Constants.URL_TERMS)
            navigateTo(WebActivity::class.java,mBundle) }
        llPrivacyPolicy.setOnClickListener {
            val mBundle = Bundle()
            mBundle.putString(Constants.KEY_TO_WHERE,Constants.URL_POLICY)
            navigateTo(WebActivity::class.java,mBundle) }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }
}
