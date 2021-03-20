package com.de.signcloud.signinsignup


import android.content.res.Resources
import androidx.compose.ui.platform.LocalContext
import com.de.signcloud.R
import java.util.regex.Pattern

private const val PHONE_VALIDATION_REGEX =
    "^(?:\\+?86)?1(?:3\\d{3}|5[^4\\D]\\d{2}|8\\d{3}|7(?:[0-35-9]\\d{2}|4(?:0\\d|1[0-2]|9\\d))|9[0-35-9]\\d{2}|6[2567]\\d{2}|4[579]\\d{2})\\d{6}\$"

class PhoneState : TextFieldState(validator = ::isPhoneValid, errorFor = ::phoneValidationError)


private fun phoneValidationError(phone: String): String {
    // TODO: 2021/3/20 get string from resource by context
    return "Invalid phone: $phone"
}

private fun isPhoneValid(email: String): Boolean {
    return Pattern.matches(PHONE_VALIDATION_REGEX, email)
}