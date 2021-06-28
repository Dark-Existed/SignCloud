package com.de.signcloud.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudDivider
import com.de.signcloud.ui.components.SignCloudTopAppBar
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack
import com.de.signcloud.ui.theme.SignCloudTheme
import com.google.accompanist.insets.statusBarsHeight


@Composable
fun Me(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBar(topAppBarText = stringResource(id = R.string.me))
        },
        content = {
            MeContent(modifier = modifier, onEvent = onEvent)
        }
    )
}


@Composable
fun MeContent(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit
) {
    Column(modifier.padding(16.dp, 0.dp)) {
        Spacer(modifier = modifier.height(32.dp))
        Text(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onEvent(HomeEvent.NavigateToChangeRole)
                },
            text = stringResource(id = R.string.change_role),
            fontSize = 20.sp
        )
        Spacer(modifier = modifier.height(32.dp))
        Text(
            modifier = modifier
                .fillMaxWidth()
                .clickable {
                    onEvent(HomeEvent.NavigateToChangeRole)
                },
            text = stringResource(id = R.string.student_work_id),
            fontSize = 20.sp
        )
        Spacer(modifier = modifier.height(32.dp))
        TextButton(
            onClick = { onEvent(HomeEvent.SignOut) },
            modifier = modifier.fillMaxWidth()
        ) {
            Text(
                text = stringResource(id = R.string.sign_out),
                color = SignCloudTheme.colors.error,
                fontSize = 18.sp
            )
        }
    }
}