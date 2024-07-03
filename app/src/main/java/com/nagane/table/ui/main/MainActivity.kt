package com.nagane.table.ui.main

import android.os.Bundle
import android.window.SplashScreen
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nagane.table.ui.screen.MainScreen
import com.nagane.table.ui.screen.SecondScreen
import com.nagane.table.ui.theme.NaganeTableTheme
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NaganeTableTheme {
                NaganeApp()
            }
        }
    }
}

@Preview
@Composable
fun NaganeApp() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "splash") {
        composable("splash") { SplashScreen(navController) }
        composable("main") { MainScreen() }
        composable("login") { LoginScreen(navController) }
    }
}

@Composable
fun NaganeNavigationGraph(
    navController: NavHostController,
    modifier: Modifier?,
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Home.route + "_route"
    ) {
        
    }
}

// Mock functions to simulate login state checking
suspend fun checkIfLoggedIn(): Boolean {
    delay(1000) // Simulate network delay
    return false // Replace with actual login state check
}

@Composable
fun SplashScreen(navController: NavHostController) {
    var isLoggedIn by remember { mutableStateOf<Boolean?>(null) }

    LaunchedEffect(Unit) {
        isLoggedIn = checkIfLoggedIn()
    }

    LaunchedEffect(isLoggedIn) {
        when (isLoggedIn) {
            true -> navController.navigate("main") {
                popUpTo("splash") { inclusive = true }
            }
            false -> navController.navigate("login") {
                popUpTo("splash") { inclusive = true }
            }
            null -> {} // Do nothing while loading
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text("Loading...")
    }
}

@Composable
fun MainScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Welcome to the Main Screen!")
    }
}

@Composable
fun LoginScreen(navController: NavHostController) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = {
            navController.navigate("main") {
                popUpTo("login") { inclusive = true }
            }
        }) {
            Text("Login")
        }
    }
}
