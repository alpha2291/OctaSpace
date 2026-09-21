package com.toletspot.houseforrent.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Gold80,
    secondary = WarmGrey80,
    tertiary = Bronze80,
    background = newBlack,
    surface = Color(0xFF1D1D1D),
    onPrimary = newBlack,
    onSecondary = newBlack,
    onTertiary = newBlack,
    onBackground = newWhite,
    onSurface = newWhite
)

private val LightColorScheme = lightColorScheme(
    primary = Gold40,
    secondary = WarmGrey40,
    tertiary = Bronze40,
    background = newWhite,
    surface = newWhite,
    onPrimary = newBlack,
    onSecondary = newBlack,
    onTertiary = newBlack,
    onBackground = newBlack,
    onSurface = newBlack
)

@Composable
fun LandSalesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+ (disabled to keep brand colors)
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicLightColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}