package com.discount.presenters

import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.os.Handler
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.FileProvider
import android.util.Patterns
import android.view.LayoutInflater
import com.bumptech.glide.Glide
import com.discount.BuildConfig
import com.discount.R
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.interactors.EditProfileInteractor
import com.discount.models.UserDetail
import com.discount.views.DiscountView
import com.discount.views.ui.activities.EditProfileActivity
import com.discount.views.ui.activities.SignUpActivity
import com.google.gson.Gson
import com.yalantis.ucrop.UCrop
import kotlinx.android.synthetic.main.app_bar_edit_profile.*
import kotlinx.android.synthetic.main.content_edit_profile.*
import kotlinx.android.synthetic.main.dialog_profile_image_options.view.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Avinash Kumar on 17/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class EditProfilePresenter(var mView: DiscountView?,var mInteractor: EditProfileInteractor): EditProfileInteractor.OnProfileUpdateListener {
    override fun onError(msg: String) {
        mView?.let {
            it.progress(false)
            it.onErrorOrInvalid(msg)
        }
    }

    override fun onSuccess(msg: String) {
        mView?.let {
            it.progress(false)
            it.onSuccess(msg)
            Handler().postDelayed(Runnable { (mView as EditProfileActivity).finish() },500)
        }
    }

    private var mCalender = Calendar.getInstance()
    private var mDatePickerDialog: DatePickerDialog? = null
    private var mListener: DatePickerDialog.OnDateSetListener? = null

    private var mUri: Uri? = null
    private var imageUri: Uri? = null

    init {
        mListener = DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            mCalender.set(year,monthOfYear,dayOfMonth)
            val month = if (monthOfYear < 10) "0${monthOfYear+1}" else (monthOfYear+1).toString()
            val day = if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth.toString()
            val mSelectedDate = "$day/$month/$year"
            (mView as EditProfileActivity).onDateSelected(mSelectedDate)
        }
    }

    fun onCreate(profile: EditProfileActivity) {
        val prefHelper = PrefHelper.instance
        val data = prefHelper?.getPref(Constants.USER_DETAILS,"")
        val userDetails = Gson().fromJson(data,UserDetail::class.java)
        userDetails?.run {
            Glide.with(mView as Context).load(profileImage).into(profile.ivProfileImage)
            profile.etFirstName.setText(firstName)
            profile.etLastName.setText(lastName)
            profile.etEmailId.setText(email)
            profile.tvDateOfBirth.text = dateOfBirth
            (mView as EditProfileActivity).radioButtonManager(gender)
            profile.tvCountryCode.text = countryCode
            profile.etPhoneNo.setText(phoneNumber)
            this
        }
    }

    fun showDatePicker() {
        mDatePickerDialog = DatePickerDialog(mView as EditProfileActivity,mListener,mCalender.get(Calendar.YEAR),mCalender.get(Calendar.MONTH),mCalender.get(Calendar.DAY_OF_MONTH))
        mDatePickerDialog?.show()
    }

    fun onDestroy() {
        mView = null
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == Constants.PICK_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK && data != null) {
                val options = UCrop.Options()
                options.setCompressionQuality(100)
                options.setMaxBitmapSize(10000)
                UCrop.of(data?.data, Uri.fromFile(File((mView as Context).cacheDir, ".jpg")))
                    .withAspectRatio(1f, 1f)
                    .withMaxResultSize(3120, 4160)
                    .withOptions(options)
                    .start((mView as Activity))
            } else {
                mUri = null
            }
        } else if (requestCode == Constants.CAPTURE_IMAGE_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                //data.data = getImageUri((mHomeView as Context),data?.extras.get("data") as Bitmap)
                val options = UCrop.Options()
                options.setCompressionQuality(100)
                options.setMaxBitmapSize(10000)
                UCrop.of(imageUri!!, Uri.fromFile(File((mView as Context).cacheDir, ".jpg")))
                    .withAspectRatio(1f, 1f)
                    .withMaxResultSize(3120, 4160)
                    .withOptions(options)
                    .start((mView as Activity))
            } else {
                mUri = null
            }
        } else if (requestCode == UCrop.REQUEST_CROP) {
            if (resultCode == Activity.RESULT_OK) {
                data?.data = UCrop.getOutput(data!!)
                mUri = data.data
            } else {
                mUri = null
            }
        }
    }

    fun requestForStorageAccess() {
        if (ContextCompat.checkSelfPermission(mView as Context, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(mView as Context, android.Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
            ContextCompat.checkSelfPermission(mView as Context, android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            showPickOptionsDialog()
            return
        } else {
            ActivityCompat.requestPermissions(mView as Activity, arrayOf(
                android.Manifest.permission.CAMERA,
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE),Constants.STORAGE_PERMISSION)
        }
    }

    private fun pickImageForProfileFromGallery() {
        val mIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        (mView as Activity).startActivityForResult(mIntent,Constants.PICK_IMAGE_REQUEST)
    }

    private fun captureProfileImageFromCamera() {
        val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss",Locale.getDefault()).format(Date())

        try {
            val storageDir = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM), "Camera")
            val mFile = File.createTempFile("IMAGE_$timeStamp",".jpg",storageDir)

            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                imageUri = FileProvider.getUriForFile(mView as Context, BuildConfig.APPLICATION_ID + ".provider",mFile)
                cameraIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION or Intent.FLAG_GRANT_WRITE_URI_PERMISSION)
            } else {
                imageUri = Uri.fromFile(mFile)
            }
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri)
            (mView as EditProfileActivity).startActivityForResult(cameraIntent, Constants.CAPTURE_IMAGE_REQUEST)
        } catch (e: Exception) {

        }
    }

    fun showPickOptionsDialog() {
        val view = LayoutInflater.from(mView as Context).inflate(R.layout.dialog_profile_image_options,null)
        val dialog = AlertDialog.Builder(mView as Context).setView(view).create()
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

    fun validate(firstName: String, lastName: String, email: String,
                 phoneNumber: String,countryCode: String ,dateOfBirth: String,gender: String) {
        mView?.let {
            if(firstName.length < 3) {
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.invalid_first_name))
                return
            }

            if(lastName.length < 3) {
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.invalid_last_name))
                return
            }

            if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
                it.onErrorOrInvalid((it as Context).resources.getString(R.string.invalid_email_id))
                return
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
            val pNumber = RequestBody.create(MediaType.parse("text/plain"),phoneNumber)
            val cCode = RequestBody.create(MediaType.parse("text/plain"),countryCode)
            val dob = RequestBody.create(MediaType.parse("text/plain"),dateOfBirth)
            val mGender = RequestBody.create(MediaType.parse("text/plain"),gender)

            mInteractor.updateUserProfile(fName, lName, mail, pNumber,
                cCode,dob,mGender,part,mListener = this)
            it.progress(true)
        }
    }
}