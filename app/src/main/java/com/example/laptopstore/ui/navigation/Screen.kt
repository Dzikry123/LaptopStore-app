package com.example.laptopstore.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object DetailOrder : Screen("home/{orderId}") {
        fun createRoute(orderId: Long) = "home/$orderId"
    }
}
