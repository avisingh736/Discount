package com.discount.views.ui.activities

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import com.discount.R
import com.discount.app.config.Constants
import com.discount.interactors.SignUpInteractor
import com.discount.presenters.SignUpPresenter
import com.discount.views.DiscountView
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), DiscountView {
    private val TAG = SignUpActivity::class.java.simpleName

    private val mSignUpPresenter = SignUpPresenter(this, SignUpInteractor())

    override fun progress(flag: Boolean) {
        if (flag) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
    }

    override fun <T> navigateTo(clazz: Class<T>, bundle: Bundle?) {
        /**
         * This method will navigate to home
         * */
        val mIntent = Intent(this,clazz).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        }
        if (bundle != null) mIntent.putExtra(Constants.KEY_BUNDLE_PARAM,bundle)
        startActivity(mIntent)
        overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
    }

    override fun onErrorOrInvalid(msg: String) {
        Snackbar.make(signUpActivityRoot,msg,Snackbar.LENGTH_SHORT).show()
    }

    override fun onSuccess(msg: String) {
        Snackbar.make(signUpActivityRoot,msg,Snackbar.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        ivGoToBack.setOnClickListener { finish() }
        tvGoToSignIn.setOnClickListener { finish() }
        ivProfileImage.setOnClickListener {
            mSignUpPresenter.requestForStorageAccess()
        }
        btnSignUp.setOnClickListener {
            mSignUpPresenter.validate(etFirstName.text.toString(),etLastName.text.toString(),
                etEmailId.text.toString(),etPassword.text.toString(),etConfirmPassword.text.toString())
        }
    }

    override fun onDestroy() {
        mSignUpPresenter.onDestroy()
        super.onDestroy()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.right_to_init,R.anim.init_to_right)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        mSignUpPresenter.onActivityResult(requestCode,resultCode,data)
        if (requestCode == Constants.PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                llSelectImage.alpha = 0f
            } else {
                llSelectImage.alpha = 1f
                ivProfileImage.setImageBitmap(null)
            }
        } else if (requestCode == Constants.CAPTURE_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                llSelectImage.alpha = 0f
            } else {
                llSelectImage.alpha = 1f
                ivProfileImage.setImageBitmap(null)
            }
        } else if (requestCode == UCrop.REQUEST_CROP) {
            if (resultCode == RESULT_OK) {
                ivProfileImage.setImageBitmap(null)
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, UCrop.getOutput(data!!))
                Glide.with(this).load(bitmap).into(ivProfileImage)
                llSelectImage.alpha = 0f
            } else {
                ivProfileImage.setImageBitmap(null)
                llSelectImage.alpha = 1f
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.STORAGE_PERMISSION) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mSignUpPresenter.showPickOptionsDialog()
            } else {
                mSignUpPresenter.requestForStorageAccess()
            }
        }
    }
}
