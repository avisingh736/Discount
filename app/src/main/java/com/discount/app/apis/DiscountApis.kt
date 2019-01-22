package com.discount.app.apis

import com.discount.app.config.Constants
import com.discount.models.AuthResponse
import com.discount.models.UniversityResponse
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
    fun getUniversityList(@Header("auth_token") authToken: String
    ): Call<UniversityResponse>

    @FormUrlEncoded
    @POST(Constants.ADD_USER_UNIVERSITY)
    fun addUserUniversity(
        @Header("auth_token") authToken: String,
        @Field("university_name") universityName: String,
        @Field("student_id") studentId: String
    ): Call<UniversityResponse>
}