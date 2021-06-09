package com.de.signcloud.ui.createcourse

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.ui.components.GeneralTextField
import com.de.signcloud.ui.components.ReadonlyTextField
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack
import com.de.signcloud.ui.components.SingleChoiceTextFieldDialog
import com.de.signcloud.ui.components.textfieldstate.BigButton
import com.de.signcloud.ui.components.textfieldstate.GenerateNotNullState
import com.de.signcloud.ui.components.textfieldstate.GenerateState
import com.de.signcloud.ui.theme.SignCloudTheme

sealed class CreateCourseEvent {
    object OnCourseCreate : CreateCourseEvent()
    object SelectSchool : CreateCourseEvent()
    object NavigateBack : CreateCourseEvent()
}

@Composable
fun CreateCourse(
    modifier: Modifier = Modifier,
    gradeItems: List<String>,
    semesterItems: List<String>,
    state: State = State(),
    onEvent: (CreateCourseEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.create_course),
                onBackPressed = { onEvent(CreateCourseEvent.NavigateBack) }
            )
        },
        content = {
            LazyColumn(modifier = modifier) {
                item {
                    Spacer(modifier = Modifier.height(16.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp)
                    ) {
                        CreateCourseContent(
                            state = state,
                            gradeItems = gradeItems,
                            semesterItems = semesterItems,
                            onEvent = onEvent
                        )
                    }
                }
            }
        }
    )
}

@Composable
fun CreateCourseContent(
    state: State,
    gradeItems: List<String>,
    semesterItems: List<String>,
    onEvent: (CreateCourseEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        GeneralTextField(generalTextFieldState = state.courseNameState,
            hintText = stringResource(id = R.string.course_name),
            onImeAction = {}
        )
        Spacer(modifier = Modifier.height(16.dp))

        SingleChoiceTextFieldDialog(
            label = stringResource(id = R.string.select_grade),
            items = gradeItems,
            generateTextFieldState = state.gradeSelectedState
        )
        Spacer(modifier = Modifier.height(16.dp))

        SingleChoiceTextFieldDialog(
            label = stringResource(id = R.string.select_semester),
            items = semesterItems,
            generateTextFieldState = state.semesterSelectedState
        )
        Spacer(modifier = Modifier.height(16.dp))

        ReadonlyTextField(
            state.schoolSelectState,
            label = { Text(text = stringResource(id = R.string.select_school)) },
            onClick = { onEvent(CreateCourseEvent.SelectSchool) }
        )
        Spacer(modifier = Modifier.height(16.dp))

        GeneralTextField(generalTextFieldState = state.courseRequirementsState,
            hintText = stringResource(id = R.string.course_requirement),
            onImeAction = {}
        )
        Spacer(modifier = Modifier.height(16.dp))

        GeneralTextField(generalTextFieldState = state.classScheduleState,
            hintText = stringResource(id = R.string.class_schedule),
            onImeAction = {}
        )
        Spacer(modifier = Modifier.height(16.dp))

        GeneralTextField(generalTextFieldState = state.examArrangementState,
            hintText = stringResource(id = R.string.exam_arrangement),
            onImeAction = {}
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            enabled = state.courseNameState.isValid &&
                    state.gradeSelectedState.isValid &&
                    state.semesterSelectedState.isValid &&
                    state.schoolSelectState.isValid,
            onClick = {
                onEvent(CreateCourseEvent.OnCourseCreate)
            },
        ) {
            Text(text = stringResource(id = R.string.create_course))
        }

    }
}


@Preview
@Composable
fun CreateCoursePreview() {
    SignCloudTheme {
        CreateCourse(
            gradeItems = listOf("1", "2", "3"),
            semesterItems = listOf("1", "2", "3"),
        ) {}
    }
}
