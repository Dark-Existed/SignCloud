package com.de.signcloud.ui.signinsignup

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.ui.components.*
import com.de.signcloud.ui.components.textfieldstate.PasswordState
import com.de.signcloud.ui.components.textfieldstate.PhoneState
import com.de.signcloud.ui.components.textfieldstate.ValidateCodeState
import com.de.signcloud.ui.theme.*

sealed class SignInEvent {
    data class SignInWithPassword(val phone: String, val password: String) : SignInEvent()
    data class GetValidate(val phone: String) : SignInEvent()
    data class SignInWithValidateCode(val phone: String, val validateCode: String) : SignInEvent()
    data class ResetPassword(val phone: String) : SignInEvent()
    object NavigateBack : SignInEvent()
}

@Composable
fun SignIn(
    initPhone: String = "",
    onPhoneChange: (String) -> Unit = {},
    validateButtonText: String = stringResource(id = R.string.get_validate_code),
    validateButtonClickable: Boolean = true,
    onEvent: (SignInEvent) -> Unit
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.sign_in),
                onBackPressed = { onEvent(SignInEvent.NavigateBack) }
            )
        },
        content = {
            val selectedState = remember { mutableStateOf(0) }
            val titles = listOf(
                stringResource(id = R.string.sign_in_with_password),
                stringResource(id = R.string.sign_in_with_validate_code)
            )

            SignInSignUpLayout(modifier = Modifier.fillMaxWidth()) {
                val selectedColor = if (isSystemInDarkTheme()) {
                    DarkColorPalette.primary
                } else {
                    LightColorPalette.primary
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    TabRow(
                        selectedTabIndex = selectedState.value,
                        backgroundColor = MaterialTheme.colors.background,
                        contentColor = selectedColor
                    ) {
                        titles.forEachIndexed { index, title ->
                            Tab(
                                selected = selectedState.value == index,
                                onClick = { selectedState.value = index },
                                selectedContentColor = selectedColor,
                            ) {
                                Text(
                                    text = title,
                                    modifier = Modifier.height(32.dp)
                                )
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    when (selectedState.value) {
                        0 -> SignInWithPasswordContent(
                            initPhone = initPhone,
                            onPhoneChange = onPhoneChange,
                            onSignInSubmitted = { phone, password ->
                                onEvent(SignInEvent.SignInWithPassword(phone, password))
                            },
                            onResetPassword = {
                                onEvent(SignInEvent.ResetPassword(it))
                            }
                        )
                        1 -> SignInWithValidateCodeContent(
                            initPhone = initPhone,
                            onPhoneChange = onPhoneChange,
                            validateButtonText = validateButtonText,
                            validateButtonClickable = validateButtonClickable,
                            onGetValidateCode = { phone ->
                                onEvent(SignInEvent.GetValidate(phone))
                            },
                            onSignInSubmitted = { phone, validateCode ->
                                onEvent(
                                    SignInEvent.SignInWithValidateCode(
                                        phone,
                                        validateCode
                                    )
                                )
                            }
                        )
                    }

                }
            }
        }
    )
}


@Composable
fun SignInWithPasswordContent(
    initPhone: String = "",
    onPhoneChange: (String) -> Unit = {},
    onSignInSubmitted: (phone: String, password: String) -> Unit,
    onResetPassword: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val focusRequester = remember { FocusRequester() }
        val phoneState = remember { PhoneState(initPhone) }
        Phone(
            phoneState,
            onImeAction = { focusRequester.requestFocus() },
            onValueChange = onPhoneChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        val passwordState = remember { PasswordState() }
        Password(
            label = stringResource(id = R.string.password),
            passwordState = passwordState,
            modifier = Modifier.focusRequester(focusRequester),
            onImeAction = { onSignInSubmitted(phoneState.text, passwordState.text) }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSignInSubmitted(phoneState.text, passwordState.text) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            enabled = phoneState.isValid && passwordState.isValid
        ) {
            Text(
                text = stringResource(id = R.string.sign_in)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        TextButton(
            onClick = {
                onResetPassword(phoneState.text)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.forgot_password))
        }
    }
}

@Composable
fun SignInWithValidateCodeContent(
    validateButtonText: String,
    validateButtonClickable: Boolean,
    initPhone: String = "",
    onPhoneChange: (String) -> Unit = {},
    onGetValidateCode: (phone: String) -> Unit,
    onSignInSubmitted: (phone: String, validateCode: String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val focusRequester = remember { FocusRequester() }
        val phoneState = remember { PhoneState(initPhone) }
        Phone(
            phoneState,
            onImeAction = { focusRequester.requestFocus() },
            onValueChange = onPhoneChange
        )
        Spacer(modifier = Modifier.height(16.dp))
        val validateCodeState = remember { ValidateCodeState() }
        ValidateCode(validateCodeState = validateCodeState)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onGetValidateCode(phoneState.text)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            enabled = phoneState.isValid && validateButtonClickable
        ) {
            Text(text = validateButtonText)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                onSignInSubmitted(phoneState.text, validateCodeState.text)
            },
            modifier = Modifier
                .fillMaxWidth(),
            enabled = phoneState.isValid && validateCodeState.isValid
        ) {
            Text(
                text = stringResource(id = R.string.sign_in)
            )
        }
    }
}


@Preview(name = "Sign in light theme")
@Composable
fun SignInPreview() {
    SignCloudTheme {
        SignIn {}
    }
}

@Preview(name = "Sign in dark theme")
@Composable
fun SignInPreviewDark() {
    SignCloudTheme(darkTheme = true) {
        SignIn {}
    }
}