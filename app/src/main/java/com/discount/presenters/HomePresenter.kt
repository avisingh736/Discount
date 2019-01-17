package com.discount.presenters

import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import com.discount.R
import com.discount.app.config.Constants
import com.discount.app.prefs.PrefHelper
import com.discount.views.DiscountView
import kotlinx.android.synthetic.main.dialog_student.view.*

/**
 * Created by Avinash Kumar on 15/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class HomePresenter(var mDiscountView: DiscountView?) {


    fun onDestroy() {
        mDiscountView = null
    }

    fun showStudentDialog() {
        val view = LayoutInflater.from(mDiscountView as Context).inflate(R.layout.dialog_student,null)
        val dialog = AlertDialog.Builder(mDiscountView as Context).setView(view).create()
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        view.fabClose.setOnClickListener {
            val prefHelper = PrefHelper.instance
            prefHelper?.savePref(Constants.DIALOG,false)
            dialog.dismiss()
        }
        dialog.show()
    }

}