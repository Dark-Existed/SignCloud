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
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack
import com.de.signcloud.ui.components.SingleChoiceTextFieldDialog
import com.de.signcloud.ui.components.textfieldstate.GenerateNotNullState
import com.de.signcloud.ui.components.textfieldstate.GenerateState
import com.de.signcloud.ui.theme.SignCloudTheme

sealed class CreateCourseEvent {
    data class OnCourseCreate(val courseName: String) : CreateCourseEvent()
    object NavigateBack : CreateCourseEvent()
}

@Composable
fun CreateCourse(
    modifier: Modifier = Modifier,
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
                        CreateCourseContent(onEvent = onEvent)
                    }
                }
            }
        }
    )
}

@Composable
fun CreateCourseContent(
    onEvent: (CreateCourseEvent) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {

        val courseNameState = remember { GenerateNotNullState() }
        GeneralTextField(generalTextFieldState = courseNameState,
            hintText = stringResource(id = R.string.course_name),
            onImeAction = {}
        )
        Spacer(modifier = Modifier.height(16.dp))

        val gradeSelectedState = remember { GenerateState() }
        SingleChoiceTextFieldDialog(
            label = stringResource(id = R.string.select_grade),
            items = listOf("1", "2", "3"),
            generateTextFieldState = gradeSelectedState
        )
        Spacer(modifier = Modifier.height(16.dp))

        val semesterSelectedState = remember { GenerateState() }
        SingleChoiceTextFieldDialog(
            label = stringResource(id = R.string.select_semester),
            items = listOf("1", "2", "3"),
            generateTextFieldState = semesterSelectedState
        )
        Spacer(modifier = Modifier.height(16.dp))

        val courseRequirementsState = remember { GenerateState() }
        GeneralTextField(generalTextFieldState = courseRequirementsState,
            hintText = stringResource(id = R.string.course_requirement),
            onImeAction = {}
        )
        Spacer(modifier = Modifier.height(16.dp))

        val classScheduleState = remember { GenerateState() }
        GeneralTextField(generalTextFieldState = classScheduleState,
            hintText = stringResource(id = R.string.class_schedule),
            onImeAction = {}
        )
        Spacer(modifier = Modifier.height(16.dp))

        val examArrangementState = remember { GenerateState() }
        GeneralTextField(generalTextFieldState = examArrangementState,
            hintText = stringResource(id = R.string.exam_arrangement),
            onImeAction = {}
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {},
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Text(text = stringResource(id = R.string.create_course))
        }

    }
}


@Preview
@Composable
fun CreateCoursePreview() {
    SignCloudTheme {
        CreateCourse {}
    }
}
