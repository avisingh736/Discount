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
         *  Request code constants
         * */
        const val PICK_IMAGE_REQUEST = 1001
    }
}