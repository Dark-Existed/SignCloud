package com.de.signcloud.ui.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterCenterFocus
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.bean.GetCoursesCreateResponse
import com.de.signcloud.ui.components.LoadNetworkImageWithToken
import com.de.signcloud.ui.components.SignCloudTopAppBarWithAction
import com.de.signcloud.ui.theme.SignCloudTheme
import com.google.accompanist.insets.statusBarsHeight


@Composable
fun Courses(
    modifier: Modifier = Modifier,
    isStudent: Boolean = false,
    courseCreateList: List<GetCoursesCreateResponse.Course> = emptyList(),
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
            if (isStudent) {

            } else {
                TeacherCourseList(courses = courseCreateList)
            }
        }
    )
}

@Composable
fun StudentCourseList(
    modifier: Modifier = Modifier
) {

}

@Composable
fun TeacherCourseList(
    modifier: Modifier = Modifier,
    courses: List<GetCoursesCreateResponse.Course>
) {
    LazyColumn(modifier = modifier) {
        item {
            Spacer(Modifier.statusBarsHeight())
        }
        itemsIndexed(courses) { index, course ->
            TeacherCourseCardViewItem(course = course)
        }
    }
}

@Composable
fun TeacherCourseCardViewItem(
    modifier: Modifier = Modifier,
    course: GetCoursesCreateResponse.Course,
    shape: Shape = RectangleShape,
    elevation: Dp = 0.dp,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    iconSize: Dp = 16.dp
) {
    Surface(
        elevation = elevation,
        shape = shape,
        modifier = modifier
    ) {
        Row(modifier = modifier) {
            LoadNetworkImageWithToken(imageUrl = course.cover, modifier = Modifier.aspectRatio(1f))
            Column(
                modifier = Modifier.padding(
                    start = 16.dp,
                    top = 16.dp,
                    end = 16.dp,
                    bottom = 8.dp
                )
            ) {
                Text(
                    text = course.name,
                    style = titleStyle,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .weight(1f)
                        .padding(bottom = 4.dp)
                )

            }
        }
    }
}

@Composable
fun StudentCourseCardViewItem(
    modifier: Modifier = Modifier
) {

}


@Preview
@Composable
fun CoursePreview() {
    SignCloudTheme {
        Courses() {

        }
    }
}

