package com.example.ktsreddit.presentation.common.navigation

import androidx.navigation.NavController


sealed class NavigateEvent {
    object NavigateToAuth : NavigateEvent()
    object NavigateToMain : NavigateEvent()

    fun navigate(navController: NavController) {

        when (this) {
            is NavigateEvent.NavigateToAuth -> {
                navController.navigate("auth")
            }
            is NavigateEvent.NavigateToMain -> {
                navController.navigate("main")
            }
        }

    }
}