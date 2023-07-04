package com.example.ktsreddit.app

import com.example.ktsreddit.data.OnboardingRepository
import com.example.ktsreddit.data.RedditRepository
import com.example.ktsreddit.presentation.viewmodels.OnBoardingViewModel
import com.example.ktsreddit.presentation.viewmodels.AuthViewModel
import com.example.ktsreddit.presentation.viewmodels.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module

class DI {

    fun getAppModule(): Module {
        return module{
            factory  <OnboardingRepository> { OnboardingRepository() }
            factory  <RedditRepository> { RedditRepository() }

            viewModel<OnBoardingViewModel>()
            viewModel<MainViewModel>()
            viewModel<AuthViewModel>()
        }
    }
}