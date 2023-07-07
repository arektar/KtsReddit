package com.example.ktsreddit.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ktsreddit.ui.screens.AuthScreen
import com.example.ktsreddit.presentation.main.MainPageScreen
import com.example.ktsreddit.presentation.onboarding.OnBoardingScreen

@Composable
fun NavRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NawRoute.Onboarding.stringRoute) {

        composable(NawRoute.Onboarding.stringRoute) { OnBoardingScreen(navController) }

        composable(NawRoute.Auth.stringRoute) { AuthScreen(navController) }
        composable(NawRoute.Main.stringRoute) { MainPageScreen(navController) }

    }
}

enum class NawRoute(val stringRoute: String) {
    Onboarding("onboarding"),
    Auth("auth"),
    Main("main"),
}