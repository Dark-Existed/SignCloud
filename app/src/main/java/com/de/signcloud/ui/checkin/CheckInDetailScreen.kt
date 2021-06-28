package com.de.signcloud.ui.checkin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.bean.StudentCheckInStatusResponse
import com.de.signcloud.ui.components.NoItems
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack
import com.de.signcloud.ui.theme.DarkColorPalette
import com.de.signcloud.ui.theme.LightColorPalette
import com.de.signcloud.ui.theme.SignCloudTheme

sealed class CheckInDetailEvent {
    object NavigateBack : CheckInDetailEvent()
    object RefreshList : CheckInDetailEvent()
    object FinishCheckIn : CheckInDetailEvent()
}

@Composable
fun CheckInDetail(
    modifier: Modifier = Modifier,
    isEnable: Boolean = false,
    checkInList: List<StudentCheckInStatusResponse.CheckInStatus> = emptyList(),
    uncheckInList: List<StudentCheckInStatusResponse.CheckInStatus> = emptyList(),
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
            CheckInDetailContent(
                modifier = modifier,
                isEnable = isEnable,
                checkInList = checkInList,
                uncheckInList = uncheckInList,
                onEvent = onEvent
            )
        }
    )
}

@Composable
fun CheckInDetailContent(
    modifier: Modifier = Modifier,
    isEnable: Boolean = false,
    checkInList: List<StudentCheckInStatusResponse.CheckInStatus> = emptyList(),
    uncheckInList: List<StudentCheckInStatusResponse.CheckInStatus> = emptyList(),
    onEvent: (CheckInDetailEvent) -> Unit
) {
    Column(modifier = modifier.padding(12.dp, 0.dp)) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 28.dp),
            onClick = { onEvent(CheckInDetailEvent.FinishCheckIn) },
            enabled = isEnable
        ) {
            Text(
                text = stringResource(id = R.string.finish_chek_in),
                style = MaterialTheme.typography.subtitle2
            )
        }
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp),
            onClick = { onEvent(CheckInDetailEvent.RefreshList) },
        ) {
            Text(
                text = stringResource(id = R.string.refresh_list),
                style = MaterialTheme.typography.subtitle2
            )
        }

        val selectedState = remember { mutableStateOf(0) }
        val titles = listOf(
            stringResource(id = R.string.checked),
            stringResource(id = R.string.uncheck)
        )
        val selectedColor = if (isSystemInDarkTheme()) {
            DarkColorPalette.primary
        } else {
            LightColorPalette.primary
        }

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
                CheckedList(modifier = modifier, checkInList = checkInList, onEvent = onEvent)
            }
            1 -> {
                UnCheckedList(modifier = modifier, uncheckInList = uncheckInList, onEvent = onEvent)
            }
        }
    }
}


@Composable
fun CheckedList(
    modifier: Modifier = Modifier,
    checkInList: List<StudentCheckInStatusResponse.CheckInStatus> = emptyList(),
    onEvent: (CheckInDetailEvent) -> Unit
) {
    if (checkInList.isEmpty()) {
        NoItems()
    } else {
        LazyColumn {
            items(checkInList) { checkInStatus ->
                CheckedCard(checkInStatus = checkInStatus)
                Spacer(modifier = modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun CheckedCard(
    modifier: Modifier = Modifier,
    checkInStatus: StudentCheckInStatusResponse.CheckInStatus
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 6.dp
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Column(modifier = modifier.fillMaxWidth()) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = checkInStatus.ino, modifier = modifier.padding(12.dp, 0.dp))
                    Text(
                        text = stringResource(id = R.string.experience) + checkInStatus.experience,
                        modifier = modifier.padding(12.dp, 0.dp)
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .weight(1f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = stringResource(
                            id = R.string.distance,
                            checkInStatus.distance.toInt()
                        ),
                        modifier = modifier.padding(12.dp, 0.dp),
                        color = if (checkInStatus.distance > 100) SignCloudTheme.colors.error else SignCloudTheme.colors.primary
                    )
                    Text(text = checkInStatus.time, modifier = modifier.padding(12.dp, 0.dp))
                }
            }
        }
    }
}

@Composable
fun UnCheckedList(
    modifier: Modifier = Modifier,
    uncheckInList: List<StudentCheckInStatusResponse.CheckInStatus> = emptyList(),
    onEvent: (CheckInDetailEvent) -> Unit
) {
    if (uncheckInList.isEmpty()) {
        NoItems()
    } else {
        LazyColumn {
            items(uncheckInList) { checkInStatus ->
                UnCheckedCard(checkInStatus = checkInStatus)
                Spacer(modifier = modifier.height(12.dp))
            }
        }
    }
}

@Composable
fun UnCheckedCard(
    modifier: Modifier = Modifier,
    checkInStatus: StudentCheckInStatusResponse.CheckInStatus
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 6.dp
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = checkInStatus.ino, modifier = modifier.padding(12.dp, 0.dp))
                Text(
                    text = stringResource(id = R.string.experience) + checkInStatus.experience,
                    modifier = modifier.padding(12.dp, 0.dp)
                )
            }
        }
    }
}