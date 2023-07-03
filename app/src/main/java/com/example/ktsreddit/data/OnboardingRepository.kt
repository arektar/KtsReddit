package com.example.ktsreddit.data

import com.example.ktsreddit.data.storage.shared.KeyValueStorage
import org.koin.core.component.KoinComponent

class OnboardingRepository:KoinComponent {

    private val sharedMem = KeyValueStorage

    fun setIgnoreOnBoarding(ignoreOnBoarding: Boolean) {
        sharedMem.setIgnoreOnBoarding(ignoreOnBoarding)
    }

    fun getIgnoreOnBoarding(): Boolean {
        return sharedMem.getIgnoreOnBoarding()
    }
}