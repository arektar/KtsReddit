package com.example.ktsreddit.data.storage.shared

import android.content.Context
import com.example.ktsreddit.app.KtsRedditApplication

object SharedWorker {
    private val sharedPref =
        KtsRedditApplication.appContext.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE)

    private val ignoreOnBoarding = sharedPref.getBoolean("ignoreOnboarding", false)
    private val authToken = sharedPref.getString("authToken", null)


    fun getIgnoreOnBoarding(): Boolean {
        return ignoreOnBoarding
    }

    fun getAuthToken(): String? {
        return authToken
    }

    fun setIgnoreOnBoarding(ignoreOnBoarding: Boolean) {
        sharedPref.edit().putBoolean("ignoreOnboarding", ignoreOnBoarding).apply()
    }

    fun setAuthToken(token: String) {
        sharedPref.edit().putString("authToken", token).apply()
    }


}