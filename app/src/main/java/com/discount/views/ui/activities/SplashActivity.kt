package com.discount.views.ui.activities

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Base64
import android.view.WindowManager
import com.discount.R
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.app.utils.MyLog
import com.discount.presenters.SplashPresenter
import com.discount.views.BaseView
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException


class SplashActivity : AppCompatActivity(),BaseView {
    private val mSplashPresenter = SplashPresenter(this)

    override fun <T> navigateTo(clazz: Class<T>, bundle: Bundle?) {
        val mIntent = Intent(this,clazz)
        if (bundle != null) mIntent.putExtra(Constants.KEY_BUNDLE_PARAM,bundle)
        startActivity(mIntent)
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)

        generateKeyHash()
        val prefHelper = PrefHelper.instance
        prefHelper?.savePref(Constants.DIALOG,true)
        mSplashPresenter.onStart()
    }

    override fun onDestroy() {
        mSplashPresenter.onDestroy()
        super.onDestroy()
    }

    private fun generateKeyHash() {
        try {
            val info = packageManager.getPackageInfo("com.discount", PackageManager.GET_SIGNATURES)
            for (signature in info.signatures) {
                val md = MessageDigest.getInstance("SHA")
                md.update(signature.toByteArray())
                MyLog.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT))
            }
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        }

    }
}
