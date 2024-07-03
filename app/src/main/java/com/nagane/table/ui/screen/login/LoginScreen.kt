package com.nagane.table.ui.screen.login

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.nagane.table.R
import com.nagane.table.ui.main.Screens
import com.nagane.table.ui.screen.checkIfLoggedIn
import com.nagane.table.ui.screen.common.TextFieldState
import com.nagane.table.ui.theme.NaganeTypography

suspend fun checkIfLoggedIn(): Boolean {
    // delay(1000)
    return false
}
@Composable
fun LoginScreen(navController: NavHostController) {
    var isLoggedIn by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        isLoggedIn = checkIfLoggedIn()
    }

    LaunchedEffect(isLoggedIn) {
        when (isLoggedIn) {
            true -> navController.navigate(Screens.Home.route) {
                popUpTo(Screens.Login.route) { inclusive = true }
            }
            false -> {}
            null -> {}
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                LoginTableOrder(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp)
                )
                Button(onClick = {
                    navController.navigate(Screens.Home.route) {
                        popUpTo(Screens.Login.route) { inclusive = true }
                    }
                }) {
                    Text("Login")
                }
            }
        }

    }
}

@Composable
fun LoginTableOrder(
    modifier: Modifier = Modifier
) {
    val tableCodeState by rememberSaveable(stateSaver = TableCodeStateSaver) {
        mutableStateOf(TableCodeState())
    }

    val storeCodeState by rememberSaveable(stateSaver = TableCodeStateSaver) {
        mutableStateOf(TableCodeState())
    }
    val focusRequester = remember { FocusRequester() }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(id = R.string.login_title),
            style = NaganeTypography.h1,
            modifier = Modifier.padding(top = 64.dp, bottom = 12.dp)
        )
        val onSubmit = {
            if (tableCodeState.isValid) {
                // TODO 가능할 시 API 요청 보내기...
            } else {
                tableCodeState.enableShowErrors()
            }
        }
        TableCode(
            tableCodeState = tableCodeState,
            onImeAction = { focusRequester.requestFocus() }
        )
    }
}

@Composable
fun TableCode(
    tableCodeState: TextFieldState = remember{ TableCodeState() },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = tableCodeState.text,
        onValueChange = {
            tableCodeState.text = it
        },
        label = {
            Text(
                text = stringResource(id = R.string.table_code),
                style = NaganeTypography.h3
            )
        },
        modifier = Modifier
            .onFocusChanged { focusState ->
                tableCodeState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    tableCodeState.enableShowErrors()
                }
            },
        textStyle = NaganeTypography.p,
        isError = tableCodeState.showErrors(),
        singleLine = true
    )
}

@Composable
fun StoreCode(
    storeCodeState: TextFieldState = remember{ TextFieldState() },
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {}
) {
    OutlinedTextField(
        value = storeCodeState.text,
        onValueChange = {
            storeCodeState.text = it
        },
        label = {
            Text(
                text = stringResource(id = R.string.table_code),
                style = NaganeTypography.h3
            )
        },
        modifier = Modifier
            .onFocusChanged { focusState ->
                storeCodeState.onFocusChange(focusState.isFocused)
                if (!focusState.isFocused) {
                    storeCodeState.enableShowErrors()
                }
            },
        textStyle = NaganeTypography.p,
        isError = storeCodeState.showErrors(),
        singleLine = true
    )
}