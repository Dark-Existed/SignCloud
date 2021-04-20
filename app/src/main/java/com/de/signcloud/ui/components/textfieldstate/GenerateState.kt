package com.de.signcloud.ui.components.textfieldstate


class GenerateNotNullState :
    TextFieldState(validator = ::isTextValid, errorFor = ::generateNotNullValidationError)

private fun isTextValid(text: String): Boolean {
    return text.isNotBlank()
}

private fun generateNotNullValidationError(text: String): String {
    return "Text should not be null"
}


class GenerateState :
    TextFieldState(validator = ::alwaysValid, errorFor = ::generateValidationError)

private fun alwaysValid(text: String): Boolean {
    return true
}

private fun generateValidationError(text: String): String {
    return ""
}