package com.de.signcloud.ui.signinsignup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.ui.components.*
import com.de.signcloud.ui.components.textfieldstate.ConfirmPasswordState
import com.de.signcloud.ui.components.textfieldstate.PasswordState
import com.de.signcloud.ui.components.textfieldstate.PhoneState
import com.de.signcloud.ui.components.textfieldstate.ValidateCodeState
import com.de.signcloud.ui.theme.SignCloudTheme

sealed class ResetPasswordEvent {
    data class GetValidate(val phone: String) : ResetPasswordEvent()
    data class ResetPassword(val phone: String, val password: String, val validateCode: String) :
        ResetPasswordEvent()

    object NavigateBack : ResetPasswordEvent()
}

@Composable
fun ResetPassword(
    initPhone: String = "",
    validateButtonText: String = stringResource(id = R.string.get_validate_code),
    validateButtonClickable: Boolean = true,
    onEvent: (ResetPasswordEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.reset_password),
                onBackPressed = { onEvent(ResetPasswordEvent.NavigateBack) }
            )
        },
        content = {
            SignInSignUpLayout(modifier = Modifier.fillMaxWidth()) {
                Column {
                    ResetPasswordContent(
                        initPhone = initPhone,
                        validateButtonText = validateButtonText,
                        validateButtonClickable = validateButtonClickable,
                        onGetValidateCode = { phone ->
                            onEvent(ResetPasswordEvent.GetValidate(phone))
                        },
                        onResetPassword = { phone, password, validateCode ->
                            onEvent(ResetPasswordEvent.ResetPassword(phone, password, validateCode))
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun ResetPasswordContent(
    initPhone: String = "",
    validateButtonText: String,
    validateButtonClickable: Boolean,
    onGetValidateCode: (phone: String) -> Unit,
    onResetPassword: (phone: String, password: String, validateCode: String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val passwordFocusRequest = remember { FocusRequester() }
        val confirmationPasswordFocusRequest = remember { FocusRequester() }
        val validateCodeFocusRequest = remember { FocusRequester() }

        val phoneState = remember { PhoneState(initPhone) }
        Phone(phoneState, onImeAction = { passwordFocusRequest.requestFocus() })
        Spacer(modifier = Modifier.height(16.dp))

        val passwordState = remember { PasswordState() }
        Password(
            label = stringResource(id = R.string.password),
            passwordState = passwordState,
            imeAction = ImeAction.Next,
            onImeAction = { confirmationPasswordFocusRequest.requestFocus() },
            modifier = Modifier.focusRequester(passwordFocusRequest)
        )
        Spacer(modifier = Modifier.height(16.dp))

        val confirmPasswordState = remember { ConfirmPasswordState(passwordState = passwordState) }
        Password(
            label = stringResource(id = R.string.confirm_password),
            passwordState = confirmPasswordState,
            imeAction = ImeAction.Next,
            onImeAction = { validateCodeFocusRequest.requestFocus() },
            modifier = Modifier.focusRequester(confirmationPasswordFocusRequest)
        )
        Spacer(modifier = Modifier.height(16.dp))

        val validateCodeState = remember { ValidateCodeState() }
        ValidateCode(validateCodeState = validateCodeState)
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = { onGetValidateCode(phoneState.text) },
            modifier = Modifier.fillMaxWidth(),
            enabled = phoneState.isValid &&
                    passwordState.isValid &&
                    confirmPasswordState.isValid &&
                    validateButtonClickable
        ) {
            Text(text = validateButtonText)
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onResetPassword(
                    phoneState.text,
                    passwordState.text,
                    validateCodeState.text
                )
            },
            modifier = Modifier.fillMaxWidth(),
            enabled = phoneState.isValid &&
                    passwordState.isValid &&
                    confirmPasswordState.isValid &&
                    validateCodeState.isValid
        ) {
            Text(text = stringResource(id = R.string.reset_password))
        }
    }
}

@Preview
@Composable
fun ResetPasswordPreview() {
    SignCloudTheme {
        ResetPassword {}
    }
}