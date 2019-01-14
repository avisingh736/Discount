package com.discount.app.prefs


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import com.discount.models.UserDetail
import com.google.gson.Gson

class PrefHelper @SuppressLint("CommitPrefEdits")
private constructor(context: Context) {

    private val sharedPreferences: SharedPreferences
    private val editor: SharedPreferences.Editor

    init {
        instance = this
        val prefsFile = context.packageName
        sharedPreferences = context.getSharedPreferences(prefsFile, Context.MODE_PRIVATE)
        editor = sharedPreferences.edit()
    }

    private fun delete(key: String) {
        if (sharedPreferences.contains(key)) {
            editor.remove(key).commit()
        }
    }

    fun remove(key: String): Boolean {
        if (sharedPreferences.contains(key)) {
            editor.remove(key).commit()
            return true
        }
        return false
    }

    fun removeAll() {
        editor.clear().commit()
    }

    fun savePref(key: String, value: Any?) {
        delete(key)
        when {
            value is Boolean -> editor.putBoolean(key, (value as Boolean?)!!)
            value is Int -> editor.putInt(key, (value as Int?)!!)
            value is Float -> editor.putFloat(key, (value as Float?)!!)
            value is Long -> editor.putLong(key, (value as Long?)!!)
            value is String -> editor.putString(key, value as String?)
            value is Enum<*> -> editor.putString(key, value.toString())
            value != null -> throw RuntimeException("Attempting to save non-primitive preference")
        }
        editor.commit()
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> getPref(key: String, defValue: T): T {
        val returnValue = sharedPreferences.all[key] as T?
        return returnValue ?: defValue
    }

    fun isPrefExists(key: String): Boolean {
        return sharedPreferences.contains(key)
    }

    companion object {

        @get:Synchronized
        var instance: PrefHelper? = null

        fun init(context: Context) {
            if (instance == null) {
                PrefHelper(context)
            }
        }

        /**
         * This method encode UserDetail object to json string
         * @param userDetail model class of user data
         * @return JSON string of model class
         * */
        fun encodeProfile(userDetail: UserDetail): String = Gson().toJson(userDetail)

        /**
         * This method decode json string to UserDetail object
         * @param userDetail json string of user data
         * @return UserDetail object of json string
         * */
        fun decodeProfile(userDetail: String) : UserDetail = Gson().fromJson(userDetail,UserDetail::class.java)

    }
}
