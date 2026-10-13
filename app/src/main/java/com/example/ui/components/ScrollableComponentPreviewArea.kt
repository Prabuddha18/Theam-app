package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
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
import com.example.data.model.AccentOption
import com.example.data.model.ThemePreset
import com.example.ui.theme.DekuShapes
import com.example.ui.theme.JetBrainsMono
import com.example.ui.theme.SpaceGrotesk

/**
 * A dedicated scrollable preview area that demonstrates how the selected theme's
 * primary, background, and accent colors look when applied to standard UI components
 * such as cards, buttons, inputs, indicators, and controls.
 */
@Composable
fun ScrollableComponentPreviewArea(
    preset: ThemePreset,
    activeAccent: AccentOption,
    modifier: Modifier = Modifier,
    isEmbeddedScrollableContainer: Boolean = true
) {
    // Local interactive states for realistic preview
    var sampleChecked by remember { mutableStateOf(true) }
    var sampleRadioOption by remember { mutableIntStateOf(1) }
    var sampleSwitchOn by remember { mutableStateOf(true) }
    var sampleSliderValue by remember { mutableFloatStateOf(0.72f) }
    var sampleSegmentIndex by remember { mutableIntStateOf(0) }
    var sampleInputText by remember { mutableStateOf("DEKU Studio Preset") }

    val primaryColor = activeAccent.color
    val backgroundColor = preset.background
    val surfaceColor = preset.surface
    val surfaceVariantColor = preset.surfaceVariant
    val borderColor = preset.border
    val textPrimary = preset.textPrimary
    val textSecondary = preset.textSecondary

    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(DekuShapes.medium)
            .background(surfaceColor)
            .border(1.dp, borderColor, DekuShapes.medium)
            .padding(14.dp)
            .testTag("scrollable_component_preview_area")
    ) {
        // Section Header with Color Role Badges
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = "COMPONENT CANVAS PREVIEW",
                    fontFamily = JetBrainsMono,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 1.sp,
                    color = primaryColor
                )
                Text(
                    text = "${preset.name} Theme in Action",
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    color = textPrimary
                )
            }

            Box(
                modifier = Modifier
                    .background(surfaceVariantColor, DekuShapes.extraSmall)
                    .border(1.dp, borderColor, DekuShapes.extraSmall)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "SCROLLABLE VIEWPORT",
                    fontFamily = JetBrainsMono,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Medium,
                    color = textSecondary
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Color Role Reference Pills
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            ColorRolePill(
                role = "BG",
                hex = preset.backgroundHex,
                color = backgroundColor,
                textColor = textPrimary,
                modifier = Modifier.weight(1f)
            )
            ColorRolePill(
                role = "SURFACE",
                hex = preset.surfaceHex,
                color = surfaceColor,
                textColor = textPrimary,
                modifier = Modifier.weight(1f)
            )
            ColorRolePill(
                role = "PRIMARY",
                hex = activeAccent.hexCode,
                color = primaryColor,
                textColor = textPrimary,
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        // The Scrollable Viewport simulated with theme background
        val scrollState = rememberScrollState()
        val viewportModifier = if (isEmbeddedScrollableContainer) {
            Modifier
                .fillMaxWidth()
                .heightIn(max = 480.dp)
                .clip(DekuShapes.small)
                .background(backgroundColor)
                .border(1.dp, borderColor, DekuShapes.small)
                .verticalScroll(scrollState)
                .padding(14.dp)
        } else {
            Modifier
                .fillMaxWidth()
                .clip(DekuShapes.small)
                .background(backgroundColor)
                .border(1.dp, borderColor, DekuShapes.small)
                .padding(14.dp)
        }

        Column(
            modifier = viewportModifier.testTag("preview_viewport_scrollable"),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            // 1. HERO CARD COMPONENT
            OutlinedCard(
                colors = CardDefaults.outlinedCardColors(
                    containerColor = surfaceColor
                ),
                border = CardDefaults.outlinedCardBorder().copy(
                    width = 1.dp,
                    brush = androidx.compose.ui.graphics.SolidColor(borderColor)
                ),
                shape = DekuShapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("preview_hero_card")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "HERO COMPONENT CARD",
                            fontFamily = JetBrainsMono,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryColor
                        )

                        Box(
                            modifier = Modifier
                                .background(primaryColor.copy(alpha = 0.16f), DekuShapes.extraSmall)
                                .border(1.dp, primaryColor.copy(alpha = 0.35f), DekuShapes.extraSmall)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "ACCENT BADGE",
                                fontFamily = JetBrainsMono,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "Standard Card with Primary Accent Integration",
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = textPrimary
                    )

                    Text(
                        text = "Cards draw against the canvas background (${preset.backgroundHex}) using elevated surface tokens (${preset.surfaceHex}) and crisp 1dp structural borders.",
                        fontFamily = SpaceGrotesk,
                        fontSize = 12.sp,
                        lineHeight = 16.sp,
                        color = textSecondary,
                        modifier = Modifier.padding(top = 4.dp, bottom = 12.dp)
                    )

                    // Card Action Buttons (Rectangular with modest radius)
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Button(
                            onClick = { /* Interactive preview */ },
                            shape = DekuShapes.small,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = primaryColor,
                                contentColor = Color.White
                            ),
                            modifier = Modifier
                                .weight(1.3f)
                                .testTag("card_primary_button")
                        ) {
                            Text(
                                text = "PRIMARY BUTTON",
                                fontFamily = SpaceGrotesk,
                                fontWeight = FontWeight.Bold,
                                fontSize = 11.sp,
                                letterSpacing = 0.5.sp
                            )
                        }

                        OutlinedButton(
                            onClick = { /* Interactive preview */ },
                            shape = DekuShapes.small,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = textPrimary
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("card_secondary_button")
                        ) {
                            Text(
                                text = "OUTLINED",
                                fontFamily = SpaceGrotesk,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            // 2. METRIC / STATISTIC CARDS (Multi-Column Grid)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                MetricCard(
                    title = "AMOLED PURITY",
                    value = if (preset.isDark) "99.8%" else "100%",
                    subtitle = if (preset.isDark) "Zero backlight emit" else "Warm ink reflectance",
                    accentColor = primaryColor,
                    surfaceColor = surfaceColor,
                    borderColor = borderColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary,
                    modifier = Modifier.weight(1f)
                )

                MetricCard(
                    title = "CONTRAST RATIO",
                    value = preset.contrastRatioText.split(" ").firstOrNull() ?: "16:1",
                    subtitle = "WCAG AAA compliant",
                    accentColor = primaryColor,
                    surfaceColor = surfaceColor,
                    borderColor = borderColor,
                    textPrimary = textPrimary,
                    textSecondary = textSecondary,
                    modifier = Modifier.weight(1f)
                )
            }

            // 3. INTERACTIVE BUTTON SUITE (Rectangular with modest corner radii)
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = surfaceColor
                ),
                border = CardDefaults.outlinedCardBorder().copy(
                    width = 1.dp,
                    brush = androidx.compose.ui.graphics.SolidColor(borderColor)
                ),
                shape = DekuShapes.small,
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("preview_button_suite_card")
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "RECTANGULAR BUTTON VARIANTS",
                        fontFamily = JetBrainsMono,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )
                    Text(
                        text = "Modest 4dp corner radii, bold architectural lettering",
                        fontFamily = SpaceGrotesk,
                        fontSize = 11.sp,
                        color = textSecondary,
                        modifier = Modifier.padding(top = 2.dp, bottom = 10.dp)
                    )

                    // Button Variant A: Primary Filled
                    Button(
                        onClick = { /* Action */ },
                        shape = DekuShapes.small,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = primaryColor,
                            contentColor = Color.White
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("suite_btn_filled")
                    ) {
                        Text(
                            text = "PRIMARY FILLED BUTTON (ACCENT)",
                            fontFamily = SpaceGrotesk,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Button Variant B: Filled Tonal
                    FilledTonalButton(
                        onClick = { /* Action */ },
                        shape = DekuShapes.small,
                        colors = ButtonDefaults.filledTonalButtonColors(
                            containerColor = surfaceVariantColor,
                            contentColor = primaryColor
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("suite_btn_tonal")
                    ) {
                        Text(
                            text = "TONAL SURFACE BUTTON",
                            fontFamily = SpaceGrotesk,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp,
                            letterSpacing = 0.5.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Button Variant C & D: Outlined and Compact
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        OutlinedButton(
                            onClick = { /* Action */ },
                            shape = DekuShapes.small,
                            colors = ButtonDefaults.outlinedButtonColors(
                                contentColor = textPrimary
                            ),
                            border = androidx.compose.foundation.BorderStroke(1.dp, borderColor),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("suite_btn_outlined")
                        ) {
                            Text(
                                text = "OUTLINED",
                                fontFamily = SpaceGrotesk,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 11.sp
                            )
                        }

                        Button(
                            onClick = { /* Disabled preview */ },
                            enabled = false,
                            shape = DekuShapes.small,
                            colors = ButtonDefaults.buttonColors(
                                disabledContainerColor = surfaceVariantColor.copy(alpha = 0.5f),
                                disabledContentColor = textSecondary.copy(alpha = 0.4f)
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("suite_btn_disabled")
                        ) {
                            Text(
                                text = "DISABLED",
                                fontFamily = SpaceGrotesk,
                                fontWeight = FontWeight.Normal,
                                fontSize = 11.sp
                            )
                        }
                    }
                }
            }

            // 4. SEGMENTED RECTANGULAR CONTROLS
            OutlinedCard(
                colors = CardDefaults.outlinedCardColors(
                    containerColor = surfaceColor
                ),
                border = CardDefaults.outlinedCardBorder().copy(
                    width = 1.dp,
                    brush = androidx.compose.ui.graphics.SolidColor(borderColor)
                ),
                shape = DekuShapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "SEGMENTED TAB SWITCHER",
                        fontFamily = JetBrainsMono,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(surfaceVariantColor, DekuShapes.small)
                            .border(1.dp, borderColor, DekuShapes.small)
                            .padding(3.dp),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        listOf("Overview", "Components", "Tokens").forEachIndexed { index, title ->
                            val isSelected = sampleSegmentIndex == index

                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .clip(DekuShapes.extraSmall)
                                    .background(if (isSelected) primaryColor else Color.Transparent)
                                    .border(
                                        width = if (isSelected) 1.dp else 0.dp,
                                        color = if (isSelected) primaryColor else Color.Transparent,
                                        shape = DekuShapes.extraSmall
                                    )
                                    .clickable { sampleSegmentIndex = index }
                                    .padding(vertical = 7.dp),
                                contentAlignment = Alignment.Center
                            ) {
                                Text(
                                    text = title,
                                    fontFamily = SpaceGrotesk,
                                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                                    fontSize = 11.sp,
                                    color = if (isSelected) Color.White else textSecondary
                                )
                            }
                        }
                    }
                }
            }

            // 5. LIST ITEM CARDS WITH ACCENT SELECTION
            Card(
                colors = CardDefaults.cardColors(
                    containerColor = surfaceColor
                ),
                border = CardDefaults.outlinedCardBorder().copy(
                    width = 1.dp,
                    brush = androidx.compose.ui.graphics.SolidColor(borderColor)
                ),
                shape = DekuShapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "LIST ITEM & SELECTION CARDS",
                        fontFamily = JetBrainsMono,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    // Row Item 1 with Checkbox
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(surfaceVariantColor, DekuShapes.small)
                            .border(1.dp, borderColor, DekuShapes.small)
                            .clickable { sampleChecked = !sampleChecked }
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Checkbox(
                                checked = sampleChecked,
                                onCheckedChange = { sampleChecked = it },
                                colors = CheckboxDefaults.colors(
                                    checkedColor = primaryColor,
                                    checkmarkColor = Color.White,
                                    uncheckedColor = borderColor
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = "AMOLED Tone Optimization",
                                    fontFamily = SpaceGrotesk,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp,
                                    color = textPrimary
                                )
                                Text(
                                    text = "Deep black rendering enabled",
                                    fontFamily = SpaceGrotesk,
                                    fontSize = 10.sp,
                                    color = textSecondary
                                )
                            }
                        }

                        Box(
                            modifier = Modifier
                                .background(primaryColor.copy(alpha = 0.15f), DekuShapes.extraSmall)
                                .padding(horizontal = 6.dp, vertical = 3.dp)
                        ) {
                            Text(
                                text = "ACTIVE",
                                fontFamily = JetBrainsMono,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Bold,
                                color = primaryColor
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    // Row Item 2 with Radio
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(surfaceVariantColor, DekuShapes.small)
                            .border(1.dp, borderColor, DekuShapes.small)
                            .clickable { sampleRadioOption = if (sampleRadioOption == 1) 2 else 1 }
                            .padding(horizontal = 10.dp, vertical = 8.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            RadioButton(
                                selected = sampleRadioOption == 1,
                                onClick = { sampleRadioOption = 1 },
                                colors = RadioButtonDefaults.colors(
                                    selectedColor = primaryColor,
                                    unselectedColor = borderColor
                                )
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Column {
                                Text(
                                    text = "High Contrast Text Rendering",
                                    fontFamily = SpaceGrotesk,
                                    fontWeight = FontWeight.SemiBold,
                                    fontSize = 12.sp,
                                    color = textPrimary
                                )
                                Text(
                                    text = "Warm ivory text on dark surfaces",
                                    fontFamily = SpaceGrotesk,
                                    fontSize = 10.sp,
                                    color = textSecondary
                                )
                            }
                        }

                        Text(
                            text = "RADIO",
                            fontFamily = JetBrainsMono,
                            fontSize = 9.sp,
                            color = textSecondary
                        )
                    }
                }
            }

            // 6. FORM INPUTS & ACCENT PROGRESS
            OutlinedCard(
                colors = CardDefaults.outlinedCardColors(
                    containerColor = surfaceColor
                ),
                border = CardDefaults.outlinedCardBorder().copy(
                    width = 1.dp,
                    brush = androidx.compose.ui.graphics.SolidColor(borderColor)
                ),
                shape = DekuShapes.small,
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = "FORM INPUT & ACCENT METRICS",
                        fontFamily = JetBrainsMono,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = primaryColor
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = sampleInputText,
                        onValueChange = { sampleInputText = it },
                        label = {
                            Text(
                                text = "TEXT INPUT FIELD",
                                fontFamily = JetBrainsMono,
                                fontSize = 10.sp
                            )
                        },
                        singleLine = true,
                        shape = DekuShapes.small,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = borderColor,
                            focusedLabelColor = primaryColor,
                            unfocusedLabelColor = textSecondary,
                            focusedTextColor = textPrimary,
                            unfocusedTextColor = textPrimary
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Switch & Slider Controls
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ACCENT SWITCH STATE",
                            fontFamily = SpaceGrotesk,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 12.sp,
                            color = textPrimary
                        )

                        Switch(
                            checked = sampleSwitchOn,
                            onCheckedChange = { sampleSwitchOn = it },
                            colors = SwitchDefaults.colors(
                                checkedThumbColor = Color.White,
                                checkedTrackColor = primaryColor,
                                uncheckedThumbColor = textSecondary,
                                uncheckedTrackColor = surfaceVariantColor
                            )
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "PROGRESS GAUGE",
                            fontFamily = SpaceGrotesk,
                            fontSize = 11.sp,
                            color = textSecondary
                        )
                        Text(
                            text = "${(sampleSliderValue * 100).toInt()}%",
                            fontFamily = JetBrainsMono,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = primaryColor
                        )
                    }

                    Spacer(modifier = Modifier.height(4.dp))

                    LinearProgressIndicator(
                        progress = { sampleSliderValue },
                        color = primaryColor,
                        trackColor = surfaceVariantColor,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(6.dp)
                            .clip(DekuShapes.extraSmall)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Slider(
                        value = sampleSliderValue,
                        onValueChange = { sampleSliderValue = it },
                        colors = SliderDefaults.colors(
                            thumbColor = primaryColor,
                            activeTrackColor = primaryColor,
                            inactiveTrackColor = surfaceVariantColor
                        )
                    )
                }
            }

            // 7. SYSTEM NOTICE BANNER CARD
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(DekuShapes.small)
                    .background(surfaceVariantColor)
                    .border(1.dp, primaryColor.copy(alpha = 0.5f), DekuShapes.small)
                    .padding(12.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(primaryColor, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Column {
                        Text(
                            text = "COLOR HARMONY VERIFIED",
                            fontFamily = JetBrainsMono,
                            fontWeight = FontWeight.Bold,
                            fontSize = 10.sp,
                            color = primaryColor
                        )
                        Text(
                            text = "Background, surface, and burnt orange accents align with OxygenOS 14 guidelines.",
                            fontFamily = SpaceGrotesk,
                            fontSize = 11.sp,
                            color = textPrimary
                        )
                    }
                }
            }
        }

        if (isEmbeddedScrollableContainer) {
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "Scroll inside the canvas to inspect all cards & buttons",
                    fontFamily = SpaceGrotesk,
                    fontSize = 10.sp,
                    color = textSecondary
                )
            }
        }
    }
}

@Composable
private fun ColorRolePill(
    role: String,
    hex: String,
    color: Color,
    textColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(Color(0x18888888), DekuShapes.extraSmall)
            .border(1.dp, Color(0x33888888), DekuShapes.extraSmall)
            .padding(horizontal = 6.dp, vertical = 4.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(10.dp)
                    .background(color, DekuShapes.extraSmall)
                    .border(0.5.dp, Color(0x44FFFFFF), DekuShapes.extraSmall)
            )
            Column {
                Text(
                    text = role,
                    fontFamily = JetBrainsMono,
                    fontWeight = FontWeight.Bold,
                    fontSize = 8.sp,
                    color = textColor
                )
                Text(
                    text = hex,
                    fontFamily = JetBrainsMono,
                    fontSize = 7.5.sp,
                    color = textColor.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun MetricCard(
    title: String,
    value: String,
    subtitle: String,
    accentColor: Color,
    surfaceColor: Color,
    borderColor: Color,
    textPrimary: Color,
    textSecondary: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(DekuShapes.small)
            .background(surfaceColor)
            .border(1.dp, borderColor, DekuShapes.small)
            .padding(10.dp)
    ) {
        Column {
            Text(
                text = title,
                fontFamily = JetBrainsMono,
                fontSize = 9.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = value,
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = textPrimary
            )
            Text(
                text = subtitle,
                fontFamily = SpaceGrotesk,
                fontSize = 10.sp,
                color = textSecondary,
                maxLines = 1
            )
        }
    }
}
