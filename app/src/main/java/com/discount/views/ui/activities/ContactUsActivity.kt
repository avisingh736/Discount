package com.discount.views.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.discount.R
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.presenters.ContactUsPresenter
import com.discount.views.DiscountView
import kotlinx.android.synthetic.main.activity_contact_us.*

class ContactUsActivity : AppCompatActivity(), DiscountView {
    private val mPresenter = ContactUsPresenter(this)

    override fun onErrorOrInvalid(msg: String) {
        Snackbar.make(alertRootLayoutContactUs,msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Snackbar.make(alertRootLayoutContactUs,msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun progress(flag: Boolean) {
        progressBar.visibility = if (flag) View.VISIBLE else View.GONE
    }

    override fun <T> navigateTo(clazz: Class<T>, bundle: Bundle?) {
        val mIntent = Intent(this,clazz)
        if (bundle != null) mIntent.putExtra(Constants.KEY_BUNDLE_PARAM,bundle)
        startActivity(mIntent)
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_contact_us)

        ivGoToBack.setOnClickListener { finish() }
        etEmailId.setText(Discount.getSession().email)
        btnSubmit.setOnClickListener {
            mPresenter.validate(etEmailId.text.toString(),
                etSubject.text.toString(),
                etMessage.text.toString())
        }
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }
}
