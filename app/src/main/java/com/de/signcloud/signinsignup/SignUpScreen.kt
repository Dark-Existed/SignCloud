package com.de.signcloud.signinsignup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import com.de.signcloud.R
import com.de.signcloud.ui.theme.SignCloudTheme

sealed class SignUpEvent {
    object SignIn : SignUpEvent()
    data class SignUp(val phone: String, val password: String) : SignUpEvent()
    object NavigateBack : SignUpEvent()
}


@Composable
fun SignUp(onNavigationEvent: (SignUpEvent) -> Unit) {
    Scaffold(
        topBar = {
            SignInSignUpTopAppBar(
                topAppBarText = stringResource(id = R.string.create_account),
                onBackPressed = { onNavigationEvent(SignUpEvent.NavigateBack) }
            )
        },
        content = {
            SignInSignUpScreen(modifier = Modifier.fillMaxWidth()) {
                Column {
                    SignUpContent { phone, password ->
                        onNavigationEvent(SignUpEvent.SignUp(phone, password))
                    }
                }
            }
        }
    )
}


@Composable
fun SignUpContent(
    onSignUpSubmitted: (phone: String, password: String) -> Unit,
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        val passwordFocusRequest = remember { FocusRequester() }
        val confirmationPasswordFocusRequest = remember { FocusRequester() }
        val phoneState = remember { PhoneState() }

        Phone(phoneState, onImeAction = {passwordFocusRequest.requestFocus()})
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
            onImeAction = { onSignUpSubmitted(phoneState.text, passwordState.text) },
            modifier = Modifier.focusRequester(confirmationPasswordFocusRequest)
        )
        Spacer(modifier = Modifier.height(16.dp))
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Text(
                text = stringResource(id = R.string.terms_and_conditions),
                style = MaterialTheme.typography.caption
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { onSignUpSubmitted(phoneState.text, passwordState.text) },
            modifier = Modifier.fillMaxWidth(),
            enabled = phoneState.isValid &&
                    passwordState.isValid && confirmPasswordState.isValid
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