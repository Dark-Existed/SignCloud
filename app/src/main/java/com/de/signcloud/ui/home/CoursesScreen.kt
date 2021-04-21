package com.de.signcloud.ui.home

import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterCenterFocus
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudTopAppBarWithAction
import com.de.signcloud.ui.theme.SignCloudTheme


@Composable
fun Courses(
    isStudent: Boolean = false,
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithAction(
                topAppBarText = stringResource(id = R.string.courses),
                actionIcon = if (isStudent) Icons.Filled.FilterCenterFocus else Icons.Filled.Add,
                onActionPressed = {
                    if (isStudent) {
                        onEvent(HomeEvent.NavigateToScanCode)
                    } else {
                        onEvent(HomeEvent.NavigateToCreateCourse)
                    }
                }
            )
        },
        content = {

        }
    )
}

@Composable
fun StudentCourseList() {
}

@Composable
fun TeacherCourseList() {
}


@Preview
@Composable
fun CoursePreview() {
    SignCloudTheme {
        Courses {}
    }
}

