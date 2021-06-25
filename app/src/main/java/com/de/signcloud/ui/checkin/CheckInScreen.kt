package com.de.signcloud.ui.checkin

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.de.signcloud.R
import com.de.signcloud.bean.CheckInInfo
import com.de.signcloud.ui.components.Location
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack
import com.de.signcloud.ui.theme.DarkColorPalette
import com.de.signcloud.ui.theme.LightColorPalette

sealed class CheckInEvent {
    object NavigateBack : CheckInEvent()
    object RefreshLocation : CheckInEvent()
    object CheckIn : CheckInEvent()
}

@Composable
fun CheckIn(
    modifier: Modifier = Modifier,
    checkInInfo: CheckInInfo,
    locationName: String = "",
    countDownText: String = "",
    countDownInt: Long = -1,
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
                countDownText = countDownText,
                countDownInt = countDownInt,
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
    countDownText: String = "",
    countDownInt: Long = -1,
    onEvent: (CheckInEvent) -> Unit
) {

    Column(
        modifier
            .fillMaxWidth()
            .padding(12.dp, 0.dp)
    ) {
        when (checkInInfo.mode) {
            "oneStep" -> OneStepCheckIn(
                locationName = locationName,
                onEvent = onEvent
            )
            "time" -> TimeLimitCheckIn(
                locationName = locationName,
                countDownText = countDownText,
                countDownInt = countDownInt,
                onEvent = onEvent
            )
        }
    }
}

@Composable
fun OneStepCheckIn(
    modifier: Modifier = Modifier,
    locationName: String = "",
    onEvent: (CheckInEvent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = stringResource(id = R.string.one_step), fontSize = 20.sp)
        Spacer(modifier = modifier.height(16.dp))
        Location(
            locationName = locationName,
            refreshLocation = { onEvent(CheckInEvent.RefreshLocation) }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp),
            onClick = { onEvent(CheckInEvent.CheckIn) },
            enabled = locationName.isNotBlank()
        ) {
            Text(
                text = stringResource(id = R.string.check_in),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

@Composable
fun TimeLimitCheckIn(
    modifier: Modifier = Modifier,
    locationName: String = "",
    countDownText: String = "",
    countDownInt: Long = -1,
    onEvent: (CheckInEvent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        val textColor = if (isSystemInDarkTheme()) {
            DarkColorPalette.primary
        } else {
            LightColorPalette.primary
        }

        Text(text = stringResource(id = R.string.time_limited), fontSize = 20.sp)
        Spacer(modifier = modifier.height(16.dp))
        Text(text = countDownText, fontSize = 24.sp, color = textColor)
        Spacer(modifier = modifier.height(16.dp))
        Location(
            locationName = locationName,
            refreshLocation = { onEvent(CheckInEvent.RefreshLocation) }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp),
            onClick = { onEvent(CheckInEvent.CheckIn) },
            enabled = locationName.isNotBlank() && (countDownInt >= 0)
        ) {
            Text(
                text = stringResource(id = R.string.check_in),
                style = MaterialTheme.typography.subtitle2
            )
        }

    }
}

