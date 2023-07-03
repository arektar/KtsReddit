package com.example.ktsreddit.app

import android.app.Application
import android.content.Context
import com.example.ktsreddit.BuildConfig
import com.example.ktsreddit.data.OnboardingRepository
import com.example.ktsreddit.presentation.viewmodels.MainViewModel
import com.example.ktsreddit.presentation.onboarding.OnBoardingViewModel
import com.example.ktsreddit.presentation.viewmodels.AuthViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import timber.log.Timber

class KtsRedditApplication : Application()  {


    private val appModule = module{
        single <OnboardingRepository> { OnboardingRepository() }

        viewModel<OnBoardingViewModel>()
        viewModel<MainViewModel>()
        viewModel<AuthViewModel>()
    }

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@KtsRedditApplication)
            modules(appModule)
        }

        _context = this

    }




    companion object {
        var _context: Context? = null
        val appContext: Context
            get() = _context ?: error("App is not initialized")
    }

}
