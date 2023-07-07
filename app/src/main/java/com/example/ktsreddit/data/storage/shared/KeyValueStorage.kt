package com.example.ktsreddit.data.storage.shared

import android.content.Context
import com.example.ktsreddit.app.KtsRedditApplication

object KeyValueStorage {
    private val sharedPref =
        KtsRedditApplication.appContext.getSharedPreferences("SharedPrefs", Context.MODE_PRIVATE)



    fun getIgnoreOnBoarding(): Boolean {
        return sharedPref.getBoolean("ignoreOnboarding", false)
    }

    fun getAuthToken(): String? {
        return sharedPref.getString("authToken", null)
    }

    fun getRefreshToken(): String? {
        return sharedPref.getString("refreshToken", null)
    }

    fun setIgnoreOnBoarding(ignoreOnBoarding: Boolean) {
        sharedPref.edit().putBoolean("ignoreOnboarding", ignoreOnBoarding).apply()
    }

    fun setAuthToken(token: String?) {
        sharedPref.edit().putString("authToken", token).apply()
    }

    fun setRefreshToken(token: String?) {
        sharedPref.edit().putString("refreshToken", token).apply()
    }


}