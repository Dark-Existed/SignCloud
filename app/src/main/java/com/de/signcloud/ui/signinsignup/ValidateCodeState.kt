package com.de.signcloud.ui.signinsignup


class ValidateCodeState : TextFieldState(
    validator = ::isValidateCodeValid,
    errorFor = ::validateCodeValidationError
)

private fun validateCodeValidationError(validateCode: String): String {
    return "Invalid phone: $validateCode"
}

private fun isValidateCodeValid(validateCode: String): Boolean {
    return validateCode.length == 6
}