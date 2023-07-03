package com.example.ktsreddit.presentation.onboarding

import androidx.lifecycle.ViewModel
import com.example.ktsreddit.data.OnboardingRepository
import com.example.ktsreddit.presentation.common.navigation.NawRoute
import com.example.ktsreddit.presentation.common.utils.OneTimeEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import org.koin.core.component.KoinComponent

class OnBoardingViewModel(
    private val repository: OnboardingRepository
):ViewModel(), KoinComponent {


    private val mutableNavEvent = OneTimeEvent<NawRoute>()
    val navEvents: Flow<NawRoute>
        get() = mutableNavEvent.receiveAsFlow()



    fun onNextClick() {
        repository.setIgnoreOnBoarding(true)
        mutableNavEvent.trySend(NawRoute.Auth)
    }

    private fun checkIgnoreOnBoarding(){
        if (repository.getIgnoreOnBoarding()){
            mutableNavEvent.trySend(NawRoute.Auth)
        }
    }

    init {
        checkIgnoreOnBoarding()

    }





}