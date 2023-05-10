package com.example.ktsreddit.presentation.common.utils

import androidx.compose.runtime.staticCompositionLocalOf
import androidx.lifecycle.LifecycleOwner

val LocalViewLifecycleOwner =
    staticCompositionLocalOf<LifecycleOwner> { noLocalProvidedFor("LocalLifecycleOwner") }

private fun noLocalProvidedFor(name: String): Nothing {
    error("CompositionLocal $name not present.")

}