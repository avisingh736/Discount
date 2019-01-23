package com.discount.views.ui.activities

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.AppBarLayout
import com.discount.R
import com.discount.app.Discount
import com.discount.app.config.Constants
import kotlinx.android.synthetic.main.app_bar_edit_profile.*
import kotlinx.android.synthetic.main.content_edit_profile.*

class EditProfileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.app_bar_edit_profile)

        ivGoToBack.setOnClickListener { finish() }

        llRadioMale.setOnClickListener { radioButtonManager(Constants.MALE) }
        llRadioFemale.setOnClickListener { radioButtonManager(Constants.FEMALE) }

        tvCountryCode.setOnClickListener {
            startActivity(Intent(this@EditProfileActivity,CountryActivity::class.java))
        }

        appBarProfile.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                fabEditProfileImage.show()
            } else {
                fabEditProfileImage.hide()
            }
        })
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
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
}
