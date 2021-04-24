package com.de.signcloud.ui.signinsignup

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack
import com.de.signcloud.ui.components.SignInSignUpLayout

sealed class BindPhoneEvent {
    data class GetValidate(val phone: String) : BindPhoneEvent()
    data class OnBindPhone(val phone: String, val password: String, val validateCode: String) :
        BindPhoneEvent()
    object NavigateBack : BindPhoneEvent()
}


@Composable
fun BindPhone(
    topBarTitle: String = stringResource(id = R.string.bind_phone),
    validateButtonText: String = stringResource(id = R.string.get_validate_code),
    validateButtonClickable: Boolean = true,
    onEvent: (BindPhoneEvent) -> Unit,
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = topBarTitle,
                onBackPressed = { onEvent(BindPhoneEvent.NavigateBack) }
            )
        },
        content = {
            SignInSignUpLayout(modifier = Modifier.fillMaxWidth()) {
                Column {
                    SignUpContent(
                        onGetValidateCode = { phone ->
                            onEvent(BindPhoneEvent.GetValidate(phone))
                        },
                        onSignUpSubmitted = { phone, password, validateCode ->
                            onEvent(BindPhoneEvent.OnBindPhone(phone, password, validateCode))
                        },
                        validateButtonText = validateButtonText,
                        validateButtonClickable = validateButtonClickable
                    )
                }
            }
        }
    )
}