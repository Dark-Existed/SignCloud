package com.de.signcloud.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import java.text.CollationElementIterator


val LightColorPalette = SignCloudColors(
    primary = Teal500,
    primaryVariant = Teal600,
    secondary = Purple200,
    secondaryVariant = Color(0xFF018786),
    background = Color.White,
    surface = Color.White,
    error = Color(0xFFB00020),
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.White,

    brand = Teal700,
    uiBackground = Neutral0,
    uiBorder = Neutral4,
    uiFloated = FunctionalGrey,
    textSecondary = Neutral7,
    textHelp = Neutral6,
    iconSecondary = Neutral7,
    iconInteractive = Neutral0,
    iconInteractiveInactive = Neutral1,
    isDark = false,

    gradient3_2 = listOf(Teal600, Teal700),
)

val DarkColorPalette = SignCloudColors(
    primary = Teal200,
    primaryVariant = Teal600,
    secondary = Purple200,
    secondaryVariant = Purple200,
    background = Color(0xFF121212),
    surface = Color(0xFF121212),
    error = Color(0xFFCF6679),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black,

    brand = Teal700,
    uiBackground = Neutral8,
    uiBorder = Neutral3,
    uiFloated = FunctionalDarkGrey,
    textSecondary = Neutral0,
    textHelp = Neutral1,
    iconSecondary = Teal50,
    iconInteractive = Neutral7,
    iconInteractiveInactive = Neutral6,
    isDark = true,

    gradient3_2 = listOf(Rose8, Lavender7, Rose11),)


@Composable
fun SignCloudTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme) DarkColorPalette else LightColorPalette

    ProvideSignCloudColors(colors = colors) {
        MaterialTheme(
            colors = convertToColors(
                darkTheme,
                colors
            ),
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}


object SignCloudTheme {
    val colors: SignCloudColors
        @Composable
        get() = LocalSignCloudColors.current
}


@Stable
class SignCloudColors(
    primary: Color,
    primaryVariant: Color,
    secondary: Color,
    secondaryVariant: Color,
    background: Color,
    surface: Color,
    error: Color,
    onPrimary: Color,
    onSecondary: Color,
    onBackground: Color,
    onSurface: Color,
    onError: Color,

    brand: Color,
    uiBackground: Color,
    uiBorder: Color,
    uiFloated: Color,
    iconPrimary: Color = brand,
    iconSecondary: Color,
    iconInteractive: Color,
    iconInteractiveInactive: Color,
    textSecondary: Color,
    textHelp: Color,
    isDark: Boolean,
    gradient3_2: List<Color>,
) {

    var gradient3_2 by mutableStateOf(gradient3_2)
        private set

    var primary by mutableStateOf(primary)
        private set
    var primaryVariant by mutableStateOf(primaryVariant)
        private set
    var secondary by mutableStateOf(secondary)
        private set
    var secondaryVariant by mutableStateOf(secondaryVariant)
        private set
    var background by mutableStateOf(background)
        private set
    var surface by mutableStateOf(surface)
        private set
    var error by mutableStateOf(error)
        private set
    var onPrimary by mutableStateOf(onPrimary)
        private set
    var onSecondary by mutableStateOf(onSecondary)
        private set
    var onBackground by mutableStateOf(onBackground)
        private set
    var onSurface by mutableStateOf(onSurface)
        private set
    var onError by mutableStateOf(onError)
        private set

    var isDark by mutableStateOf(isDark)
        private set
    var brand by mutableStateOf(brand)
        private set
    var uiBackground by mutableStateOf(uiBackground)
        private set
    var uiBorder by mutableStateOf(uiBorder)
        private set
    var uiFloated by mutableStateOf(uiFloated)
        private set
    var iconPrimary by mutableStateOf(iconPrimary)
        private set
    var iconSecondary by mutableStateOf(iconSecondary)
        private set
    var iconInteractive by mutableStateOf(iconInteractive)
        private set
    var iconInteractiveInactive by mutableStateOf(iconInteractiveInactive)
        private set

    var textSecondary by mutableStateOf(textSecondary)
        private set
    var textHelp by mutableStateOf(textHelp)
        private set



    fun update(other: SignCloudColors) {
        gradient3_2 = other.gradient3_2
        primary = other.primary
        primaryVariant = other.primaryVariant
        secondary = other.secondary
        brand = other.brand
        uiBackground = other.uiBackground
        uiBorder = other.uiBorder
        uiFloated = other.uiFloated
        textSecondary = other.textSecondary
        textHelp = other.textHelp
        iconPrimary = other.iconPrimary
        iconSecondary = other.iconSecondary
        iconInteractive = other.iconInteractive
        iconInteractiveInactive = other.iconInteractiveInactive
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


fun convertToColors(
    darkTheme: Boolean,
    colors: SignCloudColors
) = Colors(
    primary = colors.primary,
    primaryVariant = colors.primaryVariant,
    secondary = colors.secondary,
    secondaryVariant = colors.secondaryVariant,
    background = colors.background,
    surface = colors.surface,
    error = colors.error,
    onPrimary = colors.onPrimary,
    onSecondary = colors.onSecondary,
    onBackground = colors.onBackground,
    onSurface = colors.onSurface,
    onError = colors.onError,
    isLight = !darkTheme
)