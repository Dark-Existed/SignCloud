package com.de.signcloud.ui.checkin

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.bean.CheckInInfo
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack

sealed class CheckInEvent {
    object NavigateBack : CheckInEvent()
    object RefreshLocation : CheckInEvent()
}

@Composable
fun CheckIn(
    modifier: Modifier = Modifier,
    checkInInfo: CheckInInfo,
    locationName: String = "",
    onEvent: (CheckInEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.check_in),
                onBackPressed = { onEvent(CheckInEvent.NavigateBack) })
        },
        content = {
            CheckInContent(
                checkInInfo = checkInInfo,
                locationName = locationName,
                onEvent = onEvent
            )
        }
    )
}


@Composable
fun CheckInContent(
    modifier: Modifier = Modifier,
    checkInInfo: CheckInInfo,
    locationName: String = "",
    onEvent: (CheckInEvent) -> Unit
) {

    Column(
        modifier
            .fillMaxWidth()
            .padding(12.dp, 0.dp)
    ) {
        when (checkInInfo.mode) {
            "oneStep" -> OneStepCheckIn(
                checkInInfo = checkInInfo,
                locationName = locationName,
                onEvent = onEvent
            )
            "time" -> TimeLimitCheckIn(
                checkInInfo = checkInInfo,
                locationName = locationName,
                onEvent = onEvent
            )
        }
    }
}

@Composable
fun OneStepCheckIn(
    modifier: Modifier = Modifier,
    checkInInfo: CheckInInfo,
    locationName: String = "",
    onEvent: (CheckInEvent) -> Unit
) {
    Column(modifier.fillMaxWidth()) {

    }
}

@Composable
fun TimeLimitCheckIn(
    modifier: Modifier = Modifier,
    checkInInfo: CheckInInfo,
    locationName: String = "",
    onEvent: (CheckInEvent) -> Unit
) {
    Column(modifier.fillMaxWidth()) {

    }
}

