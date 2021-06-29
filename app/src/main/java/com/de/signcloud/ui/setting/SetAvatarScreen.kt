package com.de.signcloud.ui.setting

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

sealed class SetAvatarEvent {
    object NavigateBack : SetAvatarEvent()
    object SetAvatar : SetAvatarEvent()
}


@Composable
fun SetAvatar(
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = {

        },
        content = {

        }
    )
}


@Composable
fun SetAvatarContent(
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier.padding(12.dp, 0.dp),
        verticalArrangement = Arrangement.Center
    ) {

    }

}