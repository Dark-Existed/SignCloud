package com.de.signcloud.ui.course

import androidx.camera.core.Preview
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext

sealed class ScanCodeEvent() {
    object NavigateBack : ScanCodeEvent()
    data class OnScanCode(val code: String) : ScanCodeEvent()
}


@Composable
fun ScanCode(
    modifier: Modifier = Modifier,
    onEvent: (ScanCodeEvent) -> Unit
) {

}


@Composable
fun ScanCodeContent(
    modifier: Modifier = Modifier,
    onEvent: (ScanCodeEvent) -> Unit
) {
    val context = LocalContext.current

}