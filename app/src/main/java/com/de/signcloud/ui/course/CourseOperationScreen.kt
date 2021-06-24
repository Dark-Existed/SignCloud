package com.de.signcloud.ui.course

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
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
import com.de.signcloud.ui.theme.Teal200

sealed class CourseOperationEvent() {
    object NavigateBack : CourseOperationEvent()
    object NavigateToCreateCheckIn : CourseOperationEvent()
    object NavigateToCheckInList : CourseOperationEvent()
    object NavigateToCheckIn : CourseOperationEvent()
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
    Column(modifier.padding(12.dp, 0.dp)) {
        CardItem(
            modifier = modifier
                .clickable {
                    onEvent(CourseOperationEvent.NavigateToCheckIn)
                }
                .background(Teal200),
            text = stringResource(id = R.string.check_in)
        )
    }
}

@Composable
fun TeacherCourseOperation(
    modifier: Modifier,
    onEvent: (CourseOperationEvent) -> Unit
) {
    Column(modifier.padding(12.dp, 0.dp)) {
        CardItem(
            modifier = modifier
                .clickable {
                    onEvent(CourseOperationEvent.NavigateToCreateCheckIn)
                }
                .background(Teal200),
            text = stringResource(id = R.string.create_check_in)
        )
        Spacer(modifier = modifier.height(10.dp))
        CardItem(
            modifier = modifier.clickable {
                onEvent(CourseOperationEvent.NavigateToCheckInList)
            },
            text = stringResource(id = R.string.check_in_list)
        )
    }
}


@Composable
fun CardItem(
    modifier: Modifier,
    text: String
) {
    Card(shape = RoundedCornerShape(8.dp)) {
        Column(
            modifier = modifier
                .height(180.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(text = text, style = MaterialTheme.typography.h4)
        }
    }
}