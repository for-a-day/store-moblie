package com.nagane.table.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavHostController
import com.nagane.table.ui.main.Screens
import com.nagane.table.ui.theme.PretendardFontFamily
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

    Box(modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center) {
        Text("Loading...",
            style = TextStyle(fontFamily = PretendardFontFamily,
                fontWeight = FontWeight.Black))
    }
}



