package com.discount.views.ui.activities

import android.app.Activity
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import android.view.View
import com.discount.R
import com.discount.app.config.Constants
import com.discount.models.Country
import com.discount.presenters.EditProfilePresenter
import com.discount.views.BaseView
import com.discount.views.DiscountView
import kotlinx.android.synthetic.main.app_bar_edit_profile.*
import kotlinx.android.synthetic.main.content_edit_profile.*

class EditProfileActivity : AppCompatActivity(),DiscountView {
    private val mPresenter = EditProfilePresenter(this)
    private var mCountry: Country? = null

    override fun <T> navigateTo(clazz: Class<T>, bundle: Bundle?) {
        val mIntent = Intent(this,clazz)
        if (bundle != null) mIntent.putExtra(Constants.KEY_BUNDLE_PARAM,bundle)
        if (clazz == CountryActivity::class.java) {
            startActivityForResult(mIntent,Constants.COUNTRY_REQUEST)
        } else {
            startActivity(mIntent)
        }
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
    }

    override fun onErrorOrInvalid(msg: String) {
        //TODO("not implemented")
    }

    override fun onSuccess(msg: String) {
        //TODO("not implemented")
    }

    override fun progress(flag: Boolean) {
        progressBar.visibility = if (flag) View.VISIBLE else View.GONE
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_bar_edit_profile)

        ivGoToBack.setOnClickListener { finish() }

        llRadioMale.setOnClickListener { radioButtonManager(Constants.MALE) }
        llRadioFemale.setOnClickListener { radioButtonManager(Constants.FEMALE) }

        llDateOfBirth.setOnClickListener {
            mPresenter.showDatePicker()
        }

        tvCountryCode.setOnClickListener {
            navigateTo(CountryActivity::class.java)
        }

        appBarProfile.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                fabEditProfileImage.show()
            } else {
                fabEditProfileImage.hide()
            }
        })

        mPresenter.onCreate(this)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }

    fun onDateSelected(selectedDate: String) {
        tvDateOfBirth.text = selectedDate
    }

    private fun radioButtonManager(gender: String) {
        if(gender == Constants.MALE) {
            ivRadioMale.setImageResource(R.drawable.active_radio_ico)
            ivRadioFemale.setImageResource(R.drawable.inactive_radio_ico)
        } else {
            ivRadioMale.setImageResource(R.drawable.inactive_radio_ico)
            ivRadioFemale.setImageResource(R.drawable.active_radio_ico)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.COUNTRY_REQUEST) {
            if(resultCode == Activity.RESULT_OK && data != null) {
                val mBundle = data.getBundleExtra(Constants.KEY_BUNDLE_PARAM)
                mCountry = mBundle.getSerializable(Constants.KEY_RESULT_EXTRA) as Country
                tvCountryCode.text = "+${mCountry?.phoneCode}"
            }
        }
    }

    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
    }
}
