package com.example.ktsreddit.app

import android.app.Application
import com.example.ktsreddit.BuildConfig
import timber.log.Timber

class KtsRedditApplication : Application()  {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
