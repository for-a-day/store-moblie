package com.nagane.table.ui.screen.admin

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.nagane.table.R
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_extra
import com.nagane.table.ui.theme.nagane_theme_light_0
import com.nagane.table.ui.theme.nagane_theme_main

@Composable
fun ConfirmDialog(
    onClickConfirm: () -> Unit = {},
    onDismiss: () -> Unit,
    title: String,
    infoString : String,
    showInfo: Boolean,
) {

    AlertDialog(
        containerColor = nagane_theme_light_0,
        onDismissRequest = onDismiss,
        title = { Text(text = title,
            style = NaganeTypography.h2,
            color = nagane_theme_extra)},
        text = {
            Text(
                text = infoString,
                style = NaganeTypography.p,
                color = nagane_theme_extra,)
        },
        confirmButton = {
            if (!showInfo) {
                Text(stringResource(id = R.string.disconnect),
                    style = NaganeTypography.b,
                    color = nagane_theme_main,
                    modifier = Modifier.clickable(
                        onClick = onClickConfirm))
            }
        },
        dismissButton = {
            Text(if (showInfo) stringResource(id = R.string.close) else stringResource(id = R.string.cancel),
                style = NaganeTypography.b,
                color = nagane_theme_extra,
                modifier = Modifier.clickable(
                    onClick =  onDismiss)
            )
        }
    )
}