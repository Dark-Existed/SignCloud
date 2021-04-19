package com.de.signcloud.ui.home

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudTopAppBarWithAction

sealed class CoursesEvent {
    object ActionPressed : CoursesEvent()
}

@Composable
fun Courses(
    onSnackClick: (Long) -> Unit,
    modifier: Modifier = Modifier,
    onEvent: (CoursesEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithAction(
                topAppBarText = stringResource(id = R.string.courses),
                onActionPressed = { onEvent(CoursesEvent.ActionPressed) }
            )
        }
    ) {

    }
}

