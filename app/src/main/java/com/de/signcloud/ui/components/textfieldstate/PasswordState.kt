package com.de.signcloud.ui.components.textfieldstate

import com.de.signcloud.R
import com.de.signcloud.SignCloudApplication

class PasswordState :
    TextFieldState(validator = ::isPasswordValid, errorFor = ::passwordValidationError)

class ConfirmPasswordState(private val passwordState: PasswordState) : TextFieldState() {
    override val isValid
        get() = passwordAndConfirmationValid(passwordState.text, text)

    override fun getError(): String? {
        return if (showErrors()) {
            passwordConfirmationError()
        } else {
            null
        }
    }
}

private fun passwordAndConfirmationValid(password: String, confirmedPassword: String): Boolean {
    return isPasswordValid(password) && password == confirmedPassword
}

private fun isPasswordValid(password: String): Boolean {
    // TODO: 2021/3/21 add regex to check password complexity
    return password.length > 3
}


private fun passwordValidationError(password: String): String {
    // TODO: 2021/4/7 return the result why password invalidate
    return "Invalid password"
}


private fun passwordConfirmationError(): String {
    return SignCloudApplication.context.getString(R.string.password_dont_match)
}
