package com.example.ktsreddit.presentation.common.navigation

import androidx.navigation.NavController


sealed class NavigateEvent {
    object NavigateToAuth : NavigateEvent()
    object NavigateToMain : NavigateEvent()

    fun navigate(navController: NavController) {

        when (this) {
            is NavigateToAuth -> {
                navController.navigate("auth")
            }
            is NavigateToMain -> {
                navController.navigate("main")
            }
        }

    }
}