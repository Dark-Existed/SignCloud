package com.de.signcloud.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

val DarkColorPalette = darkColors(
    primary = Teal200,
    primaryVariant = Teal700,
    secondary = Purple200
)

val LightColorPalette = lightColors(
    primary = Teal500,
    primaryVariant = Teal700,
    secondary = Purple200
    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)


@Composable
fun SignCloudTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}


@Stable
class SignCloudColors(
    primary: Color,
    primaryVariant: Color,
    secondary: Color,
    isDark: Boolean
) {

    var primary by mutableStateOf(primary)
        private set

    var primaryVariant by mutableStateOf(primaryVariant)
        private set

    var secondary by mutableStateOf(secondary)
        private set

    var isDark by mutableStateOf(isDark)
        private set


    fun update(other: SignCloudColors) {
        primary = other.primary
        primaryVariant = other.primaryVariant
        secondary = other.secondary
        isDark = other.isDark
    }
}


@Composable
fun ProvideSignCloudColors(
    colors: SignCloudColors,
    content: @Composable () -> Unit
) {
    val colorPalette = remember { colors }
    colorPalette.update(colors)
    CompositionLocalProvider(LocalSignCloudColors provides colorPalette, content = content)
}

private val LocalSignCloudColors = staticCompositionLocalOf<SignCloudColors> {
    error("No SignCloudColorPalette provided")
}