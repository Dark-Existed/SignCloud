package com.de.signcloud.ui.signinsignup

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.viewModels
import com.de.signcloud.R
import com.de.signcloud.ui.theme.SignCloudTheme

sealed class SignUpEvent {
    object SignIn : SignUpEvent()
    data class GetValidate(val phone: String) : SignUpEvent()
    data class SignUp(val phone: String, val password: String, val validateCode: String) :
        SignUpEvent()

    object NavigateBack : SignUpEvent()
}


@Composable
fun SignUp(
    validateButtonText: String = stringResource(id = R.string.get_validate_code),
    validateButtonClickable: Boolean = true,
    onEvent: (SignUpEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            SignInSignUpTopAppBar(
                topAppBarText = stringResource(id = R.string.create_account),
                onBackPressed = { onEvent(SignUpEvent.NavigateBack) }
            )
        },
        content = {
            SignInSignUpScreen(modifier = Modifier.fillMaxWidth()) {
                Column {
                    SignUpContent(
                        onGetValidateCode = { phone ->
                            onEvent(SignUpEvent.GetValidate(phone))
                        },
                        onSignUpSubmitted = { phone, password, validateCode ->
                            onEvent(SignUpEvent.SignUp(phone, password, validateCode))
                        },
                        validateButtonText = validateButtonText,
                        validateButtonClickable = validateButtonClickable
                    )
                }
            }
        }
    )
}


@Composable
fun SignUpContent(
    validateButtonText: String,
    validateButtonClickable: Boolean,
    onGetValidateCode: (phone: String) -> Unit,
    onSignUpSubmitted: (phone: String, password: String, validateCode: String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val passwordFocusRequest = remember { FocusRequester() }
        val confirmationPasswordFocusRequest = remember { FocusRequester() }
        val validateCodeFocusRequest = remember { FocusRequester() }

        val phoneState = remember { PhoneState() }
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            enabled = phoneState.isValid &&
                    passwordState.isValid &&
                    confirmPasswordState.isValid &&
                    validateButtonClickable
        ) {
            Text(text = validateButtonText)
        }
//        Spacer(modifier = Modifier.height(16.dp))

        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = stringResource(id = R.string.terms_and_conditions),
                style = MaterialTheme.typography.caption
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                onSignUpSubmitted(
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
            Text(text = stringResource(id = R.string.create_account))
        }
    }
}


@Preview
@Composable
fun SignUpPreview() {
    SignCloudTheme {
        SignUp {}
    }
}