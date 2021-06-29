package com.de.signcloud.ui.setting

import android.widget.RadioGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.selection.selectable
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.de.signcloud.R
import com.de.signcloud.ui.components.SignCloudTopAppBarWithBack
import com.de.signcloud.ui.theme.SignCloudTheme

sealed class ChangeRoleEvent() {
    data class ChangeRole(val roleType: Int) : ChangeRoleEvent()
    object NavigateBack : ChangeRoleEvent()
}

@Composable
fun ChangeRole(
    modifier: Modifier = Modifier,
    options: List<String>,
    curRoleIndex: Int,
    onEvent: (ChangeRoleEvent) -> Unit
) {
    Scaffold(
        topBar = {
            SignCloudTopAppBarWithBack(
                topAppBarText = stringResource(id = R.string.change_role),
                onBackPressed = { onEvent(ChangeRoleEvent.NavigateBack) }
            )
        },
        content = {
            ChangeRoleContent(
                modifier = modifier,
                options = options,
                curRoleIndex = curRoleIndex,
                onEvent = onEvent
            )
        }
    )
}

@Composable
fun ChangeRoleContent(
    modifier: Modifier,
    options: List<String>,
    curRoleIndex: Int,
    onEvent: (ChangeRoleEvent) -> Unit
) {
    Column(modifier.padding(12.dp, 0.dp)) {
        val selectedOptionIndex = remember { mutableStateOf(curRoleIndex) }
        LazyColumn {
            itemsIndexed(options) { index, item ->
                Spacer(modifier = modifier.height(10.dp))
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = index == selectedOptionIndex.value,
                            onClick = {
                                selectedOptionIndex.value = index
                            }
                        )
                        .padding(horizontal = 16.dp)
                ) {
                    RadioButton(
                        selected = index == selectedOptionIndex.value,
                        onClick = {
                            selectedOptionIndex.value = index
                        },
                        colors = RadioButtonDefaults.colors(
                            selectedColor = SignCloudTheme.colors.primary
                        )
                    )
                    Text(
                        text = item,
                        modifier = Modifier.padding(start = 16.dp),
                        fontSize = 17.sp
                    )
                }
            }
        }
        Spacer(modifier = modifier.height(12.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth(),
            onClick = {
                onEvent(ChangeRoleEvent.ChangeRole(selectedOptionIndex.value))
            }
        ) {
            Text(text = stringResource(id = R.string.confirm))
        }
    }
}