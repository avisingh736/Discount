package com.discount.app.config

/**
 * Created by Avinash Kumar on 12/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
class Constants {
    companion object {
        /**
         *  Retrofit constants
         * */
        const val CONNECTION_TIMEOUT: Long = 5*60
        const val READ_TIMEOUT: Long = 5*60
        /**
         * Urls below
         * */
        const val BASE_URL_DEVELOPMENT = "http://dev.mindiii.com/discount/api_v1/"
        const val BASE_URL = "https://discoount.com/api_v1/"
        const val AUTH_TOKEN = "Auth-Token"

        /**
         *  Apis below
         * */
        const val SIGN_UP = "service/signup" //POST
        const val LOGIN = "service/login" //POST
        const val FORGOT_PASSWORD = "service/forgotPassword" //POST
        const val UNIVERSITY_LIST = "university/getUniversityList" //GET
        const val ADD_USER_UNIVERSITY = "university/addUserUniversity" //POST
        const val COUPON_LIST = "coupon/getCouponList" //POST
        const val CATEGORY_LIST = "category/getCategoryList" //GET
        const val STORE_LIST = "store/getStoreList"  //GET
        const val COUPON_INFO = "coupon/couponInfo" //POST
        const val STORE_INFO = "store/storeInfo" //POST



        /**
         * Keys below
         * */
        const val KEY_SUCCESS = "Success"
        const val KEY_FAIL = "Fail"
        const val MALE = "male"
        const val FEMALE = "female"
        const val KEY_RESULT_EXTRA = "result_extra"
        const val KEY_BUNDLE_PARAM = "extra_data"
        const val KEY_COUPON_EXTRA = "coupon_extra"
        const val KEY_COUPON_INFO_EXTRA = "coupon_info_extra"
        const val KEY_STORE_EXTRA = "store_extra"
        const val KEY_CATEGORY_EXTRA = "category_extra"
        const val KEY_FROM_WHERE = "from_where"
        const val KEY_FROM_CATEGORY = "from_coupon_category"
        const val KEY_FROM_COUPON_INFO = "from_coupon_info"
        const val KEY_FROM_STORE_LIST = "from_store_list"


        /**
         *  Preferences constants
         * */
        const val DIALOG = "mDialog"
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
        const val LOCATION_PERMISSION = 1004
        const val PLACE_PICKER_REQUEST = 1005
        const val COUPON_FILTER_REQUEST = 1006
    }
}