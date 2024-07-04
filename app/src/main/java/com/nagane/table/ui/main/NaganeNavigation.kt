package com.nagane.table.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nagane.table.ui.screen.AdminScreen
import com.nagane.table.ui.screen.home.homeRoute
import com.nagane.table.ui.screen.login.LoginScreen

@Composable
fun NaganeNavigationGraph(
    navController: NavHostController,
    modifier: Modifier?,
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Login.route
    ) {
        homeRoute(navController)
        composable(Screens.Login.route) { LoginScreen(navController) }
        composable(Screens.Admin.route) { AdminScreen(navController) }
    }
}
