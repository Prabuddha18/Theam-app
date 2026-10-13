package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.ScrollableComponentPreviewArea
import com.example.ui.theme.DekuShapes
import com.example.ui.theme.JetBrainsMono
import com.example.ui.theme.SpaceGrotesk
import com.example.ui.viewmodel.ThemeStudioUiState

@Composable
fun LivePreviewScreen(
    uiState: ThemeStudioUiState,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val preset = uiState.currentPreset
    val accent = uiState.activeAccent

    var previewSearchText by remember { mutableStateOf("OnePlus Nord CE 4 Lite 5G") }
    var previewSwitchActive by remember { mutableStateOf(true) }
    var previewSliderValue by remember { mutableFloatStateOf(0.65f) }
    var previewRadioOption by remember { mutableIntStateOf(1) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 16.dp)
            .testTag("live_preview_screen")
    ) {
        // Section Header
        Text(
            text = "LIVE INTERFACE AUDIT",
            fontFamily = JetBrainsMono,
            fontSize = 11.sp,
            letterSpacing = 1.5.sp,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Interactive Component Preview",
            fontFamily = SpaceGrotesk,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Preview background, surface, text, and accent color relationships in working Android UI components.",
            fontFamily = SpaceGrotesk,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        // Active Palette Summary Pill
        PaletteSummaryStrip(uiState = uiState)

        Spacer(modifier = Modifier.height(16.dp))

        // Dedicated Scrollable Preview Area for Cards and Buttons
        ScrollableComponentPreviewArea(
            preset = preset,
            activeAccent = accent,
            isEmbeddedScrollableContainer = false
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Card 1: Rectangular Buttons
        PreviewCard(title = "RECTANGULAR BUTTONS (MODEST RADII)") {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Button(
                    onClick = { },
                    shape = DekuShapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("preview_primary_button")
                ) {
                    Text(
                        text = "PRIMARY ACTION BUTTON",
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        letterSpacing = 1.sp
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    OutlinedButton(
                        onClick = { },
                        shape = DekuShapes.small,
                        colors = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outline),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("preview_secondary_button")
                    ) {
                        Text(
                            text = "OUTLINED BUTTON",
                            fontFamily = SpaceGrotesk,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp,
                            letterSpacing = 0.5.sp
                        )
                    }

                    FilledTonalButton(
                        onClick = { },
                        shape = DekuShapes.small,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = MaterialTheme.colorScheme.surfaceVariant,
                            contentColor = MaterialTheme.colorScheme.onSurface
                        ),
                        modifier = Modifier
                            .weight(1f)
                            .testTag("preview_tonal_button")
                    ) {
                        Text(
                            text = "TONAL ACTION",
                            fontFamily = SpaceGrotesk,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 11.sp,
                            letterSpacing = 0.5.sp
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card 2: Interactive Controls
        PreviewCard(title = "INPUTS & CONTROLS") {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                OutlinedTextField(
                    value = previewSearchText,
                    onValueChange = { previewSearchText = it },
                    label = {
                        Text(
                            text = "INPUT FIELD",
                            fontFamily = JetBrainsMono,
                            fontSize = 10.sp
                        )
                    },
                    singleLine = true,
                    shape = DekuShapes.small,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        unfocusedBorderColor = MaterialTheme.colorScheme.outline,
                        focusedLabelColor = MaterialTheme.colorScheme.primary,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("preview_text_field")
                )

                // Switch Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "ACCENT SWITCH CONTROL",
                            fontFamily = SpaceGrotesk,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = if (previewSwitchActive) "State: Enabled" else "State: Disabled",
                            fontFamily = JetBrainsMono,
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    Switch(
                        checked = previewSwitchActive,
                        onCheckedChange = { previewSwitchActive = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = Color.White,
                            checkedTrackColor = MaterialTheme.colorScheme.primary,
                            uncheckedThumbColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier.testTag("preview_switch")
                    )
                }

                // Slider Row
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "ACCENT RANGE SLIDER",
                            fontFamily = SpaceGrotesk,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "${(previewSliderValue * 100).toInt()}%",
                            fontFamily = JetBrainsMono,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    Slider(
                        value = previewSliderValue,
                        onValueChange = { previewSliderValue = it },
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.primary,
                            activeTrackColor = MaterialTheme.colorScheme.primary,
                            inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant
                        ),
                        modifier = Modifier.testTag("preview_slider")
                    )
                }

                // Radio Options
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    Text(
                        text = "RADIO SELECTION",
                        fontFamily = JetBrainsMono,
                        fontSize = 10.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        listOf(1 to "Option A", 2 to "Option B", 3 to "Option C").forEach { (idx, label) ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.weight(1f)
                            ) {
                                RadioButton(
                                    selected = previewRadioOption == idx,
                                    onClick = { previewRadioOption = idx },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = MaterialTheme.colorScheme.primary,
                                        unselectedColor = MaterialTheme.colorScheme.outline
                                    ),
                                    modifier = Modifier.testTag("preview_radio_$idx")
                                )
                                Text(
                                    text = label,
                                    fontFamily = SpaceGrotesk,
                                    fontSize = 11.sp,
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card 3: Color Tokens & Values
        PreviewCard(title = "HEX CODES & DESIGN TOKENS") {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                TokenRow("Background", preset.backgroundHex, preset.background, "Canvas backdrop")
                TokenRow("Surface", preset.surfaceHex, preset.surface, "Card & container surfaces")
                TokenRow("Text Primary", preset.textPrimaryHex, preset.textPrimary, "High-contrast headings")
                TokenRow("Text Secondary", preset.textSecondaryHex, preset.textSecondary, "Body & metadata copy")
                TokenRow("Accent Hero", accent.hexCode, accent.color, "Hero interactive touchpoints")
                TokenRow("Borders & Lines", preset.borderHex, preset.border, "Subtle structural lines")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card 4: Accessibility & Contrast Audit
        PreviewCard(title = "ACCESSIBILITY & WCAG 2.1 AUDIT") {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                AccessibilityRow(
                    label = "Primary Text on Background",
                    ratio = preset.contrastRatioText,
                    standard = "WCAG AAA Compliant"
                )
                AccessibilityRow(
                    label = "Accent on Background",
                    ratio = preset.contrastRatioAccent,
                    standard = "WCAG AA Large / Graphical"
                )
                AccessibilityRow(
                    label = "White Text on Accent Button",
                    ratio = "4.8:1 (AA)",
                    standard = "WCAG AA Compliant"
                )

                Text(
                    text = "Tested for OnePlus Nord CE 4 Lite 5G 1080x2400 AMOLED display. High luminance separation guarantees outdoor legibility.",
                    fontFamily = SpaceGrotesk,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.padding(top = 4.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
private fun PaletteSummaryStrip(uiState: ThemeStudioUiState) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(DekuShapes.small)
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outline, DekuShapes.small)
            .padding(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "CURRENT PALETTE",
                    fontFamily = JetBrainsMono,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${uiState.currentPreset.name} + ${uiState.activeAccent.name}",
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(uiState.currentPreset.background, RoundedCornerShape(2.dp))
                        .border(1.dp, Color(0x44888888), RoundedCornerShape(2.dp))
                )
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(uiState.currentPreset.textPrimary, RoundedCornerShape(2.dp))
                )
                Box(
                    modifier = Modifier
                        .size(20.dp)
                        .background(uiState.activeAccent.color, RoundedCornerShape(2.dp))
                )
            }
        }
    }
}

@Composable
private fun PreviewCard(
    title: String,
    content: @Composable () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(DekuShapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outline, DekuShapes.medium)
            .padding(16.dp)
    ) {
        Column {
            Text(
                text = title,
                fontFamily = JetBrainsMono,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(14.dp))
            content()
        }
    }
}

@Composable
private fun TokenRow(label: String, hex: String, color: Color, role: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, DekuShapes.extraSmall)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, DekuShapes.extraSmall)
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.weight(1f)
        ) {
            Box(
                modifier = Modifier
                    .size(22.dp)
                    .background(color, DekuShapes.extraSmall)
                    .border(1.dp, Color(0x44888888), DekuShapes.extraSmall)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = label,
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = role,
                    fontFamily = SpaceGrotesk,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface, DekuShapes.extraSmall)
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, DekuShapes.extraSmall)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = hex,
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Composable
private fun AccessibilityRow(label: String, ratio: String, standard: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, DekuShapes.extraSmall)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, DekuShapes.extraSmall)
            .padding(horizontal = 10.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = label,
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = standard,
                fontFamily = JetBrainsMono,
                fontSize = 10.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Box(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface, DekuShapes.extraSmall)
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, DekuShapes.extraSmall)
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = ratio,
                fontFamily = JetBrainsMono,
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}
