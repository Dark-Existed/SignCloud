package com.de.signcloud.ui.components.textfieldstate

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BigButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    text: String = "test",
) {
    Button(
        onClick = onClick,
        enabled = enabled,
        shape = MaterialTheme.shapes.small.copy(
            bottomEnd = ZeroCornerSize,
            bottomStart = ZeroCornerSize
        ),
        modifier = Modifier
            .height(56.dp)
            .fillMaxWidth(),
    ) {
        Text(
            text = text,
            modifier = modifier
                .fillMaxWidth()
        )
    }
}