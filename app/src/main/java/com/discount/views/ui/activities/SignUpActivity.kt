package com.discount.views.ui.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import com.discount.R
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.interactors.SignUpInteractor
import com.discount.presenters.SignUpPresenter
import com.discount.views.AuthenticationView
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(), AuthenticationView {
    private val TAG = SignUpActivity::class.java.simpleName

    val mSignUpPresenter = SignUpPresenter(this, SignUpInteractor())

    override fun navigateTo() {
        /**
         * This method will navigate to
         * */
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
        ivEditProfileImage.setOnClickListener {
            val mIntent = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(mIntent,Constants.PICK_IMAGE_REQUEST)
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
        mSignUpPresenter.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) ivProfileImage.setImageURI(data?.data)
        }
    }
}
