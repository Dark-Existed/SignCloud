package com.de.signcloud.ui.checkin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.bean.CheckInInfo
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack


sealed class CheckInListEvent {
    object NavigateBack : CheckInListEvent()
    data class NavigateToCheckInDetail(val checkInId: Int)
}

@Composable
fun CheckInList(
    modifier: Modifier = Modifier,
    checkInList: List<CheckInInfo> = emptyList(),
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
                checkInList = checkInList,
                onEvent = onEvent
            )
        }
    )
}

@Composable
fun CheckInListContent(
    modifier: Modifier = Modifier,
    checkInList: List<CheckInInfo> = emptyList(),
    onEvent: (CheckInListEvent) -> Unit
) {
    Column(
        modifier
            .fillMaxWidth()
            .padding(12.dp, 0.dp)
    ) {
        LazyColumn {

        }
    }
}