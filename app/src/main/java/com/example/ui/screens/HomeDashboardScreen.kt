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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Wallpaper
import androidx.compose.material.icons.filled.Widgets
import androidx.compose.material.icons.filled.Apps
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DotMatrixClock
import com.example.ui.theme.DekuShapes
import com.example.ui.theme.JetBrainsMono
import com.example.ui.theme.SpaceGrotesk
import com.example.ui.viewmodel.StudioTab
import com.example.ui.viewmodel.ThemeStudioUiState
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun HomeDashboardScreen(
    uiState: ThemeStudioUiState,
    onNavigateTab: (StudioTab) -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    val preset = uiState.currentPreset
    val accent = uiState.activeAccent

    val formattedDate = remember {
        val sdf = SimpleDateFormat("EEEE, dd MMMM", Locale.getDefault())
        sdf.format(Date()).uppercase()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 14.dp)
            .testTag("home_dashboard_screen")
    ) {
        // Top Header Row with DEKU branding and settings shortcut
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // Red accent focal dot
                Box(
                    modifier = Modifier
                        .size(8.dp)
                        .background(accent.color, CircleShape)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "DEKU THEME STUDIO",
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.Bold,
                    fontSize = 15.sp,
                    letterSpacing = 1.2.sp,
                    color = MaterialTheme.colorScheme.onBackground
                )
            }

            IconButton(
                onClick = { onNavigateTab(StudioTab.SETTINGS) },
                modifier = Modifier.testTag("dashboard_settings_btn")
            ) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "Open Settings",
                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Large Dot-Matrix Digital Clock & Real Date Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(DekuShapes.medium)
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, MaterialTheme.colorScheme.outline, DekuShapes.medium)
                .padding(20.dp)
                .testTag("dashboard_clock_card"),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = formattedDate,
                    fontFamily = JetBrainsMono,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium,
                    letterSpacing = 1.5.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(14.dp))

                // Canvas-rendered Dot Matrix Clock using real device time
                DotMatrixClock(
                    dotSize = 5.5.dp,
                    dotSpacing = 3.dp,
                    activeColor = MaterialTheme.colorScheme.onSurface,
                    inactiveColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)
                )

                Spacer(modifier = Modifier.height(14.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .background(accent.color, CircleShape)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "LIVE DEVICE TIME // 1080x2400 AMOLED SYNC",
                        fontFamily = JetBrainsMono,
                        fontSize = 9.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Active Theme Preview Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(DekuShapes.medium)
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, MaterialTheme.colorScheme.outline, DekuShapes.medium)
                .clickable { onNavigateTab(StudioTab.THEMES) }
                .padding(16.dp)
                .testTag("dashboard_active_theme_card")
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "ACTIVE PROFILE",
                            fontFamily = JetBrainsMono,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = accent.color
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Box(
                            modifier = Modifier
                                .background(accent.color.copy(alpha = 0.15f), DekuShapes.extraSmall)
                                .padding(horizontal = 6.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "SAVED LOCALLY",
                                fontFamily = JetBrainsMono,
                                fontSize = 8.sp,
                                fontWeight = FontWeight.Bold,
                                color = accent.color
                            )
                        }
                    }

                    Text(
                        text = "CHANGE >",
                        fontFamily = JetBrainsMono,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = preset.name.uppercase(),
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Text(
                    text = preset.tagline,
                    fontFamily = SpaceGrotesk,
                    fontSize = 12.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(12.dp))

                // Color Swatches
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    MiniPaletteChip("BG", preset.background, preset.backgroundHex, Modifier.weight(1f))
                    MiniPaletteChip("CARD", preset.surface, preset.surfaceHex, Modifier.weight(1f))
                    MiniPaletteChip("TEXT", preset.textPrimary, preset.textPrimaryHex, Modifier.weight(1f))
                    MiniPaletteChip("ACCENT", accent.color, accent.hexCode, Modifier.weight(1f))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Navigation Grid
        Text(
            text = "STUDIO SECTIONS",
            fontFamily = JetBrainsMono,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StudioNavCard(
                title = "Themes",
                subtitle = "4 Curated Palettes",
                icon = Icons.Default.Palette,
                accentColor = accent.color,
                onClick = { onNavigateTab(StudioTab.THEMES) },
                modifier = Modifier.weight(1f)
            )

            StudioNavCard(
                title = "Wallpapers",
                subtitle = "Bundled & Photo Picker",
                icon = Icons.Default.Wallpaper,
                accentColor = accent.color,
                onClick = { onNavigateTab(StudioTab.WALLPAPERS) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            StudioNavCard(
                title = "Icon Studio",
                subtitle = "Monochrome Artwork",
                icon = Icons.Default.Apps,
                accentColor = accent.color,
                onClick = { onNavigateTab(StudioTab.ICONS) },
                modifier = Modifier.weight(1f)
            )

            StudioNavCard(
                title = "Widgets",
                subtitle = "Clock & Notes Previews",
                icon = Icons.Default.Widgets,
                accentColor = accent.color,
                onClick = { onNavigateTab(StudioTab.WIDGETS) },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // System Transparency Note
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(DekuShapes.small)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, DekuShapes.small)
                .padding(12.dp)
        ) {
            Column {
                Text(
                    text = "TARGET HARDWARE & TRANSPARENCY",
                    fontFamily = JetBrainsMono,
                    fontSize = 9.sp,
                    fontWeight = FontWeight.Bold,
                    color = accent.color
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Engineered for OnePlus Nord CE 4 Lite 5G (OxygenOS, non-root). Wallpapers, color tokens, and widgets work natively without root access.",
                    fontFamily = SpaceGrotesk,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Install on OnePlus Guide Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(DekuShapes.medium)
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, accent.color.copy(alpha = 0.5f), DekuShapes.medium)
                .clickable { onNavigateTab(StudioTab.SETTINGS) }
                .padding(14.dp)
                .testTag("install_on_device_banner")
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .background(accent.color, CircleShape)
                                .size(6.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "READY FOR INSTALLATION",
                            fontFamily = JetBrainsMono,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = accent.color
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "Install on OnePlus Nord CE 4 Lite 5G",
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.Bold,
                        fontSize = 13.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Step-by-step sideloading walkthrough & OxygenOS setup guide.",
                        fontFamily = SpaceGrotesk,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Box(
                    modifier = Modifier
                        .background(accent.color, DekuShapes.extraSmall)
                        .padding(horizontal = 10.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = "VIEW GUIDE",
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        color = Color.White
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun StudioNavCard(
    title: String,
    subtitle: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(DekuShapes.medium)
            .background(MaterialTheme.colorScheme.surface)
            .border(1.dp, MaterialTheme.colorScheme.outline, DekuShapes.medium)
            .clickable { onClick() }
            .padding(14.dp)
            .testTag("nav_card_${title.lowercase()}")
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Icon(
                    imageVector = icon,
                    contentDescription = title,
                    tint = accentColor,
                    modifier = Modifier.size(22.dp)
                )

                Box(
                    modifier = Modifier
                        .size(4.dp)
                        .background(accentColor, CircleShape)
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = title,
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = subtitle,
                fontFamily = SpaceGrotesk,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun MiniPaletteChip(
    label: String,
    color: Color,
    hex: String,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .background(MaterialTheme.colorScheme.background, DekuShapes.extraSmall)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, DekuShapes.extraSmall)
            .padding(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .size(16.dp)
                .background(color, DekuShapes.extraSmall)
                .border(0.5.dp, Color(0x33888888), DekuShapes.extraSmall)
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = label,
            fontFamily = JetBrainsMono,
            fontSize = 8.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Text(
            text = hex,
            fontFamily = JetBrainsMono,
            fontSize = 7.5.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}
