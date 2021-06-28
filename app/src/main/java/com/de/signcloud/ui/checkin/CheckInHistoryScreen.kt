package com.de.signcloud.ui.checkin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.bean.StudentCheckInHistory
import com.de.signcloud.ui.components.NoItems
import com.de.signcloud.ui.components.SignCloudDivider
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack
import com.de.signcloud.ui.theme.SignCloudTheme

sealed class CheckInHistoryEvent {
    object NavigateBack : CheckInHistoryEvent()
    object RefreshList : CheckInHistoryEvent()
}


@Composable
fun CheckInHistory(
    modifier: Modifier = Modifier,
    checkInHistoryItems: List<StudentCheckInHistory.CheckInHistory> = emptyList(),
    onEvent: (CheckInHistoryEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.check_in_history),
                onBackPressed = { CheckInHistoryEvent.NavigateBack }
            )
        },
        content = {
            CheckInHistoryContent(
                modifier = modifier,
                checkInHistoryItems = checkInHistoryItems,
                onEvent = onEvent
            )
        }
    )
}


@Composable
fun CheckInHistoryContent(
    modifier: Modifier = Modifier,
    checkInHistoryItems: List<StudentCheckInHistory.CheckInHistory> = emptyList(),
    onEvent: (CheckInHistoryEvent) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp, 0.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp),
            onClick = { onEvent(CheckInHistoryEvent.RefreshList) },
        ) {
            Text(
                text = stringResource(id = R.string.refresh_list),
                style = MaterialTheme.typography.subtitle2
            )
        }
        SignCloudDivider()
        Spacer(modifier = modifier.height(16.dp))
        if (checkInHistoryItems.isEmpty()) {
            NoItems()
        } else {
            LazyColumn(modifier.fillMaxWidth()) {
                items(checkInHistoryItems) { checkInHistory ->
                    CheckInHistory(checkInHistory = checkInHistory)
                    Spacer(modifier = modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun CheckInHistory(
    modifier: Modifier = Modifier,
    checkInHistory: StudentCheckInHistory.CheckInHistory
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
                Text(text = checkInHistory.time, modifier = modifier.padding(12.dp, 0.dp))
                Text(
                    text = if (checkInHistory.mode == "time") stringResource(id = R.string.time_limited)
                    else stringResource(id = R.string.one_step)
                )
                Text(
                    text = if (checkInHistory.isSignIn) stringResource(id = R.string.checked)
                    else stringResource(id = R.string.uncheck),
                    modifier = modifier.padding(12.dp, 0.dp),
                    color = if (checkInHistory.isSignIn) SignCloudTheme.colors.primary else SignCloudTheme.colors.error
                )
            }
        }
    }
}

