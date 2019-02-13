package com.discount.views.ui.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.AppBarLayout
import android.support.design.widget.Snackbar
import android.view.View
import com.bumptech.glide.Glide
import com.discount.R
import com.discount.app.config.Constants
import com.discount.interactors.EditProfileInteractor
import com.discount.models.Country
import com.discount.presenters.EditProfilePresenter
import com.discount.views.DiscountView
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.app_bar_edit_profile.*
import kotlinx.android.synthetic.main.content_edit_profile.*

class EditProfileActivity : AppCompatActivity(),DiscountView {
    private val mPresenter = EditProfilePresenter(this, EditProfileInteractor())
    private var mCountry: Country? = null
    private var mGender: String = ""

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
        Snackbar.make(alertRootLayoutEditProfile,msg, Snackbar.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Snackbar.make(alertRootLayoutEditProfile,msg, Snackbar.LENGTH_SHORT).show()
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

        fabEditProfileImage.setOnClickListener {
            mPresenter.requestForStorageAccess()
        }

        appBarProfile.addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                fabEditProfileImage.show()
            } else {
                fabEditProfileImage.hide()
            }
        })

        mPresenter.onCreate(this)
        btnUpdate.setOnClickListener { mPresenter.validate(
            etFirstName.text.toString(),
            etLastName.text.toString(),
            etEmailId.text.toString(),
            etPhoneNo.text.toString(),
            tvCountryCode.text.toString(),
            tvDateOfBirth.text.toString(),
            mGender
        ) }

    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }

    fun onDateSelected(selectedDate: String) {
        tvDateOfBirth.text = selectedDate
    }

    fun radioButtonManager(gender: String) {
        if(gender == Constants.MALE) {
            mGender = Constants.MALE
            ivRadioMale.setImageResource(R.drawable.active_radio_ico)
            ivRadioFemale.setImageResource(R.drawable.inactive_radio_ico)
        } else {
            mGender = Constants.FEMALE
            ivRadioMale.setImageResource(R.drawable.inactive_radio_ico)
            ivRadioFemale.setImageResource(R.drawable.active_radio_ico)
        }

    }

    override fun onDestroy() {
        mPresenter.onDestroy()
        super.onDestroy()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mPresenter.onActivityResult(requestCode,resultCode,data)
        if (requestCode == Constants.PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {
            } else {
                ivProfileImage.setImageBitmap(null)
            }
        } else if (requestCode == Constants.CAPTURE_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {
            } else {
                ivProfileImage.setImageBitmap(null)
            }
        } else if (requestCode == UCrop.REQUEST_CROP) {
            if (resultCode == RESULT_OK) {
                ivProfileImage.setImageBitmap(null)
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, UCrop.getOutput(data!!))
                Glide.with(this).load(bitmap).into(ivProfileImage)
            } else {
                ivProfileImage.setImageBitmap(null)
            }
        } else if (requestCode == Constants.COUNTRY_REQUEST) {
            if(resultCode == Activity.RESULT_OK && data != null) {
                val mBundle = data.getBundleExtra(Constants.KEY_BUNDLE_PARAM)
                mCountry = mBundle.getSerializable(Constants.KEY_RESULT_EXTRA) as Country
                tvCountryCode.text = "+${mCountry?.phoneCode}"
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.STORAGE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mPresenter.showPickOptionsDialog()
            } else {
                mPresenter.requestForStorageAccess()
            }
        }
    }
}
