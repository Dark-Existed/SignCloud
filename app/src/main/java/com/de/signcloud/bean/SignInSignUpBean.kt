package com.de.signcloud.bean

data class ValidateCodeResult(val code: Int, val message: String, val data: String)

data class SignUpResult(val code: Int, val message: String, val data: String?)

data class SignInResult(val code: Int, val message: String, val data: Data?) {
    data class Data(val userinfo: UserInfo, val token: String)
    data class UserInfo(
        val userName: String,
        val phone: String,
        val cover: String?,
        // TODO: 2021/4/13 make clear why roles is []
        val roles: String
    )

}