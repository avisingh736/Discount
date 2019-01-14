package com.discount.app

import android.app.Application
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.MediaStore
import com.crashlytics.android.Crashlytics
import com.discount.app.apis.DiscountApis
import com.discount.app.prefs.PrefHelper
import com.discount.app.config.Constants.Companion.BASE_URL
import io.fabric.sdk.android.Fabric
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory



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


        private var retrofit: Retrofit?= null
        fun getApis(): DiscountApis {
            val client = OkHttpClient.Builder().build()
            if (retrofit == null) {
                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit!!.create(DiscountApis::class.java)
        }

        fun getRealPathFromUri(mContext: Context, mUri: Uri): String {
            var cursor:  Cursor? = null
            try {
                val proj = arrayOf(MediaStore.Images.Media.DATA)
                cursor = mContext.contentResolver.query(mUri, proj, null, null, null)
                val cIndex = cursor?.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                cursor?.moveToFirst()
                return cursor?.getString(cIndex!!)!!
            } finally {
                cursor?.close()
            }
        }

    }
}