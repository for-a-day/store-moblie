package com.nagane.table.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nagane.table.ui.screen.CustomSplashScreen
import com.nagane.table.ui.screen.admin.AdminScreen
import com.nagane.table.ui.screen.home.homeRoute
import com.nagane.table.ui.screen.login.LoginScreen
import com.nagane.table.ui.screen.order.BillScreen
import com.nagane.table.ui.screen.order.OrderScreen

@Composable
fun NaganeNavigationGraph(
    navController: NavHostController,
    modifier: Modifier?,
) {
    NavHost(
        navController = navController,
        startDestination = Screens.Splash.route
    ) {
        homeRoute(navController)
        composable(Screens.Splash.route) { CustomSplashScreen(navController) }
        composable(Screens.Login.route) { LoginScreen(navController) }
        composable(Screens.Admin.route) { AdminScreen(navController) }
        composable(Screens.Order.route) { OrderScreen(navController) }
        composable(Screens.Bill.route) { BillScreen(navController) }
    }
}
