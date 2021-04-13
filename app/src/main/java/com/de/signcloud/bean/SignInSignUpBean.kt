package com.de.signcloud.bean

import com.google.gson.annotations.SerializedName

data class ValidateCodeResult(val code: Int, val message: String, val data: String)

data class SignUpResult(val code: Int, val message: String, val data: String?)

data class SignInResult(val code: Int, val message: String, val data: Data?) {

    data class Data(
        @SerializedName("userinfo") val userInfo: UserInfo,
        val token: String
    )

    data class UserInfo(
        val userName: String,
        val phone: String,
        val avatar: String?,
        val roles: List<String>,
        val defaultRole: String
    )
}