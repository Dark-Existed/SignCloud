package com.de.signcloud.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.*
import androidx.compose.ui.unit.dp
import com.de.signcloud.R
import com.de.signcloud.ui.components.textfieldstate.PhoneState
import com.de.signcloud.ui.components.textfieldstate.TextFieldState
import com.de.signcloud.ui.theme.*

@Composable
fun Phone(
    phoneState: PhoneState = remember { PhoneState() },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    onValueChange: (String) -> Unit = {}
) {
    OutlinedTextField(
        value = phoneState.text,
        onValueChange = {
            phoneState.text = it
            onValueChange(it)
        },
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = stringResource(id = R.string.phone),
                    style = MaterialTheme.typography.body2
                )
            }
        },
        modifier = Modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                val focused = focusState == FocusState.Active
                phoneState.onFocusChange(focused)
                if (!focused) {
                    phoneState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.body2,
        isError = phoneState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction, keyboardType = KeyboardType.Phone
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
        singleLine = true
    )
    phoneState.getError()?.let { error -> TextFieldError(textError = error) }
}


@Composable
fun Password(
    label: String,
    passwordState: TextFieldState,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {}
) {
    val showPassword = remember { mutableStateOf(false) }
    OutlinedTextField(
        value = passwordState.text,
        onValueChange = {
            passwordState.text = it
            passwordState.enableShowErrors()
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                val focused = focusState == FocusState.Active
                passwordState.onFocusChange(focused)
                if (!focused) {
                    passwordState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.body2,
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = label,
                    style = MaterialTheme.typography.body2
                )
            }
        },
        trailingIcon = {
            if (showPassword.value) {
                IconButton(onClick = { showPassword.value = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = stringResource(id = R.string.hide_password)
                    )
                }
            } else {
                IconButton(onClick = { showPassword.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = stringResource(id = R.string.show_password)
                    )
                }
            }
        },
        visualTransformation = if (showPassword.value) {
            VisualTransformation.None
        } else {
            PasswordVisualTransformation()
        },
        isError = passwordState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = imeAction),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
        singleLine = true
    )
    passwordState.getError()?.let { error -> TextFieldError(textError = error) }
}


@Composable
fun ValidateCode(
    validateCodeState: TextFieldState,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = validateCodeState.text,
        onValueChange = {
            validateCodeState.text = it
            validateCodeState.enableShowErrors()
        },
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = stringResource(id = R.string.validate_code),
                    style = MaterialTheme.typography.body2
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                val focused = focusState == FocusState.Active
                validateCodeState.onFocusChange(focused)
                if (!focused) {
                    validateCodeState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.body2,
        isError = validateCodeState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction, keyboardType = KeyboardType.Number
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
        singleLine = true
    )
    validateCodeState.getError()?.let { error -> TextFieldError(textError = error) }
}


@Composable
fun GeneralTextField(
    generalTextFieldState: TextFieldState,
    hintText: String,
    modifier: Modifier = Modifier,
    imeAction: ImeAction = ImeAction.Done,
    onImeAction: () -> Unit
) {
    OutlinedTextField(
        value = generalTextFieldState.text,
        onValueChange = {
            generalTextFieldState.text = it
            generalTextFieldState.showErrors()
        },
        label = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = hintText,
                    style = MaterialTheme.typography.body2
                )
            }
        },
        modifier = modifier
            .fillMaxWidth()
            .onFocusChanged { focusState ->
                val focused = focusState == FocusState.Active
                generalTextFieldState.onFocusChange(focused)
                if (!focused) {
                    generalTextFieldState.enableShowErrors()
                }
            },
        textStyle = MaterialTheme.typography.body2,
        isError = generalTextFieldState.showErrors(),
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction, keyboardType = KeyboardType.Text
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
        singleLine = true
    )
    generalTextFieldState.getError()?.let { error -> TextFieldError(textError = error) }
}


@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(color = MaterialTheme.colors.error)
        )
    }
}


@Composable
fun ReadonlyTextField(
    textFieldState: TextFieldState,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    label: @Composable () -> Unit
) {
    Box(modifier.fillMaxWidth()) {
        TextField(
            value = textFieldState.text,
            onValueChange = {
                textFieldState.text = it
            },
            modifier = modifier.fillMaxWidth(),
            label = label,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = SignCloudTheme.colors.uiBorder
            )
        )
        Box(
            modifier = Modifier
                .matchParentSize()
                .alpha(0f)
                .clip(
                    MaterialTheme.shapes.small.copy(
                        bottomEnd = ZeroCornerSize,
                        bottomStart = ZeroCornerSize
                    )
                )
                .clickable(onClick = onClick),
        )
    }
}
