package com.de.signcloud.ui.checkin

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack

sealed class CheckInDetailEvent {
    object NavigateBack : CheckInDetailEvent()
}

@Composable
fun CheckInDetail(
    modifier: Modifier = Modifier,
    isEnable: Boolean = false,
    onEvent: (CheckInDetailEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = LocalContext.current.getString(R.string.check_in_detail),
                onBackPressed = { onEvent(CheckInDetailEvent.NavigateBack) }
            )
        },
        content = {
            CheckInDetailContent(modifier = modifier, isEnable = isEnable, onEvent = onEvent)
        }
    )
}

@Composable
fun CheckInDetailContent(
    modifier: Modifier = Modifier,
    isEnable: Boolean = false,
    onEvent: (CheckInDetailEvent) -> Unit
) {
    Column(modifier = modifier.padding(12.dp, 0.dp)) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp),
            onClick = {  },
            enabled = isEnable
        ) {
            Text(
                text = stringResource(id = R.string.finish_chek_in),
                style = MaterialTheme.typography.subtitle2
            )
        }

    }
}