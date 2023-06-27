package com.example.ktsreddit.presentation.onboarding

import androidx.lifecycle.ViewModel
import com.example.ktsreddit.data.OnboardingRepository
import com.example.ktsreddit.presentation.common.navigation.NawRoute
import com.example.ktsreddit.presentation.common.utils.OneTimeEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class OnBoardingViewModel:ViewModel() {


    private val mutableNavEvent = OneTimeEvent<NawRoute>()
    val navEvents: Flow<NawRoute>
        get() = mutableNavEvent.receiveAsFlow()

    private val repository = OnboardingRepository()


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