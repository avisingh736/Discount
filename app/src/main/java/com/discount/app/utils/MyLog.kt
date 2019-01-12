package com.discount.app.utils

import android.util.Log
import com.discount.app.Discount.Companion.isLogEnable

/**
 * Created by Avinash Kumar on 12/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class MyLog {
    companion object {
        fun v(tag: String,msg: String) {
            if (isLogEnable) Log.v(tag,msg)
        }

        fun d(tag: String,msg: String) {
            if (isLogEnable) Log.d(tag,msg)
        }

        fun i(tag: String,msg: String) {
            if (isLogEnable) Log.i(tag,msg)
        }

        fun w(tag: String,msg: String) {
            if (isLogEnable) Log.w(tag,msg)
        }

        fun e(tag: String,msg: String) {
            if (isLogEnable) Log.e(tag,msg)
        }

        fun e(tag: String,msg: String,t:Throwable) {
            if (isLogEnable) Log.e(tag,msg,t)
        }
    }
}