package com.nagane.table.ui.main

sealed class Screens(val route: String) {
    data object Splash : Screens("splash")
    data object Login : Screens("login")

    data object Home : Screens("home")

    data object Order : Screens("order") {
        data object OrderResult : Screens("orderResult")
    }

    data object Bill : Screens("bill")

    data object Admin : Screens("admin") {
        data object AdminLogin : Screens("login")

        data object Logout : Screens("logout")
    }
}