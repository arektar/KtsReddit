package com.example.ktsreddit.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.ui.Modifier
import com.example.ktsreddit.R
import com.example.ktsreddit.presentation.ui.pages.Onboarding
import com.example.ktsreddit.presentation.ui.theme.KtsRedditTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val hello: String = getString(R.string.start_screen_hello)
            val next: String = getString(R.string.start_screen_next)

            KtsRedditTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Onboarding(
                        hello,
                        next
                    )
                }
            }
        }
    }
}

