package com.nagane.table.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.nagane.table.R
import com.nagane.table.ui.main.Screens
import com.nagane.table.ui.screen.common.LoginInfo
import com.nagane.table.ui.screen.order.OrderScreen
import com.nagane.table.ui.theme.NaganeTableTheme
import com.nagane.table.ui.theme.PretendardFontFamily
import com.nagane.table.ui.theme.nagane_theme_main
import com.nagane.table.ui.theme.nagane_theme_sub
import kotlinx.coroutines.delay


suspend fun checkIfLoggedIn(): Boolean {
    delay(1000)
    return false
}

@Composable
fun CustomSplashScreen(navController: NavHostController) {
    var isLoggedIn by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        isLoggedIn = checkIfLoggedIn()
    }

    LaunchedEffect(isLoggedIn) {
        when (isLoggedIn) {
            true -> navController.navigate(Screens.Home.route) {
                popUpTo("splash") { inclusive = true }
            }
            false -> navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
            null -> {}
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(nagane_theme_main),
        contentAlignment = Alignment.Center
    ) {
        LoginInfo(
            logoImage = R.drawable.nagane_dark_b,
            firstTitle = R.string.splash_title_first,
            secondTitle = R.string.splash_title_second,
            titleColor = nagane_theme_sub
        )
    }
}

@Preview(
    device = Devices.TABLET)
@Composable
fun CustomSplashScreenPreview() {
    NaganeTableTheme {
        Surface {
            CustomSplashScreen(navController = rememberNavController())
        }
    }
}