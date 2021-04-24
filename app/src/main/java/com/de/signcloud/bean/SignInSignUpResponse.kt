package com.de.signcloud.bean

import com.google.gson.annotations.SerializedName

class IsPhoneExistResponse(val code: Int, val message: String, val data: Boolean)

class ValidateCodeResponse(val code: Int, val message: String, val data: String)

class SignUpResponse(val code: Int, val message: String, val data: String?)

class SignInResponse(val code: Int, val message: String, val data: Data?) {
    class Data(
        val userInfo: UserInfo?,
        val token: String?,
        val githubId: Int?
    )

    class UserInfo(
        val phone: String,
        @SerializedName("username") val userName: String,
        val avatar: String?,
        val roles: List<String>,
        val defaultRole: String
    )
}


class ResetPasswordResponse(val code: Int, val message: String, val data: String)