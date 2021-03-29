package com.de.signcloud.signinsignup

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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.ui.theme.*

sealed class SignInEvent {
    data class SignIn(val phone: String, val password: String) : SignInEvent()
    object NavigateBack : SignInEvent()
}

@Composable
fun SignIn(onNavigationEvent: (SignInEvent) -> Unit) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    Scaffold(
        topBar = {
            SignInSignUpTopAppBar(
                topAppBarText = stringResource(id = R.string.sign_in),
                onBackPressed = { onNavigationEvent(SignInEvent.NavigateBack) }
            )
        },
        content = {
            val selectedState = remember { mutableStateOf(0) }
            val titles = listOf(
                stringResource(id = R.string.sign_in_with_password),
                stringResource(id = R.string.sign_in_with_validate_code)
            )

            SignInSignUpScreen(modifier = Modifier.fillMaxWidth()) {
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
                                unselectedContentColor = Color.Black.copy(alpha = ContentAlpha.medium)
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
                            onSignInSubmitted = { phone, password ->
                                onNavigationEvent(SignInEvent.SignIn(phone, password))
                            })
                        1-> SignInWithValidateCodeContent()
                    }

                }
            }
        }
    )
}


@Composable
fun SignInWithPasswordContent(
    onSignInSubmitted: (phone: String, password: String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val focusRequester = remember { FocusRequester() }
        val phoneState = remember { PhoneState() }
        Phone(phoneState, onImeAction = { focusRequester.requestFocus() })
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
    }
}

@Composable
fun SignInWithValidateCodeContent(

) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val focusRequester = remember { FocusRequester() }
        val phoneState = remember { PhoneState() }

        Phone(phoneState, onImeAction = { focusRequester.requestFocus() })
        Spacer(modifier = Modifier.height(16.dp))
        val validateCodeState = remember { ValidateCodeState() }
        ValidateCode(validateCodeState = validateCodeState)
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /*TODO*/ },
            modifier = Modifier.fillMaxWidth(),
            enabled =  phoneState.isValid
        ) {
            Text(text = stringResource(id = R.string.get_validate_code))
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {},
            modifier = Modifier.fillMaxWidth(),
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