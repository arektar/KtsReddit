package com.example.ktsreddit.app

import android.app.Application
import android.content.Context
import com.example.ktsreddit.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class KtsRedditApplication : Application()  {


    private val appModule = DI().getAppModule()

    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@KtsRedditApplication)
            modules(appModule)
        }

        _context = this

    }




    companion object {
        var _context: Context? = null
        val appContext: Context
            get() = _context ?: error("App is not initialized")
    }

}
