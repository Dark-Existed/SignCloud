package com.de.signcloud.bean

data class ValidateCode(val code: Int, val message: String, val data: String)

data class SignUpResult(val code: Int, val message: String, val data: String?)