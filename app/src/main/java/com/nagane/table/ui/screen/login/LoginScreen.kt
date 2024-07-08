package com.nagane.table.ui.screen.login

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nagane.table.R
import com.nagane.table.data.model.TableLogin
import com.nagane.table.ui.main.Screens
import com.nagane.table.ui.screen.common.CustomOutlinedTextField
import com.nagane.table.ui.screen.common.LoginInfo
import com.nagane.table.ui.theme.NaganeTableTheme
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_light_0
import com.nagane.table.ui.theme.nagane_theme_light_8
import com.nagane.table.ui.theme.nagane_theme_main



@Composable
fun LoginScreen(
    navController: NavController,
    loginViewModel: LoginViewModel = viewModel(),
) {
    var isLoggedIn by remember { mutableStateOf<Boolean?>(null) }
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        isLoggedIn = loginViewModel.checkIfTableExists()
    }

    LaunchedEffect(isLoggedIn) {
        when (isLoggedIn) {
            true -> navController.navigate(Screens.Home.route) {
                popUpTo(Screens.Login.route) { inclusive = true }
            }
            else -> {}
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
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LoginTableOrder(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                navController = navController,
                onClick = { tableLogin ->
                    loginViewModel.loginTable(
                        tableLogin,
                        onResult = { response ->
                            if (response.statusCode == 200) {
                                    navController.navigate(Screens.Home.route) {
                                    popUpTo(Screens.Login.route) { inclusive = true }
                                }
                            } else {
                                Toast.makeText(
                                    context,
                                    response?.message ?: "서버와의 통신이 불가능합니다.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    )
                }
            )
        }
    }
}

@Composable
fun LoginTableOrder(
    navController: NavController,
    modifier: Modifier = Modifier,
    onClick: (TableLogin) -> Unit = {},
) {

    val tableCode = remember { mutableStateOf(TextFieldValue("")) }
    val storeCode = remember { mutableStateOf(TextFieldValue("")) }
    val tableNumber = remember { mutableStateOf(TextFieldValue("")) }
    val tableName = remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()

    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    val allFieldsFilled = tableCode.value.text.isNotEmpty() &&
            storeCode.value.text.isNotEmpty() &&
            tableNumber.value.text.isNotEmpty() &&
            tableName.value.text.isNotEmpty()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoginInfo(
            logoImage = R.drawable.nagane_light_b,
            firstTitle = R.string.welcome_title,
            secondTitle = R.string.login_title,
            titleColor = nagane_theme_main
        )

        // 테이블 코드
        CustomOutlinedTextField(
            value = tableCode.value,
            onValueChange = {
                tableCode.value = it
            },
            label = stringResource(id = R.string.table_code),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 가맹점 코드
        CustomOutlinedTextField(
            value = storeCode.value,
            onValueChange = {
                storeCode.value = it
            },
            label = stringResource(id = R.string.store_code),
        )

        Spacer(modifier = Modifier.height(8.dp))


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

        Spacer(modifier = Modifier.height(8.dp))

        // 테이블 이름
        CustomOutlinedTextField(
            value = tableName.value,
            onValueChange = {
                tableName.value = it
            },
            label = stringResource(id = R.string.table_name),
        )

        Button(
            onClick = { onClick(
                TableLogin(
                    tableCode.value.text,
                        storeCode.value.text,
                        tableNumber.value.text.toIntOrNull() ?: -99,
                        tableName.value.text
                )
            ) },
            modifier = Modifier
                .padding(16.dp)
                .width(280.dp)
                .height(52.dp),
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

@Preview(
    device = Devices.TABLET)
@Composable
fun LoginScreenPreview() {
    NaganeTableTheme {
        Surface {
            LoginScreen(navController = rememberNavController())
        }
    }
}