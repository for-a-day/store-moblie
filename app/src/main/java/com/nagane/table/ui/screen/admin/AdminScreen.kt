package com.nagane.table.ui.screen.admin

import androidx.compose.foundation.background
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.nagane.table.R
import com.nagane.table.ui.screen.common.BackButton
import com.nagane.table.ui.screen.common.CustomAppBarUI
import com.nagane.table.ui.screen.common.CustomOutlinedTextField
import com.nagane.table.ui.screen.common.LoginInfo
import com.nagane.table.ui.theme.NaganeTableTheme
import com.nagane.table.ui.theme.NaganeTypography
import com.nagane.table.ui.theme.nagane_theme_light_0
import com.nagane.table.ui.theme.nagane_theme_light_6
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub
import kotlinx.coroutines.launch


@Composable
fun AdminScreen(navController: NavController) {

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        topBar = {
            CustomAppBarUI(
                title = stringResource(id = R.string.admin_title),
                leftButton = {
                    BackButton(
                        onClick = {
                            navController.popBackStack()
                        },
                        tint = nagane_theme_main
                    )
                },
                backgroundColor = nagane_theme_sub,
                subColor = nagane_theme_main
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .verticalScroll(rememberScrollState())
                .background(nagane_theme_main),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoginAdmin(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 20.dp),
                navController = navController
            )

        }

    }
}

@Composable
fun LoginAdmin(
    navController: NavController,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {

    val tableCode = remember { mutableStateOf(TextFieldValue("")) }
    val storeCode = remember { mutableStateOf(TextFieldValue("")) }
    val coroutineScope = rememberCoroutineScope()

    val focusRequester = remember { FocusRequester() }
    val context = LocalContext.current

    val allFieldsFilled = tableCode.value.text.isNotEmpty() &&
            storeCode.value.text.isNotEmpty()


    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {


        LoginInfo(
            logoImage = R.drawable.nagane_dark_b,
            firstTitle = R.string.welcome_title,
            secondTitle = R.string.admin_login_title,
            titleColor = nagane_theme_sub
        )

        // 테이블 코드
        CustomOutlinedTextField(
            value = tableCode.value,
            onValueChange = {
                tableCode.value = it
            },
            label = stringResource(id = R.string.table_code),
            isAdmin = true,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // 가맹점 코드
        CustomOutlinedTextField(
            value = storeCode.value,
            onValueChange = {
                storeCode.value = it
            },
            label = stringResource(id = R.string.store_code),
            isAdmin = true,
        )


        Button(
            onClick = {
                coroutineScope.launch {

                }
            },
            modifier = Modifier
                .padding(16.dp)
                .width(280.dp)
                .height(52.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = nagane_theme_sub,
                contentColor = nagane_theme_main,
                disabledContainerColor = nagane_theme_light_6.copy(alpha = 0.75f),
                disabledContentColor = nagane_theme_light_0.copy(alpha = 0.25f)
            ),
            enabled = allFieldsFilled
        ) {
            Text(
                text = stringResource(id = R.string.admin_login_btn),
                style = NaganeTypography.h5,
                color = if (allFieldsFilled) nagane_theme_main else nagane_theme_light_0.copy(alpha = 0.5f)
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
fun AdminScreenPreview() {
    NaganeTableTheme {
        Surface {
            AdminScreen(navController = rememberNavController())
        }
    }
}