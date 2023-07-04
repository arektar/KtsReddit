package com.example.ktsreddit.app

import android.content.Context
import com.example.ktsreddit.data.OnboardingRepository
import com.example.ktsreddit.data.RedditRepository
import com.example.ktsreddit.data.auth.AuthRepository
import com.example.ktsreddit.data.network.NetworkStatusTracker
import com.example.ktsreddit.presentation.viewmodels.OnBoardingViewModel
import com.example.ktsreddit.presentation.viewmodels.AuthViewModel
import com.example.ktsreddit.presentation.viewmodels.MainViewModel
import net.openid.appauth.AuthorizationService
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class DI {

    fun getAppModule(appContext: Context): Module {
        return module{
            factory  <OnboardingRepository> { OnboardingRepository() }
            factory  <RedditRepository> { RedditRepository() }
            factory  <AuthRepository> { AuthRepository() }
            factory  <AuthorizationService> { AuthorizationService(appContext) }

            single  <NetworkStatusTracker> { NetworkStatusTracker }

            viewModel<OnBoardingViewModel>()
            viewModel<MainViewModel>()
            viewModel<AuthViewModel>()
        }
    }
}