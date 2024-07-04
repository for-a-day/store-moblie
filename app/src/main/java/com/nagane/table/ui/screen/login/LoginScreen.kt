package com.nagane.table.ui.screen.login

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nagane.table.R
import com.nagane.table.data.api.ApiResponse
import com.nagane.table.ui.main.Screens
import com.nagane.table.ui.theme.NaganeTableTheme
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_light_0
import com.nagane.table.ui.theme.nagane_theme_light_8
import com.nagane.table.ui.theme.nagane_theme_main

@Composable
fun LoginScreen(navController: NavHostController,
                loginViewModel: LoginViewModel = hiltViewModel()) {
    val isLoggedIn by loginViewModel.isLoggedIn.collectAsState()
    val loginResult by loginViewModel.loginResult.collectAsState()
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

//    LaunchedEffect(Unit) {
//        loginViewModel.checkIfLoggedIn()
//    }

    LaunchedEffect(isLoggedIn) {
        when (isLoggedIn) {
            true -> navController.navigate(Screens.Home.route) {
                popUpTo(Screens.Login.route) { inclusive = true }
            }
            false, null -> {}
        }
    }

    LaunchedEffect(loginResult) {
        loginResult?.let { response ->
            if (response is ApiResponse.Success && response.statusCode == 200) {
                navController.navigate(Screens.Home.route) {
                    popUpTo(Screens.Login.route) { inclusive = true }
                }
            } else {
                val errorMessage = when (response) {
                    is ApiResponse.Failure.ClientError -> response.message
                    is ApiResponse.Failure.ServerError -> response.message
                    is ApiResponse.Failure.Exception -> response.message ?: "An unexpected error occurred"
                    else -> "Failed to communicate with server"
                }

                showToast(
                    context,
                    errorMessage)
            }
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginTableOrder(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                loginViewModel = loginViewModel
            )
        }
    }
}

@Composable
fun LoginTableOrder(
    loginViewModel: LoginViewModel,
    modifier: Modifier = Modifier,
) {
    val tableCode = remember { mutableStateOf(TextFieldValue("")) }
    val storeCode = remember { mutableStateOf(TextFieldValue("")) }
    val tableNumber = remember { mutableStateOf(TextFieldValue("")) }
    val tableName = remember { mutableStateOf(TextFieldValue("")) }
    val context = LocalContext.current
    val allFieldsFilled = tableCode.value.text.isNotEmpty() &&
            storeCode.value.text.isNotEmpty() &&
            tableNumber.value.text.isNotEmpty() &&
            tableName.value.text.isNotEmpty()

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = stringResource(id = R.string.login_title),
            style = NaganeTypography.h1,
            modifier = Modifier.padding(top = 64.dp, bottom = 12.dp)
        )

        // 테이블 코드
        CustomOutlinedTextField(
            value = tableCode.value,
            onValueChange = { tableCode.value = it },
            label = stringResource(id = R.string.table_code),
        )

        // 가맹점 코드
        CustomOutlinedTextField(
            value = storeCode.value,
            onValueChange = { storeCode.value = it },
            label = stringResource(id = R.string.store_code),
        )

        // 테이블 번호(숫자만 가능)
        CustomOutlinedTextField(
            value = tableNumber.value,
            onValueChange = {
                if (it.text.all { char -> char.isDigit() }) {
                    tableNumber.value = it
                }
            },
            label = stringResource(id = R.string.table_number),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )

        // 테이블 이름
        CustomOutlinedTextField(
            value = tableName.value,
            onValueChange = { tableName.value = it },
            label = stringResource(id = R.string.table_name),
        )

        Button(
            onClick = {
                if (allFieldsFilled) {
                    loginViewModel.login(
                        tableCode.value.text,
                        storeCode.value.text,
                        tableNumber.value.text.toIntOrNull() ?: 0,
                        tableName.value.text
                    )
                } else {
                    showToast(
                        context,
                        "모든 값을 채워주세요"
                    )
                }
            },
            modifier = Modifier.padding(16.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = nagane_theme_main,
                contentColor = nagane_theme_light_0,
                disabledContainerColor = nagane_theme_light_8,
                disabledContentColor = nagane_theme_light_0
            )
        ) {
            Text(
                text = stringResource(id = R.string.table_login_btn),
                style = NaganeTypography.h5
            )
        }
    }
}

fun showToast(context: Context,
              message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}

@Composable
fun CustomOutlinedTextField(
    value: TextFieldValue,
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
        singleLine = true
    )
}



@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Preview
@Composable
fun LoginScreenPreview() {
    NaganeTableTheme {
        Surface {
            LoginScreen(navController = rememberNavController())
        }
    }
}