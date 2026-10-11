package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.AllThemes
import com.example.data.model.AvailableAccents
import com.example.data.model.ThemePreset
import com.example.data.model.ThemePresetId
import com.example.ui.components.ScrollableComponentPreviewArea
import com.example.ui.theme.DekuShapes
import com.example.ui.theme.JetBrainsMono
import com.example.ui.theme.SpaceGrotesk
import com.example.ui.viewmodel.ThemeStudioUiState

@Composable
fun StudioThemeSelectorScreen(
    uiState: ThemeStudioUiState,
    onPresetSelected: (ThemePresetId) -> Unit,
    onAccentSelected: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 14.dp)
            .testTag("theme_selector_screen")
    ) {
        // Section Header
        Text(
            text = "THEME SYSTEM",
            fontFamily = JetBrainsMono,
            fontSize = 11.sp,
            letterSpacing = 1.5.sp,
            fontWeight = FontWeight.Medium,
            color = uiState.activeAccent.color
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "DEKU Visual Palette",
            fontFamily = SpaceGrotesk,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Four distinct themes rooted in monochrome dot-matrix minimalism and restrained red highlights.",
            fontFamily = SpaceGrotesk,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        // Scrollable Live Preview Component Area
        ScrollableComponentPreviewArea(
            preset = uiState.currentPreset,
            activeAccent = uiState.activeAccent,
            isEmbeddedScrollableContainer = true
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Section Title: Curated Themes
        Text(
            text = "SELECT THEME PRESET",
            fontFamily = JetBrainsMono,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(10.dp))

        AllThemes.forEach { preset ->
            val isActive = uiState.currentPresetId == preset.id

            ThemeSelectionCard(
                preset = preset,
                isActive = isActive,
                onSelect = { onPresetSelected(preset.id) }
            )

            Spacer(modifier = Modifier.height(12.dp))
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Accent Selection
        Text(
            text = "ACCENT COLOR REFINEMENT",
            fontFamily = JetBrainsMono,
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Primary action points, indicators, and focus states use restrained accent colors.",
            fontFamily = SpaceGrotesk,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AvailableAccents.forEach { accent ->
                val isSelected = accent.id == uiState.activeAccent.id

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(DekuShapes.small)
                        .background(MaterialTheme.colorScheme.surface)
                        .border(
                            width = if (isSelected) 2.dp else 1.dp,
                            color = if (isSelected) accent.color else MaterialTheme.colorScheme.outlineVariant,
                            shape = DekuShapes.small
                        )
                        .clickable { onAccentSelected(accent.id) }
                        .padding(8.dp)
                        .testTag("accent_${accent.id}"),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(20.dp)
                                .background(accent.color, DekuShapes.extraSmall)
                                .border(0.5.dp, MaterialTheme.colorScheme.outlineVariant, DekuShapes.extraSmall)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = accent.name,
                            fontFamily = SpaceGrotesk,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 10.sp,
                            color = if (isSelected) accent.color else MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = accent.hexCode,
                            fontFamily = JetBrainsMono,
                            fontSize = 8.5.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun ThemeSelectionCard(
    preset: ThemePreset,
    isActive: Boolean,
    onSelect: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(DekuShapes.medium)
            .background(preset.surface)
            .border(
                width = if (isActive) 2.dp else 1.dp,
                color = if (isActive) preset.accentDefault else preset.border,
                shape = DekuShapes.medium
            )
            .clickable { onSelect() }
            .padding(16.dp)
            .testTag("theme_card_${preset.name.lowercase().replace(" ", "_")}")
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = preset.name.uppercase(),
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.Bold,
                        fontSize = 17.sp,
                        color = preset.textPrimary
                    )
                    Text(
                        text = preset.tagline,
                        fontFamily = SpaceGrotesk,
                        fontSize = 12.sp,
                        color = preset.textSecondary
                    )
                }

                if (isActive) {
                    Box(
                        modifier = Modifier
                            .background(preset.accentDefault, DekuShapes.extraSmall)
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "ACTIVE",
                            fontFamily = JetBrainsMono,
                            fontWeight = FontWeight.Bold,
                            fontSize = 9.sp,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = preset.description,
                fontFamily = SpaceGrotesk,
                fontSize = 12.sp,
                lineHeight = 16.sp,
                color = preset.textSecondary
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Palette Swatches
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    MiniColorBox(preset.background, "BG", preset.textPrimary)
                    MiniColorBox(preset.surface, "SF", preset.textPrimary)
                    MiniColorBox(preset.textPrimary, "TX", preset.background)
                    MiniColorBox(preset.accentDefault, "AC", Color.White)
                }

                Box(
                    modifier = Modifier
                        .background(preset.surfaceVariant, DekuShapes.extraSmall)
                        .border(1.dp, preset.border, DekuShapes.extraSmall)
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = preset.contrastRatioText,
                        fontFamily = JetBrainsMono,
                        fontSize = 9.sp,
                        color = preset.textSecondary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            if (!isActive) {
                OutlinedButton(
                    onClick = onSelect,
                    shape = DekuShapes.small,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = "APPLY ${preset.name.uppercase()}",
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.Bold,
                        fontSize = 11.sp,
                        letterSpacing = 0.5.sp,
                        color = preset.accentDefault
                    )
                }
            }
        }
    }
}

@Composable
private fun MiniColorBox(color: Color, label: String, textColor: Color) {
    Box(
        modifier = Modifier
            .size(24.dp)
            .background(color, DekuShapes.extraSmall)
            .border(1.dp, Color(0x33888888), DekuShapes.extraSmall),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = label,
            fontFamily = JetBrainsMono,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            color = textColor
        )
    }
}
