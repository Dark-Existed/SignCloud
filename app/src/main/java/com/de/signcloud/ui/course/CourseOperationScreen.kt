package com.de.signcloud.ui.course

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack

sealed class CourseOperationEvent() {
    object NavigateBack : CourseOperationEvent()
}

@Composable
fun CourseOperation(
    modifier: Modifier = Modifier,
    isStudent: Boolean = false,
    onEvent: (CourseOperationEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.course_operation),
                onBackPressed = { onEvent(CourseOperationEvent.NavigateBack) })
        },
        content = {
            CourseOperationContent(modifier = modifier, isStudent = isStudent, onEvent = onEvent)
        }
    )
}

@Composable
fun CourseOperationContent(
    modifier: Modifier,
    isStudent: Boolean = false,
    onEvent: (CourseOperationEvent) -> Unit
) {
    if (isStudent) {
        StudentCourseOperation(modifier = modifier, onEvent = onEvent)
    } else {
        TeacherCourseOperation(modifier = modifier, onEvent = onEvent)
    }
}

@Composable
fun StudentCourseOperation(
    modifier: Modifier,
    onEvent: (CourseOperationEvent) -> Unit
) {

}

@Composable
fun TeacherCourseOperation(
    modifier: Modifier,
    onEvent: (CourseOperationEvent) -> Unit
) {

}


@Composable
fun CardItem(
    modifier: Modifier,
    text: String
) {
    Card(shape = RoundedCornerShape(8.dp)) {
        Column(
            modifier = Modifier.height(180.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = text, style = MaterialTheme.typography.h4)
        }
    }
}