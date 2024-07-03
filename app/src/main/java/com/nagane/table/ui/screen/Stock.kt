package com.nagane.table.ui.screen

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import com.nagane.table.R
import com.nagane.table.ui.theme.NaganeTypography

@Composable
fun TableCode(
    value: String = "",
    onChange: (String) -> Unit = {},
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = "",
        onValueChange = {

        },
        label = {
            Text(
                text = stringResource(id = R.string.table_code),
                style = NaganeTypography.p
            )
        },
        modifier = Modifier
            .onFocusChanged { focusState ->

            },
        textStyle = NaganeTypography.p,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
        singleLine = true
    )
}

@Composable
fun StoreCode(
    value: String = "",
    onChange: (String) -> Unit = {},
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    val showPassword = rememberSaveable { mutableStateOf(false) }

    OutlinedTextField(
        value = "",
        onValueChange = {
        },
        label = {
            Text(
                text = stringResource(id = R.string.store_code),
                style = NaganeTypography.p
            )
        },
        trailingIcon = {
            if (showPassword.value) {
                IconButton(onClick = { showPassword.value = false }) {
                    Icon(
                        imageVector = Icons.Filled.Visibility,
                        contentDescription = null
                    )
                }
            } else {
                IconButton(onClick = { showPassword.value = true }) {
                    Icon(
                        imageVector = Icons.Filled.VisibilityOff,
                        contentDescription = null
                    )
                }
            }
        },
        modifier = Modifier
            .onFocusChanged { focusState ->

            },
        textStyle = NaganeTypography.p,

        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = imeAction,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
        singleLine = true
    )
}