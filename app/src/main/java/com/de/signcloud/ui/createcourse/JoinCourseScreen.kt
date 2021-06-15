package com.de.signcloud.ui.createcourse

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack


sealed class JoinCourseEvent() {
    object NavigateBack : JoinCourseEvent()
}

@Composable
fun JoinCourse(
    modifier: Modifier = Modifier,
    onEvent: (JoinCourseEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.join_course),
                onBackPressed = { onEvent(JoinCourseEvent.NavigateBack) }
            )
        },
        content = {

        }
    )
}


@Composable
fun JoinCourseContent() {

}