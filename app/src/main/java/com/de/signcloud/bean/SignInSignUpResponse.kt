package com.de.signcloud.bean

import com.google.gson.annotations.SerializedName

class ValidateCodeResponse(val code: Int, val message: String, val data: String)

class SignUpResponse(val code: Int, val message: String, val data: String?)

class SignInResponse(val code: Int, val message: String, val data: Data?) {
    class Data(
        @SerializedName("userinfo") val userInfo: UserInfo,
        val token: String
    )

    class UserInfo(
        @SerializedName("username") val userName: String,
        val phone: String,
        val avatar: String?,
        val roles: List<String>,
        val defaultRole: String
    )
}

class ResetPasswordResponse()