package com.de.signcloud.ui.setting

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.ui.components.GeneralTextField
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack
import com.de.signcloud.ui.components.textfieldstate.GenerateNotNullState

sealed class SetIdEvent() {
    object NavigateBack : SetIdEvent()
    data class SetStudentWorkId(val text: String) : SetIdEvent()
}

@Composable
fun SetId(
    modifier: Modifier = Modifier,
    onEvent: (SetIdEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.student_work_id),
                onBackPressed = {
                    onEvent(SetIdEvent.NavigateBack)
                }
            )
        },
        content = {
            SetIdContent(modifier = modifier, onEvent = onEvent)
        }
    )
}

@Composable
fun SetIdContent(
    modifier: Modifier = Modifier,
    onEvent: (SetIdEvent) -> Unit
) {
    Column(modifier.fillMaxWidth()) {
        val studentWorkIdState = GenerateNotNullState()
        GeneralTextField(
            generalTextFieldState = studentWorkIdState,
            hintText = stringResource(id = R.string.student_work_id),
            onImeAction = {}
        )
        Spacer(modifier = modifier.height(12.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {

            }
        ) {
            Text(text = stringResource(id = R.string.confirm))
        }
    }
}