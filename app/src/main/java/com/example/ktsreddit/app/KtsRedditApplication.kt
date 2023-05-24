package com.example.ktsreddit.app

import android.app.Application
import android.content.Context
import com.example.ktsreddit.BuildConfig
import timber.log.Timber

class KtsRedditApplication : Application()  {

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        _context = this

    }


    companion object {
        var _context: Context? = null
        val appContext: Context
            get() = _context ?: error("App is not initialized")
    }

}
