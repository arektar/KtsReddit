package com.example.ktsreddit.ui.common.compose_theme

import android.content.res.Configuration
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.platform.LocalConfiguration
import com.example.ktsreddit.presentation.ui.theme.Shapes
import com.example.ktsreddit.presentation.ui.theme.Typography


private val LocalColors = staticCompositionLocalOf { LightColorPalette }


@Composable
fun KtsRedditTheme(content: @Composable () -> Unit) {
    val orientation = LocalConfiguration.current.orientation
    val colors = if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
        DarkColorPalette
    } else {
        LightColorPalette
    }


    CompositionLocalProvider(LocalColors provides colors) {
        MaterialTheme(
            colors = colors.material,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

val MaterialTheme.myColors: MyColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColors.current