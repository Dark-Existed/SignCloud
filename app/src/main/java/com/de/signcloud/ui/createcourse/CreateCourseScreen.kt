package com.de.signcloud.ui.createcourse

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack
import com.de.signcloud.ui.theme.SignCloudTheme

sealed class CreateCourseEvent {
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



        Button(
            onClick = { },
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
