package com.discount.presenters

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.util.Patterns
import android.view.LayoutInflater
import com.discount.BuildConfig
import com.discount.R
import com.discount.app.config.Constants
import com.discount.app.utils.MyLog
import com.discount.interactors.SignUpInteractor
import com.discount.views.DiscountView
import com.discount.views.ui.activities.HomeActivity
import com.discount.views.ui.activities.SignUpActivity
import com.facebook.*
import com.facebook.login.LoginResult
import com.facebook.login.widget.LoginButton
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.activity_sign_up.*
import kotlinx.android.synthetic.main.dialog_profile_image_options.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Avinash Kumar on 12/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class SignUpPresenter(var mDiscountView: DiscountView?, var mInteractor: SignUpInteractor):
    SignUpInteractor.OnRegistrationFinishedListener {
    private val TAG = SignUpPresenter::class.java.simpleName

    private var mUri: Uri? = null
    private var imageUri: Uri? = null

    private var fbButton: LoginButton? = null
    private var callbackManager: CallbackManager? = null

    init {
        callbackManager = CallbackManager.Factory.create()
    }

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
        fbButton = null
    }

    fun validate(firstName: String, lastName: String, email: String,
                 password: String, cPassword: String) {
        mDiscountView?.let {
            if(firstName.isEmpty() || firstName.length < 3) {
                if(firstName.isEmpty()) {
                    it.onErrorOrInvalid((it as Context).resources.getString(R.string.please_enter_first_name))
                    (mDiscountView as SignUpActivity).etFirstName.requestFocus()
                    return
                }
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.invalid_first_name))
                return
            }

            if(lastName.isEmpty() || lastName.length < 3) {
                if (lastName.isEmpty()) {
                    (mDiscountView as SignUpActivity).etLastName.requestFocus()
                    it.onErrorOrInvalid((it as Context).resources.getString(R.string.please_enter_last_name))
                    return
                }
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.invalid_last_name))
                return
            }

            if(email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                if(email.isEmpty()) {
                    (mDiscountView as SignUpActivity).etEmailId.requestFocus()
                    it.onErrorOrInvalid((it as Context).resources.getString(R.string.please_enter_email_id))
                    return
                }
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.invalid_email_id))
                return
            }

            if(password.isEmpty() || password.length < 6) {
                if (password.isEmpty()) {
                    (mDiscountView as SignUpActivity).etPassword.requestFocus()
                    it.onErrorOrInvalid((it as Context).resources.getString(R.string.please_enter_password))
                    return
                }
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.invalid_password))
                return
            }

            if (cPassword.isEmpty() || password != cPassword) {
                if (cPassword.isEmpty()) {
                    (mDiscountView as SignUpActivity).etConfirmPassword.requestFocus()
                    it.onErrorOrInvalid((it as Context).resources.getString(R.string.please_confirm_password))
                    return
                }
                if(password != cPassword) {
                    it.onErrorOrInvalid((it as Context).resources.getString(R.string.password_does_not_match))
                    return
                }
            }


            val part = if(mUri != null) {
                val mFile = File(mUri?.path)
                val mRequestBody = RequestBody.create(MediaType.parse("image/*"),mFile)
                MultipartBody.Part.createFormData("profile_image",mFile.name,mRequestBody)
            } else {
                val mRequestBody = RequestBody.create(MultipartBody.FORM,"")
                MultipartBody.Part.createFormData("profile_image","",mRequestBody)
            }

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
        callbackManager?.onActivityResult(requestCode,resultCode,data)
        if (requestCode == Constants.PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val options = UCrop.Options()
                options.setCompressionQuality(100)
                options.setMaxBitmapSize(10000)
                UCrop.of(data?.data, Uri.fromFile(File((mDiscountView as Context).cacheDir, ".jpg")))
                    .withAspectRatio(1f, 1f)
                    .withMaxResultSize(3120, 4160)
                    .withOptions(options)
                    .start((mDiscountView as Activity))
            } else {
                mUri = null
            }
        } else if (requestCode == Constants.CAPTURE_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                //data.data = getImageUri((mHomeView as Context),data?.extras.get("data") as Bitmap)
                val options = UCrop.Options()
                options.setCompressionQuality(100)
                options.setMaxBitmapSize(10000)
                UCrop.of(imageUri!!, Uri.fromFile(File((mDiscountView as Context).cacheDir, ".jpg")))
                    .withAspectRatio(1f, 1f)
                    .withMaxResultSize(3120, 4160)
                    .withOptions(options)
                    .start((mDiscountView as Activity))
            } else {
                mUri = null
            }
        } else if (requestCode == UCrop.REQUEST_CROP) {
            if (resultCode == RESULT_OK) {
                data?.data = UCrop.getOutput(data!!)
                mUri = data.data
            } else {
                mUri = null
            }
        }
    }

    fun requestForStorageAccess() {
        if (ContextCompat.checkSelfPermission(mDiscountView as Context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(mDiscountView as Context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(mDiscountView as Context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            showPickOptionsDialog()
            return
        } else {
            ActivityCompat.requestPermissions(mDiscountView as Activity, arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE),Constants.STORAGE_PERMISSION)
        }
    }

    private fun pickImageForProfileFromGallery() {
        val mIntent = Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        (mDiscountView as Activity).startActivityForResult(mIntent,Constants.PICK_IMAGE_REQUEST)
    }

    private fun captureProfileImageFromCamera() {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(Date())

        try {
            val storageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera")
            val mFile = File.createTempFile("IMAGE_$timeStamp",".jpg",storageDir)

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(mDiscountView as Context, BuildConfig.APPLICATION_ID + ".provider",mFile)
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            } else {
                imageUri = Uri.fromFile(mFile)
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
            (mDiscountView as SignUpActivity).startActivityForResult(cameraIntent, Constants.CAPTURE_IMAGE_REQUEST)
        } catch (e: Exception) {

        }
    }

    fun showPickOptionsDialog() {
        val view = LayoutInflater.from(mDiscountView as Context).inflate(R.layout.dialog_profile_image_options,null)
        val dialog = AlertDialog.Builder(mDiscountView as Context).setView(view).create()
        view.tvCamera.setOnClickListener{
            captureProfileImageFromCamera()
            dialog.dismiss()
        }
        view.tvGallery.setOnClickListener {
            pickImageForProfileFromGallery()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun initFacebookButton(mButton: LoginButton) {
        fbButton = mButton
        fbButton?.setReadPermissions(mutableListOf("user_photos", "email", "user_birthday", "public_profile"))
        fbButton?.registerCallback(callbackManager,object : FacebookCallback<LoginResult>{
            override fun onSuccess(result: LoginResult?) {
                val mRequest = GraphRequest.newMeRequest(result?.accessToken) { `object`, response ->
                    MyLog.i(TAG,response.toString())

                    val fName = RequestBody.create(MediaType.parse("text/plain"),`object`?.getString("first_name")!!)
                    val lName = RequestBody.create(MediaType.parse("text/plain"),`object`?.getString("last_name")!!)
                    val mail = RequestBody.create(MediaType.parse("text/plain"),`object`?.getString("email")!!)
                    val dType = RequestBody.create(MediaType.parse("text/plain"),"2")
                    val sId = RequestBody.create(MediaType.parse("text/plain"),`object`?.getString("id"))
                    val sType = RequestBody.create(MediaType.parse("text/plain"),"facebook")
                    mInteractor.register(firstName = fName,lastName =  lName,email =  mail,deviceType = dType,
                        socialId = sId, socialType = sType,mListener = this@SignUpPresenter)
                    mDiscountView?.progress(true)
                }

                val mBundle = Bundle()
                mBundle.putString("fields","id,first_name,last_name,email,gender,birthday")
                mRequest.parameters = mBundle
                mRequest.executeAsync()
            }

            override fun onError(error: FacebookException?) {
                MyLog.e(TAG,"Error with facebook login",error!!)
            }

            override fun onCancel() {
                MyLog.i(TAG, "Login cancelled")
            }
        })
    }

    fun continueWithFacebook() {
        fbButton?.performClick()
    }
}