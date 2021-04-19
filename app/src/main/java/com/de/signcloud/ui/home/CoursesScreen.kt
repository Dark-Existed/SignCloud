package com.de.signcloud.ui.home

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudDivider
import com.de.signcloud.ui.components.SignCloudTopAppBarWithAction
import com.de.signcloud.ui.theme.SignCloudTheme


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


@Preview
@Composable
fun CoursePreview() {
    SignCloudTheme{
        Courses {}
    }
}

