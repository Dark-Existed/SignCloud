package com.de.signcloud.ui.course

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.de.signcloud.R
import com.de.signcloud.bean.GetCourseByCodeResponse
import com.de.signcloud.ui.components.LoadNetworkImageWithToken
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack

sealed class SearchCourseResultEvent() {
    object NavigateBack : SearchCourseResultEvent()
    data class OnJoinCourse(val code: String) : SearchCourseResultEvent()
}

@Composable
fun SearchCourseResult(
    modifier: Modifier = Modifier,
    course: GetCourseByCodeResponse.Course,
    onEvent: (SearchCourseResultEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(topAppBarText = stringResource(id = R.string.search_result),
                onBackPressed = { onEvent(SearchCourseResultEvent.NavigateBack) }
            )
        },
        content = {
            SearchCourseResultContent(
                modifier = modifier,
                course = course,
                onEvent = onEvent
            )
        }
    )
}

@Composable
fun SearchCourseResultContent(
    modifier: Modifier = Modifier,
    course: GetCourseByCodeResponse.Course,
    onEvent: (SearchCourseResultEvent) -> Unit
) {
    Column(
        modifier = modifier.padding(12.dp, 0.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = course.name,
            fontSize = 24.sp,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(16.dp))

        Text(
            text = "${course.school}-${course.college}",
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(16.dp))

        Text(text = course.grade)
        Spacer(modifier = modifier.height(16.dp))

        Text(text = course.semester)
        Spacer(modifier = modifier.height(16.dp))

        Text(
            text = course.teacher,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = modifier.height(16.dp))

        LoadNetworkImageWithToken(modifier.size(64.dp), imageUrl = course.cover)
        Spacer(modifier = modifier.height(8.dp))
        Text(text = "${stringResource(id = R.string.course_code)}: ${course.code}")
        Spacer(modifier = modifier.height(16.dp))

        Text(
            modifier = modifier.fillMaxWidth(),
            text = "${stringResource(id = R.string.course_requirement)}: ${course.courseRequirement}"
        )
        Spacer(modifier = modifier.height(16.dp))

        Text(
            modifier = modifier.fillMaxWidth(),
            text = "${stringResource(id = R.string.class_schedule)}: ${course.classSchedule}"
        )
        Spacer(modifier = modifier.height(16.dp))

        Text(
            modifier = modifier.fillMaxWidth(),
            text = "${stringResource(id = R.string.exam_arrangement)}: ${course.examArrangement}"
        )
        Spacer(modifier = modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onEvent(SearchCourseResultEvent.OnJoinCourse(course.code))
            },
        ) {
            Text(text = stringResource(id = R.string.join_course))
        }
    }
}