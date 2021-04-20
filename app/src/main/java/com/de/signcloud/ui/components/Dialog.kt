package com.de.signcloud.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.de.signcloud.ui.components.textfieldstate.TextFieldState
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.listItems


@Composable
fun SingleChoiceTextFieldDialog(
    label: String,
    items: List<String>,
    generateTextFieldState: TextFieldState,
    modifier: Modifier = Modifier
) {
    val dialog = MaterialDialog()
    dialog.build {
        listItems(
            list = items,
            onClick = { index, item ->
                generateTextFieldState.text = item
            }
        ) { index, item ->
            Box(
                modifier = modifier
                    .fillMaxWidth()
                    .height(40.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Text(item)
            }
        }
    }
    ReadonlyTextField(
        textFieldState = generateTextFieldState,
        onClick = {
            dialog.show()
        },
        label = {
            Text(text = label)
        }
    )
}