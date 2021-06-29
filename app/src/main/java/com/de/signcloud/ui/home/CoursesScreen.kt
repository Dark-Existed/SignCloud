package com.de.signcloud.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.de.signcloud.R
import com.de.signcloud.bean.Course
import com.de.signcloud.bean.GetCoursesCreateResponse
import com.de.signcloud.bean.GetJoinedCourseResponse
import com.de.signcloud.ui.components.LoadNetworkImageWithToken
import com.de.signcloud.ui.components.NoItems
import com.de.signcloud.ui.components.SignCloudTopAppBar
import com.de.signcloud.ui.components.SignCloudTopAppBarWithAction
import com.de.signcloud.ui.theme.SignCloudTheme
import com.google.accompanist.insets.statusBarsHeight


@Composable
fun Courses(
    modifier: Modifier = Modifier,
    isStudent: Boolean = false,
    courseCreateList: List<Course> = emptyList(),
    courseJoinedList: List<Course> = emptyList(),
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.padding(12.dp, 0.dp),
                backgroundColor = MaterialTheme.colors.surface,
                elevation = 0.dp
            ) {
                Text(
                    text = stringResource(id = R.string.courses),
                    textAlign = TextAlign.Start,
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentSize(Alignment.CenterStart)
                        .weight(10F),
                    fontSize = 20.sp
                )
                if (isStudent) {
                    Menu(modifier.weight(1F), onEvent)
                } else {
                    Icon(
                        Icons.Filled.Add,
                        contentDescription = null,
                        modifier = Modifier.clickable {
                            onEvent(HomeEvent.NavigateToCreateCourse)
                        }
                    )
                }
            }
        },
        content = {
            if (isStudent) {
                StudentCourseList(courses = courseJoinedList, onEvent = onEvent)
            } else {
                TeacherCourseList(courses = courseCreateList, onEvent = onEvent)
            }
        }
    )
}


@Composable
fun Menu(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .wrapContentSize(Alignment.TopStart)
    ) {
        IconButton(onClick = { expanded.value = true }) {
            Icon(Icons.Default.MoreVert, contentDescription = null)
        }
        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            DropdownMenuItem(onClick = { onEvent(HomeEvent.NavigateToScanCode) }) {
                Text(stringResource(id = R.string.scan_code))
            }
            DropdownMenuItem(onClick = { onEvent(HomeEvent.NavigateToJoinCourse) }) {
                Text(stringResource(id = R.string.search_course))
            }
        }
    }
}

@Composable
fun StudentCourseList(
    modifier: Modifier = Modifier,
    courses: List<Course>,
    onEvent: (HomeEvent) -> Unit
) {
    if (courses.isEmpty()) {
        NoItems()
    } else {
        LazyColumn(modifier = modifier.padding(12.dp, 0.dp)) {
            item { Spacer(modifier = modifier.statusBarsHeight()) }
            items(courses) { course ->
                Spacer(modifier = modifier.height(12.dp))
                StudentCourseCardViewItem(
                    modifier = Modifier
                        .height(96.dp)
                        .clickable {
                            onEvent(HomeEvent.NavigateToCourseDetail(course))
                        },
                    course = course,
                    shape = RoundedCornerShape(topStart = 24.dp),
                    onEvent = onEvent
                )
            }
            item {
                Spacer(Modifier.height(64.dp))
            }
        }
    }

}

@Composable
fun TeacherCourseList(
    modifier: Modifier = Modifier,
    courses: List<Course>,
    onEvent: (HomeEvent) -> Unit
) {
    if (courses.isEmpty()) {
        NoItems()
    } else {
        LazyColumn(modifier = modifier.padding(12.dp, 0.dp)) {
            item {
                Spacer(Modifier.statusBarsHeight())
            }
            items(courses) { course ->
                Spacer(modifier = modifier.height(12.dp))
                TeacherCourseCardViewItem(
                    modifier = Modifier
                        .height(96.dp)
                        .clickable {
                            onEvent(HomeEvent.NavigateToCourseDetail(course))
                        },
                    course = course,
                    shape = RoundedCornerShape(topStart = 24.dp),
                    onEvent = onEvent
                )
            }
            item {
                Spacer(Modifier.height(64.dp))
            }
        }
    }

}

@Composable
fun TeacherCourseCardViewItem(
    modifier: Modifier = Modifier,
    course: Course,
    shape: Shape = RectangleShape,
    gradient: List<Color> = SignCloudTheme.colors.gradient3_2,
    elevation: Dp = 0.dp,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    onEvent: (HomeEvent) -> Unit
) {
    Surface(
        elevation = elevation,
        shape = shape,
        modifier = modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(gradient)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                LoadNetworkImageWithToken(
                    imageUrl = course.cover,
                    modifier = Modifier.aspectRatio(1f)
                )
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
                        maxLines = 1,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 4.dp)
                    )
                    Text(
                        text = course.school + '-' + course.college,
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        maxLines = 2,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = course.teacher,
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        maxLines = 1,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start)
                    )
                }
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = null,
                    modifier = modifier
                        .clickable { onEvent(HomeEvent.NavigateToCourseOperation(course.code)) }
                        .padding(12.dp, 0.dp)
                        .size(32.dp)
                )
            }
        }
    }
}

@Composable
fun StudentCourseCardViewItem(
    modifier: Modifier = Modifier,
    course: Course,
    shape: Shape = RectangleShape,
    gradient: List<Color> = SignCloudTheme.colors.gradient3_2,
    elevation: Dp = 0.dp,
    titleStyle: TextStyle = MaterialTheme.typography.subtitle1,
    onEvent: (HomeEvent) -> Unit
) {
    Surface(
        elevation = elevation,
        shape = shape,
        modifier = modifier
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .background(Brush.horizontalGradient(gradient)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row {
                LoadNetworkImageWithToken(
                    imageUrl = course.cover,
                    modifier = Modifier.aspectRatio(1f)
                )
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
                        maxLines = 1,
                        color = Color.White,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier
                            .weight(1f)
                            .padding(bottom = 4.dp)
                    )
                    Text(
                        text = course.school + '-' + course.college,
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        maxLines = 2,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start)
                    )
                    Text(
                        text = course.teacher,
                        style = MaterialTheme.typography.caption,
                        color = Color.White,
                        maxLines = 1,
                        modifier = Modifier
                            .weight(1f)
                            .wrapContentWidth(Alignment.Start)
                    )
                }
            }
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Icon(
                    Icons.Filled.Menu,
                    contentDescription = null,
                    modifier = modifier
                        .clickable { onEvent(HomeEvent.NavigateToCourseOperation(course.code)) }
                        .padding(12.dp, 0.dp)
                        .size(32.dp)
                )
            }

        }
    }
}


@Preview
@Composable
fun CoursePreview() {
    SignCloudTheme {
        Courses() {

        }
    }
}

