package com.nagane.table.ui.screen.common

import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.input.TextFieldValue
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub

@Composable
fun CustomOutlinedTextField(
    value: TextFieldValue,
    isAdmin: Boolean = false,
    onValueChange: (TextFieldValue) -> Unit,
    label: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    // imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = {
            Text(
                text = label,
                style = NaganeTypography.p
            )
        },
//        modifier = Modifier
//            .onFocusChanged { focusState ->
//
//            },
        textStyle = NaganeTypography.p,
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = {
                onImeAction()
            }
        ),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            // focusedTextColor = if (isAdmin) nagane_theme_sub else nagane_theme_main,
            // unfocusedTextColor = if (isAdmin) nagane_theme_sub else nagane_theme_main,
            focusedBorderColor = if (isAdmin) nagane_theme_sub else nagane_theme_main,
            unfocusedBorderColor = if (isAdmin) nagane_theme_sub else nagane_theme_main,
            focusedLabelColor = if (isAdmin) nagane_theme_sub else nagane_theme_main,
            unfocusedLabelColor = if (isAdmin) nagane_theme_sub else nagane_theme_main,
        )
    )
}