package com.de.signcloud.ui.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudTopAppBar
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack


@Composable
fun Me(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBar(topAppBarText = stringResource(id = R.string.me))
        },
        content = {
            MeContent(modifier = modifier, onEvent = onEvent)
        }
    )
}


@Composable
fun MeContent(
    modifier: Modifier = Modifier,
    onEvent: (HomeEvent) -> Unit
) {
    Column(modifier.padding(12.dp, 0.dp)) {


        TextButton(
            onClick = { onEvent(HomeEvent.SignOut) },
            modifier = modifier.fillMaxWidth()
        ) {
            Text(text = stringResource(id = R.string.sign_out),color = Color(0xffe21365))
        }
    }
}