package com.de.signcloud.ui.components.textfieldstate


class ValidateCodeState : TextFieldState(
    validator = ::isValidateCodeValid,
    errorFor = ::validateCodeValidationError
)

private fun validateCodeValidationError(validateCode: String): String {
    return "Invalid phone: $validateCode"
}

private fun isValidateCodeValid(validateCode: String): Boolean {
    return validateCode.length == 4
}