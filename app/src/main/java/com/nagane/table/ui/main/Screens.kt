package com.nagane.table.ui.main

sealed class Screens(val route: String) {
    object splash : Screens("splash")
    object Login : Screens("login")

    object Home : Screens("home")
    object Menu : Screens("menu") {
        object MenuDetail : Screens("menu/{menuNo}")
    }

    object Order : Screens("order") {
        object OrderResult : Screens("orderResult")
    }

    object Bill : Screens("bill/{orderNo}")

    object Admin : Screens("admin") {
        object AdminLogin : Screens("login")

        object Logout : Screens("logout")
    }
}