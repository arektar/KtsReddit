package com.example.ktsreddit.app
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ktsreddit.R
import com.kts.github.data.network.Networking
import timber.log.Timber

class MainActivity : AppCompatActivity(R.layout.activity_main){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Networking.init(this)
        Timber.plant(Timber.DebugTree())
    }
}
