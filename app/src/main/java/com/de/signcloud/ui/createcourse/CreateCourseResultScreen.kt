package com.de.signcloud.ui.createcourse

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack
import com.de.signcloud.ui.theme.SignCloudTheme
import com.google.accompanist.coil.rememberCoilPainter

sealed class CreateResultEvent {
    object NavigateBack : CreateCourseEvent()
}

@Composable
fun CreateResult(
    modifier: Modifier = Modifier,
    courseCode: String = "",
    imageUrl: String = "",
    onEvent: (CreateCourseEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.create_result),
                onBackPressed = { onEvent(CreateResultEvent.NavigateBack) }
            )
        },
        content = {
            Column(modifier = modifier.fillMaxSize()) {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .padding(horizontal = 24.dp)
                ) {
                    CreateResultContent(
                        modifier = modifier,
                        onEvent = onEvent,
                    )
                }
            }
        }
    )
}

@Composable
fun CreateResultContent(
    modifier: Modifier = Modifier,
    courseCode: String = "",
    imageUrl: String = "",
    onEvent: (CreateCourseEvent) -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.course_code),
            color = SignCloudTheme.colors.primary,
            fontSize = 20.sp
        )
        Text(
            text = courseCode,
            color = SignCloudTheme.colors.primary,
            fontSize = 18.sp
        )
        Spacer(modifier = modifier.height(24.dp))
        Image(
            painter = rememberCoilPainter(request = imageUrl),
            contentDescription = null,
            modifier = modifier.size(180.dp)
        )
    }
}


@Preview
@Composable
fun CreateResultPreview() {
    SignCloudTheme {
        CreateResult {}
    }
}