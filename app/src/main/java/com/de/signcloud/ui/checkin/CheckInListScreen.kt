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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.bean.CheckInInfo
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack
import com.de.signcloud.ui.theme.DarkColorPalette
import com.de.signcloud.ui.theme.LightColorPalette


sealed class CheckInListEvent {
    object NavigateBack : CheckInListEvent()
    data class NavigateToCheckInDetail(val checkInInfo: CheckInInfo) : CheckInListEvent()
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
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            LazyColumn(modifier.padding(bottom = 12.dp)) {
                items(checkInList) { checkInInfo ->
                    Spacer(modifier = modifier.height(6.dp))
                    CheckInItem(checkInInfo = checkInInfo, onEvent = onEvent)
                    Spacer(modifier = modifier.height(6.dp))
                }
            }
        }
    }
}

@Composable
fun CheckInItem(
    modifier: Modifier = Modifier,
    checkInInfo: CheckInInfo,
    onEvent: (CheckInListEvent) -> Unit
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
                onEvent(CheckInListEvent.NavigateToCheckInDetail(checkInInfo))
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 6.dp
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = checkInInfo.startTime, modifier = modifier.padding(12.dp, 0.dp))
            if (checkInInfo.isFinished == 1) {
                Text(
                    text = LocalContext.current.getString(R.string.finish),
                    modifier = modifier.padding(12.dp, 0.dp),
                    color = if (isSystemInDarkTheme()) DarkColorPalette.error else LightColorPalette.error
                )
            } else {
                Text(
                    text = LocalContext.current.getString(R.string.processing),
                    modifier = modifier.padding(12.dp, 0.dp),
                    color = if (isSystemInDarkTheme()) DarkColorPalette.primary else LightColorPalette.primary
                )
            }
        }
    }
}