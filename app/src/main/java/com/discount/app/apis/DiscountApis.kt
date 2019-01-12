package com.discount.app.apis

import com.discount.app.config.Constants
import com.discount.models.AuthenticationResponse
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
    @POST(Constants.SIGN_UP) fun register(
        @Part("first_name") firstName: RequestBody,
        @Part("last_name") lastName: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody,
        @Part("cpassword") cPassword: RequestBody,
        @Part profileImage: MultipartBody.Part,
        @Part("device_type") deviceType: RequestBody,
        @Part("social_id") socialId: RequestBody,
        @Part("social_type") socialType: RequestBody
        ): Call<AuthenticationResponse>


    @FormUrlEncoded
    @POST(Constants.LOGIN) fun login(
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("device_type") deviceType: String
    ): Call<AuthenticationResponse>

    @POST(Constants.FORGOT_PASSWORD) fun forgotPassword(
        @Field("email") email: String
    ): Call<AuthenticationResponse>
}