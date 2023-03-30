package com.example.ktsreddit.presentation

import android.content.res.Configuration
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.ktsreddit.presentation.ui.pages.Onboarding
import com.example.ktsreddit.presentation.ui.theme.KtsRedditTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val isDarkTheme: Boolean = setTheme(this)
            GetFirstScreen(isDarkTheme)
        }
    }
}

@Composable
fun GetFirstScreen(isDarkTheme: Boolean) {
    KtsRedditTheme(darkTheme = isDarkTheme) {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colors.background
        ) {
            Onboarding()
        }
    }
}

fun setTheme(activity: MainActivity): Boolean {
    val orientation = activity.resources.configuration.orientation
    return orientation != Configuration.ORIENTATION_PORTRAIT
}