package com.discount.presenters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.widget.AdapterView
import com.discount.R
import com.discount.adapters.UniversityAdapter
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.app.utils.MyLog
import com.discount.interactors.HomeInteractor
import com.discount.models.University
import com.discount.models.UserDetail
import com.discount.views.DiscountView
import com.google.gson.Gson
import kotlinx.android.synthetic.main.dialog_student.view.*

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class HomePresenter(var mDiscountView: DiscountView?,var mInteractor: HomeInteractor): HomeInteractor.OnResponseListener {
    private val TAG = HomePresenter::class.java.simpleName

    var mDialog: AlertDialog? = null
    var selectedPosition: Int = 0
    var universities: MutableList<University>? = null

    fun studentDialogStatus() {
        val prefHelper = PrefHelper.instance
        if(prefHelper?.getPref(Constants.DIALOG,false)!!) {
            val s = prefHelper.getPref(Constants.USER_DETAILS,"")
            val userDetail = Gson().fromJson(s,UserDetail::class.java)
            if (userDetail != null)
                mInteractor.getUniversityListFromServer(userDetail.authToken,this)
        }
    }

    override fun showStudentDialog(universities: MutableList<University>) {
        universities.add(0,University("","",""))
        this.universities = universities
        val dialogView = LayoutInflater.from(mDiscountView as Context).inflate(R.layout.dialog_student,null)
        mDialog = AlertDialog.Builder(mDiscountView as Context).setView(dialogView).create()
        mDialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialogView.fabClose.setOnClickListener {
            val prefHelper = PrefHelper.instance
            prefHelper?.savePref(Constants.DIALOG,false)
            mDialog?.dismiss()
        }
        dialogView.spUniversityName.adapter = UniversityAdapter(mDiscountView as Context,universities)
        dialogView.spUniversityName.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, p3: Long) {
                selectedPosition = position
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {

            }
        }
        dialogView.btnAdd.setOnClickListener { validate(dialogView) }
        mDialog?.show()
    }

    override fun onUniversityAdded(msg: String) {
        if (mDialog != null) {
            mDialog?.dismiss()
        }
    }

    private fun validate(dialogView: View) {
        if (selectedPosition == 0) {
            dialogView.tilUniversityName.error = (mDiscountView as Context).getString(R.string.please_select_university)
            return
        } else {
            dialogView.tilUniversityName.error = null
        }

        val mStudentId = dialogView.etStudentId.text.toString()
        if (mStudentId.isEmpty()) {
            dialogView.tilStudentId.error = (mDiscountView as Context).getString(R.string.please_enter_student_id)
            dialogView.etStudentId.requestFocus()
            return
        } else {
            dialogView.tilStudentId.error = null
        }

        val prefHelper = PrefHelper.instance
        if(prefHelper?.getPref(Constants.DIALOG,false)!!) {
            val s = prefHelper.getPref(Constants.USER_DETAILS,"")
            val userDetail = Gson().fromJson(s,UserDetail::class.java)
            val uName = universities?.get(selectedPosition)?.universityName!!
            if (userDetail != null)
                mInteractor.addUserUniversityToServer(userDetail.authToken,uName,mStudentId,this)
        }
    }

    override fun onError(msg: String) {
        mDiscountView?.onErrorOrInvalid(msg)
    }

    fun onDestroy() {
        mDiscountView = null
    }

}