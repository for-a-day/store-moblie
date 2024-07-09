package com.nagane.table.ui.main

import android.os.Build
import android.os.Bundle

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.nagane.table.ui.screen.home.homeRoute
import com.nagane.table.ui.screen.login.LoginScreen
import com.nagane.table.ui.theme.NaganeTableTheme
import com.nagane.table.ui.theme.nagane_theme_light_0

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
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

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(nagane_theme_light_0)
    ) { innerPadding ->
        NaganeNavigationGraph(
            navController = navController,
            modifier = Modifier.padding(innerPadding))
    }
}


