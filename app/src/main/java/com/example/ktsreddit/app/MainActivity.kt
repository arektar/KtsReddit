package com.example.ktsreddit.app
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.ktsreddit.R
import com.example.ktsreddit.data.network.NetworkStatusTracker

class MainActivity : AppCompatActivity(R.layout.activity_main){



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val networkStatusTracker = NetworkStatusTracker
        networkStatusTracker.init(this)

    }
}

