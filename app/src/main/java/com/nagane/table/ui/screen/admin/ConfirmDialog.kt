package com.nagane.table.ui.screen.admin

import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import com.nagane.table.ui.theme.nagane_theme_light_0

@Composable
fun ConfirmDialog(
    onClickConfirm: () -> Unit = {},
    onDismiss: () -> Unit
) {

    AlertDialog(
        containerColor = nagane_theme_light_0,
        onDismissRequest = onDismiss,
        title = { Text(text = "경고") },
        text = { Text(text = "기기 등록을 해제하시겠습니까?") },
        confirmButton = {
            Text("확인",
                modifier = Modifier.clickable(
                    onClick = onDismiss))
        },
        dismissButton = {
            Text("취소",
                modifier = Modifier.clickable(
                    onClick =  onDismiss)
            )
        }
    )
}