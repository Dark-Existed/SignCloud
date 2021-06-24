package com.de.signcloud.ui.checkin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack


sealed class CheckInListEvent {
    object NavigateBack : CheckInListEvent()
}

@Composable
fun CheckInList(
    modifier: Modifier = Modifier,
    onEvent: (CheckInListEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.check_in_list),
                onBackPressed = { onEvent(CheckInListEvent.NavigateBack) }
            )
        },
        content = {
            CheckInListContent(
                modifier = modifier,
                onEvent = onEvent
            )
        }
    )
}

@Composable
fun CheckInListContent(
    modifier: Modifier = Modifier,
    onEvent: (CheckInListEvent) -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(12.dp, 0.dp)
    ) {

    }
}