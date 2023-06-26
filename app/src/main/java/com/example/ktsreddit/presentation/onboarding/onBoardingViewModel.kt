package com.example.ktsreddit.presentation.onboarding

import androidx.lifecycle.ViewModel
import com.example.ktsreddit.data.storage.shared.KeyValueStorage
import com.example.ktsreddit.presentation.common.navigation.NawRoute
import com.example.ktsreddit.presentation.common.utils.OneTimeEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class OnBoardingViewModel:ViewModel() {


    private val mutableNavEvent = OneTimeEvent<NawRoute>()
    val navEvents: Flow<NawRoute>
        get() = mutableNavEvent.receiveAsFlow()

    val sharedMem = KeyValueStorage


    fun onNextClick() {
        sharedMem.setIgnoreOnBoarding(true)
        mutableNavEvent.trySend(NawRoute.Auth)
    }

    private fun checkIgnoreOnBoarding(){
        if (sharedMem.getIgnoreOnBoarding()){
            mutableNavEvent.trySend(NawRoute.Auth)
        }
    }

    init {
        checkIgnoreOnBoarding()

    }





}