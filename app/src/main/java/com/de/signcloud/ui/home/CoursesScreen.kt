package com.de.signcloud.ui.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudDivider
import com.de.signcloud.ui.components.SignCloudTopAppBarWithAction


@Composable
fun Courses(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithAction(
                topAppBarText = stringResource(id = R.string.courses),
                onActionPressed = { onEvent(HomeEvent.NavigateToCreateCourse) }
            )
        }
    ) {

    }
}

