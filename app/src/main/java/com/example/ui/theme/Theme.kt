package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = AccentCyan,
    onPrimary = Color(0xFF001F2F),
    primaryContainer = AccentCyanContainer,
    onPrimaryContainer = Color(0xFFE0F2FE),
    secondary = AccentIndigo,
    onSecondary = Color(0xFF0C1033),
    secondaryContainer = Color(0xFF263380),
    onSecondaryContainer = Color(0xFFEEF2FF),
    tertiary = AccentAmber,
    onTertiary = Color(0xFF2E1A00),
    tertiaryContainer = Color(0xFF4D3300),
    onTertiaryContainer = Color(0xFFFEF3C7),
    background = ObsidianDarkBg,
    onBackground = TextPrimaryDark,
    surface = SlateDarkSurface,
    onSurface = TextPrimaryDark,
    surfaceVariant = SlateDarkSurfaceElevated,
    onSurfaceVariant = TextSecondaryDark,
    outline = SlateDarkBorder,
    outlineVariant = Color(0xFF475569),
    inverseSurface = Color(0xFFF1F5F9),
    inverseOnSurface = Color(0xFF0F172A)
)

private val LightColorScheme = lightColorScheme(
    primary = PrimaryLightBlue,
    onPrimary = Color.White,
    primaryContainer = PrimaryLightBlueContainer,
    onPrimaryContainer = Color(0xFF001F2E),
    secondary = IndigoLight,
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFE0E0FF),
    onSecondaryContainer = Color(0xFF0C1060),
    tertiary = AmberLight,
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFFFFDDB3),
    onTertiaryContainer = Color(0xFF2C1600),
    background = PaperLightBg,
    onBackground = TextPrimaryLight,
    surface = CardLightSurface,
    onSurface = TextPrimaryLight,
    surfaceVariant = CardLightSurfaceElevated,
    onSurfaceVariant = TextSecondaryLight,
    outline = SlateLightBorder,
    outlineVariant = Color(0xFF94A3B8),
    inverseSurface = Color(0xFF1E293B),
    inverseOnSurface = Color(0xFFF8FAFC)
)

@Composable
fun MyApplicationTheme(
    themeMode: ThemeMode = ThemeMode.LIGHT,
    content: @Composable () -> Unit
) {
    val colorScheme = LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography
    ) {
        // Ensure LocalContentColor explicitly defaults to onSurface across the entire app hierarchy
        CompositionLocalProvider(
            LocalContentColor provides colorScheme.onSurface,
            content = content
        )
    }
}
