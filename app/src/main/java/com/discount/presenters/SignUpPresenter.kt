package com.discount.presenters

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Environment
import android.provider.MediaStore
import android.provider.MediaStore.Images
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.util.Patterns
import android.view.LayoutInflater
import com.discount.R
import com.discount.app.config.Constants
import com.discount.interactors.SignUpInteractor
import com.discount.views.DiscountView
import com.discount.views.ui.activities.HomeActivity
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.dialog_profile_image_options.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Avinash Kumar on 12/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class SignUpPresenter(var mDiscountView: DiscountView?, var mInteractor: SignUpInteractor): SignUpInteractor.OnRegistrationFinishedListener {
    private val TAG = SignUpPresenter::class.java.simpleName

    private var mUri: Uri? = null
    private var imageUri: Uri? = null

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

            val mFile = File(mUri?.path)
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
            if (resultCode == Activity.RESULT_OK && data != null) {
                data.data = getImageUri((mDiscountView as Context),data?.extras.get("data") as Bitmap)
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
        val file = File(Environment.getExternalStorageDirectory(), "/Discount/Images/photo_$timeStamp.jpg")
        imageUri = FileProvider.getUriForFile(mDiscountView as Context,"com.discount.fileprovider",file)
        val takePicture = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        takePicture.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
        (mDiscountView as Activity).startActivityForResult(takePicture, Constants.CAPTURE_IMAGE_REQUEST)
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

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }
}