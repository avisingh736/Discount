package com.discount.app.apis

import com.discount.app.config.Constants
import com.discount.models.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

/**
 * Created by Avinash Kumar on 12/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
interface DiscountApis {

    @Multipart
    @POST(Constants.SIGN_UP)
    fun register(
        @Part("first_name") firstName: RequestBody,
        @Part("last_name") lastName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("cpassword") cPassword: RequestBody,
        @Part profileImage: MultipartBody.Part,
        @Part("device_type") deviceType: RequestBody,
        @Part("social_id") socialId: RequestBody,
        @Part("social_type") socialType: RequestBody
    ): Call<AuthResponse>


    @FormUrlEncoded
    @POST(Constants.LOGIN)
    fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("device_type") deviceType: String
    ): Call<AuthResponse>

    @FormUrlEncoded
    @POST(Constants.FORGOT_PASSWORD)
    fun forgotPassword(
        @Field("email") email: String
    ): Call<AuthResponse>

    @GET(Constants.UNIVERSITY_LIST)
    fun getUniversityList(
        @Header(Constants.AUTH_TOKEN) authToken: String
    ): Call<UniversityResponse>

    @FormUrlEncoded
    @POST(Constants.ADD_USER_UNIVERSITY)
    fun addUserUniversity(
        @Header(Constants.AUTH_TOKEN) authToken: String,
        @Field("university_name") universityName: String,
        @Field("student_id") studentId: String
    ): Call<UniversityResponse>

    @FormUrlEncoded
    @POST(Constants.COUPON_LIST)
    fun getCouponList(
        @Header(Constants.AUTH_TOKEN) authToken: String,
        @Field("latitude") latitude: String,
        @Field("longitude") longitude: String,
        @Field("search") search: String = "",
        @Field("limit") limit: String = "10",
        @Field("offset") offset: String = "0"
    ): Call<CouponResponse>

    @GET(Constants.CATEGORY_LIST)
    fun getCategories(
        @Header(Constants.AUTH_TOKEN) authToken: String
    ): Call<CategoryResponse>

    @GET(Constants.STORE_LIST)
    fun getStores(
        @Header(Constants.AUTH_TOKEN) authToken: String
    ): Call<StoreResponse>

    @FormUrlEncoded
    @POST(Constants.COUPON_INFO)
    fun getCouponInfo(
        @Header(Constants.AUTH_TOKEN) authToken: String,
        @Field("couponId") couponId: String
    ): Call<CouponInfoResponse>


    @FormUrlEncoded
    @POST(Constants.STORE_INFO)
    fun getStoreInfo(
        @Header(Constants.AUTH_TOKEN) authToken: String,
        @Field("storeId") storeId: String,
        @Field("limit") limit: String = "6",
        @Field("offset") offset: String = "0"
    ): Call<StoreInfoResponse>

    //ToDo: Model needs to be create for below

    @GET(Constants.MY_PROFILE)
    fun getMyProfile(
        @Header(Constants.AUTH_TOKEN) authToken: String
    ): Call<AuthResponse>

    @Multipart
    @POST(Constants.UPDATE_USER_PROFILE)
    fun updateUserProfile(
        @Header(Constants.AUTH_TOKEN) authToken: String,
        @Part("first_name") firstName: RequestBody,
        @Part("last_name") lastName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("phoneNumber") phoneNumber: RequestBody,
        @Part("country_code") countryCode: RequestBody,
        @Part("gender") gender: RequestBody,
        @Part("date_of_birth") dateOfBirth: RequestBody,
        @Part profileImage: MultipartBody.Part
    ): Call<AuthResponse>

    @FormUrlEncoded
    @POST(Constants.CHANGE_PASSWORD)
    fun changePassword(
        @Header(Constants.AUTH_TOKEN) authToken: String,
        @Field("oldPassword") oldPassword: String,
        @Field("newPassword") newPassword: String,
        @Field("cPassword") cPassword: String
    ): Call<AuthResponse>

    @FormUrlEncoded
    @POST(Constants.CONTACT_US)
    fun contactUs(
        @Header(Constants.AUTH_TOKEN) authToken: String,
        @Field("subject") subject: String,
        @Field("message") message: String,
        @Field("email") email: String
    ): Call<BaseResponse>

    @GET(Constants.SUBSCRIPTION_PLAN_LIST)
    fun getSubscriptionPlans(
        @Header(Constants.AUTH_TOKEN) authToken: String
    ): Call<SubscriptionResponse>

    @FormUrlEncoded
    @POST(Constants.REDEEM_COUPON)
    fun redeemCoupon(
        @Header(Constants.AUTH_TOKEN) authToken: String,
        @Field("couponId") couponId: String,
        @Field("storeId") storeId: String
    ): Call<BaseResponse>
}