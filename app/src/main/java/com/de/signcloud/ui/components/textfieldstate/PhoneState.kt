package com.de.signcloud.ui.components.textfieldstate


import com.de.signcloud.R
import com.de.signcloud.SignCloudApplication
import java.util.regex.Pattern

private const val PHONE_VALIDATION_REGEX =
    "^(?:\\+?86)?1(?:3\\d{3}|5[^4\\D]\\d{2}|8\\d{3}|7(?:[0-35-9]\\d{2}|4(?:0\\d|1[0-2]|9\\d))|9[0-35-9]\\d{2}|6[2567]\\d{2}|4[579]\\d{2})\\d{6}\$"

class PhoneState(initText: String = "") :
    TextFieldState(
        initText = initText,
        validator = ::isPhoneValid,
        errorFor = ::phoneValidationError
    )


private fun phoneValidationError(phone: String): String {
    return SignCloudApplication.context.getString(R.string.invalid_phone) + phone
}

private fun isPhoneValid(email: String): Boolean {
    return Pattern.matches(PHONE_VALIDATION_REGEX, email)
}