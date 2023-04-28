package com.example.ktsreddit.common.compose_theme

import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val Purple200 = Color(0xFFBB86FC)
val Purple500 = Color(0xFF6200EE)
val Purple700 = Color(0xFF3700B3)
val Teal200 = Color(0xFF03DAC5)


val LightColorPalette = MyColors(
    material = lightColors(
        primary = Purple500,
        primaryVariant = Purple700,
        secondary = Teal200
    ),
    cardMpBorder = Color(0xFF303F9F),

    )


val DarkColorPalette = MyColors(
    material = darkColors(
        primary = Purple200,
        primaryVariant = Purple700,
        secondary = Teal200
    ),
    cardMpBorder = Color(0xFF303F9F),

    )