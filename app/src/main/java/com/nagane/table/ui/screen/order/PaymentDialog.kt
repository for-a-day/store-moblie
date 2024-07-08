package com.nagane.table.ui.screen.order

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.nagane.table.R
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PaymentDialog(
    onDismiss: () -> Unit) {
    val scope = rememberCoroutineScope()
    var dialogMessage by remember { mutableStateOf(R.string.payment_info_first) }
    var showProgressBar by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        scope.launch {
            delay(3000)
            dialogMessage = R.string.payment_info_second
            showProgressBar = true
            delay(5000)
            dialogMessage = R.string.payment_info_third
            showProgressBar = false
            delay(3000)
            onDismiss()
        }
    }

    Dialog(onDismissRequest = { onDismiss() }) {
        Surface(
            shape = MaterialTheme.shapes.extraLarge,
            modifier = Modifier.padding(64.dp),
            color = nagane_theme_sub
        ) {
            Box(
                modifier = Modifier.padding(64.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = stringResource(id = dialogMessage),
                        style = NaganeTypography.h2,
                        textAlign = TextAlign.Center,
                        color = nagane_theme_main
                    )
                    if (showProgressBar) {
                        CircularProgressIndicator(
                            modifier = Modifier.padding(top = 32.dp),
                            color = nagane_theme_main
                        )
                    }
                }
            }
        }
    }
}