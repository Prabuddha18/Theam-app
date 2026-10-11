package com.example.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.data.model.AccentOption
import com.example.data.model.AvailableAccents
import com.example.data.model.DarkMonoPreset
import com.example.data.model.ThemePreset

// Rectangular shapes with modest corner radii (8dp to 16dp)
val DekuShapes = Shapes(
    extraSmall = RoundedCornerShape(4.dp),
    small = RoundedCornerShape(8.dp),
    medium = RoundedCornerShape(12.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(20.dp)
)

fun createDekuColorScheme(preset: ThemePreset, activeAccent: AccentOption): ColorScheme {
    val accent = activeAccent.color
    return if (preset.isDark) {
        darkColorScheme(
            primary = accent,
            onPrimary = Color.White,
            primaryContainer = accent.copy(alpha = 0.2f),
            onPrimaryContainer = preset.textPrimary,
            secondary = preset.textSecondary,
            onSecondary = preset.background,
            secondaryContainer = preset.surfaceVariant,
            onSecondaryContainer = preset.textPrimary,
            tertiary = preset.textSecondary,
            onTertiary = preset.background,
            background = preset.background,
            onBackground = preset.textPrimary,
            surface = preset.surface,
            onSurface = preset.textPrimary,
            surfaceVariant = preset.surfaceVariant,
            onSurfaceVariant = preset.textSecondary,
            outline = preset.border,
            outlineVariant = preset.border.copy(alpha = 0.6f)
        )
    } else {
        lightColorScheme(
            primary = accent,
            onPrimary = Color.White,
            primaryContainer = accent.copy(alpha = 0.15f),
            onPrimaryContainer = preset.textPrimary,
            secondary = preset.textSecondary,
            onSecondary = Color.White,
            secondaryContainer = preset.surfaceVariant,
            onSecondaryContainer = preset.textPrimary,
            tertiary = preset.textSecondary,
            onTertiary = Color.White,
            background = preset.background,
            onBackground = preset.textPrimary,
            surface = preset.surface,
            onSurface = preset.textPrimary,
            surfaceVariant = preset.surfaceVariant,
            onSurfaceVariant = preset.textSecondary,
            outline = preset.border,
            outlineVariant = preset.border.copy(alpha = 0.6f)
        )
    }
}

@Composable
fun DekuTheme(
    preset: ThemePreset = DarkMonoPreset,
    activeAccent: AccentOption = AvailableAccents[0],
    content: @Composable () -> Unit
) {
    val colorScheme = createDekuColorScheme(preset, activeAccent)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = DekuTypography,
        shapes = DekuShapes,
        content = content
    )
}
