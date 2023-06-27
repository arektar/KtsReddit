package com.example.ktsreddit.data

import com.example.ktsreddit.data.storage.shared.KeyValueStorage

class OnboardingRepository {

    private val sharedMem = KeyValueStorage

    fun setIgnoreOnBoarding(ignoreOnBoarding: Boolean) {
        sharedMem.setIgnoreOnBoarding(ignoreOnBoarding)
    }

    fun getIgnoreOnBoarding(): Boolean {
        return sharedMem.getIgnoreOnBoarding()
    }
}