package com.discount.views.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.discount.R
import com.discount.app.config.Constants
import com.discount.interactors.PasswordInteractor
import com.discount.presenters.PasswordPresenter
import com.discount.views.DiscountView
import kotlinx.android.synthetic.main.activity_forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() , DiscountView{

    private val mPasswordPresenter = PasswordPresenter(this,PasswordInteractor())

    override fun <T> navigateTo(clazz: Class<T>, bundle: Bundle?) {
        val mIntent = Intent(this,clazz)
        if (bundle != null) mIntent.putExtra(Constants.KEY_BUNDLE_PARAM,bundle)
        startActivity(mIntent)
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
    }

    override fun onErrorOrInvalid(msg: String) {
        Snackbar.make(forgetPasswordRoot,msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Snackbar.make(forgetPasswordRoot,msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun progress(flag: Boolean) {
        if (flag) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)

        ivGoToBack.setOnClickListener { finish() }
        btnSend.setOnClickListener {mPasswordPresenter.validate(etEmailId.text.toString())}
    }

    override fun onDestroy() {
        mPasswordPresenter.onDestroy()
        super.onDestroy()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }
}
