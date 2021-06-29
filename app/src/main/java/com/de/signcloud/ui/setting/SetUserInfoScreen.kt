package com.de.signcloud.ui.setting

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.ui.components.GeneralTextField
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack
import com.de.signcloud.ui.components.textfieldstate.GenerateNotNullState

sealed class SetUserInfoEvent() {
    object NavigateBack : SetUserInfoEvent()
    data class SetUserInfo(val ino: String, val userName:String) : SetUserInfoEvent()
}

@Composable
fun SetUserInfo(
    modifier: Modifier = Modifier,
    onEvent: (SetUserInfoEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.set_user_info),
                onBackPressed = {
                    onEvent(SetUserInfoEvent.NavigateBack)
                }
            )
        },
        content = {
            SetUserInfoContent(modifier = modifier, onEvent = onEvent)
        }
    )
}

@Composable
fun SetUserInfoContent(
    modifier: Modifier = Modifier,
    onEvent: (SetUserInfoEvent) -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(12.dp, 0.dp)
    ) {
        val studentWorkIdState = remember { GenerateNotNullState() }
        GeneralTextField(
            generalTextFieldState = studentWorkIdState,
            hintText = stringResource(id = R.string.student_work_id),
            onImeAction = {}
        )
        Spacer(modifier = modifier.height(12.dp))

        val userNameState = remember { GenerateNotNullState() }
        GeneralTextField(
            generalTextFieldState = userNameState,
            hintText = stringResource(id = R.string.user_name),
            onImeAction = {}
        )

        Spacer(modifier = modifier.height(16.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {},
            enabled = studentWorkIdState.isValid && userNameState.isValid
        ) {
            Text(text = stringResource(id = R.string.confirm))
        }
    }
}