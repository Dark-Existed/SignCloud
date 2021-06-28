package com.de.signcloud.ui.checkin

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.bean.GetCourseStudentResponse
import com.de.signcloud.ui.components.NoItems
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack

sealed class CourseStudentEvent {
    object NavigateBack : CourseStudentEvent()
    object RefreshList : CourseStudentEvent()
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
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp, 0.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 28.dp),
            onClick = { onEvent(CourseStudentEvent.RefreshList) },
        ) {
            Text(
                text = stringResource(id = R.string.refresh_list),
                style = MaterialTheme.typography.subtitle2
            )
        }
        Spacer(modifier = modifier.height(16.dp))
        if (courseMembers.isEmpty()) {
            NoItems()
        } else {
            LazyColumn(modifier.fillMaxWidth()) {
                items(courseMembers) { courseMember ->
                    CourseMemberItem(modifier = modifier, courseMember = courseMember)
                    Spacer(modifier = modifier.height(12.dp))
                }
            }
        }
    }
}

@Composable
fun CourseMemberItem(
    modifier: Modifier,
    courseMember: GetCourseStudentResponse.CourseStudent
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .height(80.dp)
            .clickable {
            },
        shape = RoundedCornerShape(8.dp),
        elevation = 6.dp
    ) {
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Row(
                modifier = modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = courseMember.ino, modifier = modifier.padding(12.dp, 0.dp))
                Text(text = courseMember.userName)
                Text(
                    text = stringResource(id = R.string.experience) + courseMember.experience,
                    modifier = modifier.padding(12.dp, 0.dp)
                )
            }
        }
    }
}