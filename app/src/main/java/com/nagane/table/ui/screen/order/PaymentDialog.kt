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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.nagane.table.R
import com.nagane.table.ui.screen.home.CartViewModel
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun PaymentDialog(
    onDismiss: () -> Unit,
    cartViewModel: CartViewModel = viewModel(),
    paymentCase: Int,
    dineCase: Int
) {
    val scope = rememberCoroutineScope()
    var dialogMessage by remember { mutableStateOf(R.string.payment_info_first) }
    var showProgressBar by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        scope.launch {
            delay(3000)
            dialogMessage = R.string.payment_info_second
            showProgressBar = true
            delay(3000)
            
            // 이 시점에서 API 요청 실행
            val result = cartViewModel.createOrder(paymentCase, dineCase)

            if (result == 200) {
                dialogMessage = R.string.payment_info_third
                delay(5000)
                dialogMessage = R.string.payment_info_forth
                showProgressBar = false
                delay(3000)
                onDismiss() // 성공 시 콜백 실행
            } else {
                if (result == 400) {
                    dialogMessage = R.string.payment_over_count // 실패 메시지 설정
                } else {
                    dialogMessage = R.string.payment_fail // 실패 메시지 설정
                }
                showProgressBar = false
                delay(3000)
                onDismiss() // 실패 시 콜백 실행
            }
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
                        style = NaganeTypography.h1,
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