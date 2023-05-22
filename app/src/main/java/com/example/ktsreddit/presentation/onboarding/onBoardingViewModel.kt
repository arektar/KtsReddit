package com.example.ktsreddit.presentation.onboarding

import androidx.lifecycle.ViewModel
import com.example.ktsreddit.presentation.common.navigation.NavigateEvent
import com.example.ktsreddit.presentation.common.utils.OneTimeEvent
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow

class OnBoardingViewModel:ViewModel() {


    private val mutableNavEvent = OneTimeEvent<NavigateEvent>()
    val navEvents: Flow<NavigateEvent>
        get() = mutableNavEvent.receiveAsFlow()


    fun onNextClick() {
        mutableNavEvent.trySend(NavigateEvent.NavigateToAuth)
    }

}