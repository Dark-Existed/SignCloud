package com.de.signcloud.ui.checkin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.LocationOn
import androidx.compose.material.icons.outlined.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.ui.components.GeneralTextField
import com.de.signcloud.ui.components.Location
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack
import com.de.signcloud.ui.components.textfieldstate.GenerateNotNullState
import com.de.signcloud.ui.course.CardItem
import com.de.signcloud.ui.theme.DarkColorPalette
import com.de.signcloud.ui.theme.LightColorPalette

sealed class CreateCheckInEvent() {
    object NavigateBack : CreateCheckInEvent()
    object CreateOneStepCheckIn : CreateCheckInEvent()
    object RefreshLocation : CreateCheckInEvent()
    data class CreateTimeLimitCheckIn(val minutes: String) : CreateCheckInEvent()
}

@Composable
fun CreateCheckIn(
    modifier: Modifier = Modifier,
    locationName: String = "",
    onEvent: (CreateCheckInEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.create_check_in),
                onBackPressed = { onEvent(CreateCheckInEvent.NavigateBack) })
        },
        content = {
            CreateCheckInContent(
                modifier = modifier,
                locationName = locationName,
                onEvent = onEvent
            )
        }
    )
}


@Composable
fun CreateCheckInContent(
    modifier: Modifier = Modifier,
    locationName: String = "",
    onEvent: (CreateCheckInEvent) -> Unit
) {
    Column(modifier = modifier.padding(12.dp, 0.dp)) {

        val selectedState = remember { mutableStateOf(0) }
        val titles = listOf(
            stringResource(id = R.string.one_step),
            stringResource(id = R.string.time_limited)
        )
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
                0 -> {
                    OneStepContent(onEvent = onEvent, locationName = locationName)
                }
                1 -> {
                    TimeLimitContent(onEvent = onEvent, locationName = locationName)
                }
            }
        }

    }
}


@Composable
fun OneStepContent(
    modifier: Modifier = Modifier,
    locationName: String = "",
    onEvent: (CreateCheckInEvent) -> Unit
) {
    Column(modifier.padding(12.dp, 0.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Location(
            locationName = locationName,
            refreshLocation = { onEvent(CreateCheckInEvent.RefreshLocation) })
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp),
            onClick = { onEvent(CreateCheckInEvent.CreateOneStepCheckIn) },
            enabled = locationName.isNotBlank()
        ) {
            Text(
                text = stringResource(id = R.string.create_check_in),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

@Composable
fun TimeLimitContent(
    modifier: Modifier = Modifier,
    locationName: String = "",
    onEvent: (CreateCheckInEvent) -> Unit
) {
    Column(modifier.padding(12.dp, 0.dp), horizontalAlignment = Alignment.CenterHorizontally) {


        val minutesState = remember { GenerateNotNullState() }
        GeneralTextField(
            generalTextFieldState = minutesState,
            hintText = stringResource(id = R.string.minutes),
            onImeAction = {}
        )
        Spacer(modifier = Modifier.height(16.dp))
        Location(
            locationName = locationName,
            refreshLocation = { onEvent(CreateCheckInEvent.RefreshLocation) })
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp),
            onClick = { onEvent(CreateCheckInEvent.CreateTimeLimitCheckIn(minutesState.text)) },
            enabled = minutesState.isValid && locationName.isNotBlank()
        ) {
            Text(
                text = stringResource(id = R.string.create_check_in),
                style = MaterialTheme.typography.subtitle2
            )
        }
    }
}

