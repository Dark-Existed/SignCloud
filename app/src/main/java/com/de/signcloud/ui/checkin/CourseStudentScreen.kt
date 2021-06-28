package com.de.signcloud.ui.checkin

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.bean.GetCourseStudentResponse
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack

sealed class CourseStudentEvent() {
    object NavigateBack : CourseStudentEvent()
}

@Composable
fun CourseStudent(
    modifier: Modifier = Modifier,
    courseMembers: List<GetCourseStudentResponse.CourseStudent> = emptyList(),
    onEvent: (CourseStudentEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.course_member),
                onBackPressed = { onEvent(CourseStudentEvent.NavigateBack) }
            )
        },
        content = {
            CourseStudentContent(
                modifier = modifier,
                courseMembers = courseMembers,
                onEvent = onEvent
            )
        }
    )
}

@Composable
fun CourseStudentContent(
    modifier: Modifier,
    courseMembers: List<GetCourseStudentResponse.CourseStudent> = emptyList(),
    onEvent: (CourseStudentEvent) -> Unit
) {
    LazyColumn(
        modifier
            .fillMaxWidth()
            .padding(12.dp, 0.dp)
    ) {
        items(courseMembers) { courseMember ->

        }
    }

}

@Composable
fun CourseMemberItem(
    modifier: Modifier,
    courseMember: GetCourseStudentResponse.CourseStudent
) {

}