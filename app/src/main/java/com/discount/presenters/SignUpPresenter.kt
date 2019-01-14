package com.discount.presenters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Patterns
import com.discount.R
import com.discount.app.Discount
import com.discount.app.config.Constants
import com.discount.interactors.SignUpInteractor
import com.discount.views.DiscountView
import com.discount.views.ui.activities.HomeActivity
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

/**
 * Created by Avinash Kumar on 12/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class SignUpPresenter(var mDiscountView: DiscountView?, var mInteractor: SignUpInteractor): SignUpInteractor.OnRegistrationFinishedListener {
    private val TAG = SignUpPresenter::class.java.simpleName

    private var mUri: Uri? = null

    override fun onError(msg: String) {
        mDiscountView?.let {
            it.progress(false)
            it.onErrorOrInvalid(msg)
        }
    }

    override fun onSuccess(msg: String) {
        mDiscountView?.let {
            it.progress(false)
            it.onSuccess(msg)
            it.navigateTo(HomeActivity::class.java)
            (it as Activity).finish()
        }
    }

    fun onDestroy() {
        mDiscountView = null
    }

    fun validate(firstName: String, lastName: String, email: String,
                 password: String, cPassword: String) {
        mDiscountView?.let {
            if(firstName.isEmpty() || firstName.length < 3) {
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.invalid_first_name))
                return
            }

            if(lastName.isEmpty() || lastName.length < 3) {
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.invalid_last_name))
                return
            }

            if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.invalid_email_id))
                return
            }

            if(password.isEmpty() || password.length < 6) {
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.invalid_password))
                return
            }

            if(password != cPassword) {
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.password_not_matching))
                return
            }

            if(mUri == null) {
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.profile_image_not_selected))
                return
            }

            val mFile = File(Discount.getRealPathFromUri(it as Context, mUri!!))
            val mRequestBody = RequestBody.create(MediaType.parse("image/*"),mFile)
            val part = MultipartBody.Part.createFormData("profile_image",mFile.name,mRequestBody)

            val fName = RequestBody.create(MediaType.parse("text/plain"),firstName)
            val lName = RequestBody.create(MediaType.parse("text/plain"),lastName)
            val mail = RequestBody.create(MediaType.parse("text/plain"),email)
            val pass = RequestBody.create(MediaType.parse("text/plain"),password)
            val cPass = RequestBody.create(MediaType.parse("text/plain"),cPassword)
            val dType = RequestBody.create(MediaType.parse("text/plain"),"2")
            val sId = RequestBody.create(MediaType.parse("text/plain"),"")
            val sType = RequestBody.create(MediaType.parse("text/plain"),"")

            mInteractor.register(fName, lName, mail, pass,
                cPass,part,dType,sId,sType,mListener = this)
            it.progress(true)
        }
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) mUri = data.data
        }
    }

    fun requestForStorageAccess() {
        if (ContextCompat.checkSelfPermission(mDiscountView as Context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            pickImageForProfile()
            return
        } else {
            ActivityCompat.requestPermissions(mDiscountView as Activity, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),Constants.STORAGE_PERMISSION)
        }
    }

    fun pickImageForProfile() {
        val mIntent = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        (mDiscountView as Activity).startActivityForResult(mIntent,Constants.PICK_IMAGE_REQUEST)
    }
}