package com.de.signcloud.ui.checkin

import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

sealed class CheckInDetailEvent() {
    object NavigateBack : CheckInDetailEvent()
}

@Composable
fun CheckInDetail(
    modifier: Modifier = Modifier,
    onEvent: (CheckInListEvent) -> Unit
) {
    Scaffold(
        topBar = {},
        content = {}
    )
}

@Composable
fun CheckInDetailContent(
    modifier: Modifier = Modifier,
    onEvent: (CheckInListEvent) -> Unit
) {

}