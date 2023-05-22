package com.example.ktsreddit.presentation.common.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.ktsreddit.presentation.auth.AuthScreen
import com.example.ktsreddit.presentation.main.MainPageScreen
import com.example.ktsreddit.presentation.onboarding.OnBoardingScreen

@Composable
fun NavRoot() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Route.Onboarding.stringRoute) {

        composable(Route.Onboarding.stringRoute) { OnBoardingScreen(navController) }

        composable(Route.Auth.stringRoute) { AuthScreen(navController) }
        composable(Route.Main.stringRoute) { MainPageScreen(navController) }

    }
}

enum class Route(val stringRoute: String) {
    Onboarding("onboarding"),
    Auth("auth"),
    Main("main"),
}