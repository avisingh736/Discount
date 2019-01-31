package com.discount.models

import com.google.gson.annotations.SerializedName
import java.io.Serializable

/**
 * Created by Avinash Kumar on 12/1/19.
 * At: Mindiii Systems Pvt. Ltd.
 * Mail: avinash.mindiii@gmail.com
 */
data class UserDetail(
    @SerializedName("userId") var userId: String,
    @SerializedName("first_name") var firstName: String,
    @SerializedName("last_name") var lastName: String,
    @SerializedName("full_name") var fullName: String,
    @SerializedName("user_role") var userRole: String,
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String,
    @SerializedName("profile_image") var profileImage: String,
    @SerializedName("address") var address: String,
    @SerializedName("latitude") var latitude: String,
    @SerializedName("longitude") var longitude: String,
    @SerializedName("type") var type: String,
    @SerializedName("plan") var plan: String,
    @SerializedName("status") var status: String,
    @SerializedName("social_id") var socialId: String,
    @SerializedName("social_type") var socialType: String,
    @SerializedName("device_token") var deviceToken: String,
    @SerializedName("device_type") var deviceType: String,
    @SerializedName("auth_token") var authToken: String,
    @SerializedName("university_id") var universityId: String,
    @SerializedName("student_id") var studentId: String,
    @SerializedName("gender") var gender: String,
    @SerializedName("date_of_birth") var dateOfBirth: String,
    @SerializedName("phoneNumber") var phoneNumber: String,
    @SerializedName("country_code") var countryCode: String,
    @SerializedName("created_on") var createdOn: String,
    @SerializedName("updated_on") var updatedOn: String
): Serializable