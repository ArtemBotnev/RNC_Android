package com.artembotnev.roman_numerals_converter.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColors = lightColors(
    primary = colorPrimary,
    secondary = colorAccent,
    error = colorError,
    surface = colorPrimary,
)

private val DarkColors = darkColors(
    primary = colorPrimaryDark,
    secondary = colorAccentDark,
    error = colorError,
    surface = colorPrimaryDark
)

@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val systemUiController = rememberSystemUiController()
    if (darkTheme){
        systemUiController.setSystemBarsColor(color = colorSecondaryDark)
    } else {
        systemUiController.setSystemBarsColor(color = colorSecondary)
    }

    MaterialTheme(
        colors = if (darkTheme) DarkColors else LightColors,
        content = content
    )
}
