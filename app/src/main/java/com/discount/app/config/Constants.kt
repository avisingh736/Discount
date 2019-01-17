package com.discount.app.config

/**
 * Created by Avinash Kumar on 12/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class Constants {
    companion object {
        /**
         * Urls below
         * */
        const val BASE_URL = "http://dev.mindiii.com/discount/api_v1/"

        /**
         *  Apis below
         * */
        const val SIGN_UP = "service/signup" //POST
        const val LOGIN = "service/login" //POST
        const val FORGOT_PASSWORD = "service/forgotPassword" //POST



        /**
         * Keys below
         * */
        const val SUCCESS = "Success"
        const val FAIL = "Fail"

        /**
         *  Preferences constants
         * */
        const val DIALOG = "dialog"
        const val IS_USER_LOGGED_IN = "is_user_logged_in"
        const val REMEMBER_ME = "remember_me"
        const val USER_DETAILS = "user_details"
        const val EMAIL = "email"
        const val PASSWORD = "password"

        /**
         *  Request code constants
         * */
        const val PICK_IMAGE_REQUEST = 1001
        const val CAPTURE_IMAGE_REQUEST = 1002
        const val STORAGE_PERMISSION = 1003
        const val PLACE_PICKER_REQUEST = 1004
    }
}