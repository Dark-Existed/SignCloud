package com.de.signcloud.ui.course

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed class CourseOperationEvent() {

}

@Composable
fun CourseOperation(
    modifier: Modifier = Modifier,
    isStudent: Boolean = false,
    onEvent: (CourseOperationEvent) -> Unit
) {

}

@Composable
fun CourseOperationContent(
    modifier: Modifier,
    onEvent: (CourseOperationEvent) -> Unit
) {

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
) {

}