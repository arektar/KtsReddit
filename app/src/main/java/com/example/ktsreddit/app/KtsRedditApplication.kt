package com.example.ktsreddit.app

import android.app.Application
import com.example.ktsreddit.BuildConfig
import com.example.ktsreddit.data.network.NetworkStatusTracker
import timber.log.Timber

class KtsRedditApplication : Application()  {

    override fun onCreate() {
        super.onCreate()
        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }


        val networkStatusTracker = NetworkStatusTracker
        networkStatusTracker.init(this)

    }
}
