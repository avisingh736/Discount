package com.discount.app

import android.app.Activity
import android.app.Application
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import android.util.TypedValue
import com.crashlytics.android.Crashlytics
import com.discount.R
import com.discount.app.apis.DiscountApis
import com.discount.app.config.Constants
import com.discount.app.config.Constants.Companion.BASE_URL
import com.discount.app.config.Constants.Companion.BASE_URL_DEVELOPMENT
import com.discount.app.prefs.PrefHelper
import com.discount.models.UserDetail
import com.discount.views.ui.activities.SignInActivity
import com.google.gson.Gson
import io.fabric.sdk.android.Fabric
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


/**
 * Created by Avinash Kumar on 12/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class Discount: Application() {

    override fun onCreate() {
        super.onCreate()
        Fabric.with(this, Crashlytics())
        PrefHelper.init(this)
    }

    companion object {
        /**
         *  Make logs enable and disable from here
         * */
        const val isLogEnable = true

        /**
         *  Retrofit configuration
         * */
        private var retrofit: Retrofit?= null
        fun getApis(): DiscountApis {
            val client = OkHttpClient.Builder()
                .connectTimeout(Constants.CONNECTION_TIMEOUT,TimeUnit.SECONDS)
                .readTimeout(Constants.READ_TIMEOUT,TimeUnit.SECONDS)
                .build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(DiscountApis::class.java)
        }

        /**
         *  Current user session
         * */
        private var userDetail: UserDetail? = null
        fun getSession(): UserDetail {
            if (userDetail == null) {
                val s = PrefHelper.instance?.getPref(Constants.USER_DETAILS,"")
                userDetail = Gson().fromJson(s, UserDetail::class.java)
            }
            return userDetail!!
        }

        /**
         *  Remove current session on logout
         * */
        fun removeSession() {
            userDetail = null
        }

        /**
         *  Convert dp to pixels
         * */
        fun dpToPx(mContext: Context, dp: Float): Int = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, mContext.resources.displayMetrics)).toInt()

        fun logout(context: Context) {
            Discount.removeSession()
            val prefHelper = PrefHelper.instance
            prefHelper?.run {
                savePref(Constants.IS_USER_LOGGED_IN,false)
                remove(Constants.USER_DETAILS)
            }
            val mIntent = Intent(context, SignInActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(mIntent)
            (context as Activity).finish()
            (context as Activity).overridePendingTransition(R.anim.init_to_left,R.anim.left_to_init)
        }
    }
}