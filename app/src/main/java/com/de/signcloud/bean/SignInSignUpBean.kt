package com.de.signcloud.bean

data class ValidateCodeResult(val code: Int, val message: String, val data: String)

data class SignUpResult(val code: Int, val message: String, val data: String?)

data class SignInResult(val code: Int, val message: String, val data: Data?) {
    data class Data(val token: String)
}