package com.de.signcloud.ui.components.textfieldstate

import com.de.signcloud.R
import com.de.signcloud.SignCloudApplication


class GenerateNotNullState :
    TextFieldState(validator = ::isTextValid, errorFor = ::generateNotNullValidationError)

private fun isTextValid(text: String): Boolean {
    return text.isNotBlank()
}

private fun generateNotNullValidationError(text: String): String {
    return SignCloudApplication.context.getString(R.string.text_should_not_be_null)
}


class GenerateState :
    TextFieldState(validator = ::alwaysValid, errorFor = ::generateValidationError)

private fun alwaysValid(text: String): Boolean {
    return true
}

private fun generateValidationError(text: String): String {
    return ""
}