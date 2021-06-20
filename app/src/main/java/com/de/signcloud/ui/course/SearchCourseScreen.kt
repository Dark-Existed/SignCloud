package com.de.signcloud.ui.course

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.ui.components.GeneralTextField
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack
import com.de.signcloud.ui.components.textfieldstate.GenerateNotNullState


sealed class SearchCourseEvent {
    data class OnSearchCourse(val code: String) : SearchCourseEvent()
    object NavigateBack : SearchCourseEvent()
}

@Composable
fun SearchCourse(
    modifier: Modifier = Modifier,
    onEvent: (SearchCourseEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.search_course),
                onBackPressed = { onEvent(SearchCourseEvent.NavigateBack) }
            )
        },
        content = {
            SearchCourseContent(modifier = modifier, onEvent = onEvent)
        }
    )
}


@Composable
fun SearchCourseContent(
    modifier: Modifier = Modifier,
    onEvent: (SearchCourseEvent) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp, 0.dp)
    ) {

        val classCodeState = remember { GenerateNotNullState() }
        GeneralTextField(generalTextFieldState = classCodeState,
            hintText = stringResource(id = R.string.course_code),
            onImeAction = {}
        )
        Spacer(modifier = Modifier.height(16.dp))

        Button(
            modifier = Modifier
                .fillMaxWidth(),
            enabled = classCodeState.isValid,
            onClick = {
                onEvent(SearchCourseEvent.OnSearchCourse(classCodeState.text))
            },
        ) {
            Text(text = stringResource(id = R.string.search_course))
        }

    }

}