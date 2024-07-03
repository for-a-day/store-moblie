package com.nagane.table.ui.screen.home

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.nagane.table.ui.main.Screens

fun NavGraphBuilder.homeRoute(navController: NavController) {
    navigation(
        startDestination = Screens.Home.route,
        route = Screens.Home.route + "_route"
    ) {
        composable(Screens.Home.route) {

        }
    }
}