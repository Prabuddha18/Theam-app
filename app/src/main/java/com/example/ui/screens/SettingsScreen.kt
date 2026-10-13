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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
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
import com.example.ui.theme.DekuShapes
import com.example.ui.theme.JetBrainsMono
import com.example.ui.theme.SpaceGrotesk
import com.example.ui.viewmodel.StudioTab
import com.example.ui.viewmodel.ThemeStudioUiState

@Composable
fun SettingsScreen(
    uiState: ThemeStudioUiState,
    onNavigateTab: (StudioTab) -> Unit,
    onResetDefaults: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()
    var showResetDialog by remember { mutableStateOf(false) }
    val accent = uiState.activeAccent

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 14.dp)
            .testTag("settings_screen")
    ) {
        // Section Header
        Text(
            text = "STUDIO SETTINGS",
            fontFamily = JetBrainsMono,
            fontSize = 11.sp,
            letterSpacing = 1.5.sp,
            fontWeight = FontWeight.Medium,
            color = accent.color
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Settings & Information",
            fontFamily = SpaceGrotesk,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Application architecture, permission declarations, legal policies, and technical specs.",
            fontFamily = SpaceGrotesk,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        // Card 1: App Specifications
        SettingsCard(title = "APPLICATION METRICS", accentColor = accent.color) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SpecRow("Application Name", "DEKU Theme Studio")
                SpecRow("Build Version", "v1.0.0 Stable")
                SpecRow("Architecture", "Single Activity // Jetpack Compose // M3")
                SpecRow("Active Theme", uiState.currentPreset.name)
                SpecRow("Active Accent", accent.name + " (${accent.hexCode})")
                SpecRow("Data Persistence", "Local Android SharedPreferences (Offline)")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card 2: Target Hardware Profile
        SettingsCard(title = "DEVICE & SYSTEM ENVIRONMENT", accentColor = accent.color) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SpecRow("Target Device", "OnePlus Nord CE 4 Lite 5G")
                SpecRow("Display", "6.67\" 120Hz AMOLED (1080x2400 20:9)")
                SpecRow("Platform OS", "OxygenOS 14 (Android 14+)")
                SpecRow("Root Access", "Not Required / Not Used")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card 3: OnePlus Nord CE 4 Lite Sideloading & Installation Guide
        SettingsCard(title = "ONEPLUS NORD CE 4 LITE // INSTALLATION & SIDELOADING GUIDE", accentColor = accent.color) {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                Text(
                    text = "Follow these 5 simple steps to install the compiled DEKU APK directly onto your OnePlus Nord CE 4 Lite 5G without needing root privileges:",
                    fontFamily = SpaceGrotesk,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                InstallStepItem(
                    stepNumber = "01",
                    title = "Download APK File",
                    description = "In Google AI Studio Build, tap the Project/Export menu at the top-right and select 'Download APK' to save the compiled package (app-debug.apk).",
                    accentColor = accent.color
                )

                InstallStepItem(
                    stepNumber = "02",
                    title = "Transfer to OnePlus Phone",
                    description = "If downloaded on PC/Browser, transfer the APK to your OnePlus Nord CE 4 Lite via USB-C cable, Google Drive, or send directly via browser download on the device.",
                    accentColor = accent.color
                )

                InstallStepItem(
                    stepNumber = "03",
                    title = "Locate in OxygenOS Files",
                    description = "Open the built-in 'My Files' (or 'Files by Google') app on your OnePlus phone, navigate to 'Downloads', and tap the downloaded DEKU APK file.",
                    accentColor = accent.color
                )

                InstallStepItem(
                    stepNumber = "04",
                    title = "Allow Unknown Source (OxygenOS)",
                    description = "When prompted by OxygenOS security dialog ('Your phone is not allowed to install apps from this source'), tap 'Settings' and toggle ON 'Allow apps from this source' for the file manager.",
                    accentColor = accent.color
                )

                InstallStepItem(
                    stepNumber = "05",
                    title = "Tap Install & Launch",
                    description = "Press Back and tap 'Install'. Android Package Installer will install DEKU Theme Studio. Tap 'Open' to launch the app natively.",
                    accentColor = accent.color
                )

                // OxygenOS tips box
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(DekuShapes.extraSmall)
                        .background(MaterialTheme.colorScheme.surfaceVariant)
                        .border(1.dp, MaterialTheme.colorScheme.outlineVariant, DekuShapes.extraSmall)
                        .padding(10.dp)
                ) {
                    Column {
                        Text(
                            text = "OXYGENOS 14 PERSONALIZATION TIP",
                            fontFamily = JetBrainsMono,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = accent.color
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "To match your OnePlus system accent with DEKU: Go to OxygenOS Settings > Wallpapers & style > Colors, and pick the custom burnt red or dark monochrome palette. Long-press your home screen to add DEKU widgets.",
                            fontFamily = SpaceGrotesk,
                            fontSize = 11.sp,
                            lineHeight = 15.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card 3: Permissions & Privacy
        SettingsCard(title = "PERMISSIONS & PRIVACY MANIFESTO", accentColor = accent.color) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                SpecRow("Network Access", "Zero (100% Offline)")
                SpecRow("Telemetry / Tracking", "None (No analytics, ads, or trackers)")
                SpecRow("Account / Login", "None (Completely anonymous and local)")
                SpecRow("File Access", "Zero broad storage permissions")
                SpecRow("Photo Picker", "Android Photo Picker (Zero permission required)")
                SpecRow("Wallpaper Setting", "android.permission.SET_WALLPAPER (Normal)")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card 4: Legal Navigation Links
        SettingsCard(title = "LEGAL POLICIES & TERMS", accentColor = accent.color) {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                LegalLinkRow(
                    title = "Privacy Policy",
                    subtitle = "Complete disclosure of local data handling",
                    accentColor = accent.color,
                    onClick = { onNavigateTab(StudioTab.PRIVACY_POLICY) }
                )

                LegalLinkRow(
                    title = "Terms & Conditions",
                    subtitle = "Software usage terms and hardware limitations",
                    accentColor = accent.color,
                    onClick = { onNavigateTab(StudioTab.TERMS_CONDITIONS) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Card 5: About Section
        SettingsCard(title = "ABOUT DEKU", accentColor = accent.color) {
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                Text(
                    text = "DEKU Theme Studio is an independent Android customization studio. It bridges the clean dot-matrix aesthetic associated with Nothing OS and the balanced information architecture associated with Pixel Android.",
                    fontFamily = SpaceGrotesk,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "DEKU is independent and not affiliated with, endorsed by, or sponsored by Nothing Technology Limited, Google LLC, or OnePlus / Guangdong Oppo Mobile Telecommunications.",
                    fontFamily = SpaceGrotesk,
                    fontSize = 10.sp,
                    lineHeight = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Reset to Defaults Button
        OutlinedButton(
            onClick = { showResetDialog = true },
            shape = DekuShapes.small,
            colors = ButtonDefaults.outlinedButtonColors(
                contentColor = accent.color
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("reset_defaults_button")
        ) {
            Text(
                text = "RESTORE DEFAULT DEKU PREFERENCES",
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                letterSpacing = 0.5.sp
            )
        }

        Spacer(modifier = Modifier.height(32.dp))
    }

    if (showResetDialog) {
        AlertDialog(
            onDismissRequest = { showResetDialog = false },
            title = {
                Text(
                    text = "Reset Preferences?",
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Text(
                    text = "This will reset active theme to Dark Mono, default DEKU Red accent, and bundled Monolith Matrix wallpaper.",
                    fontFamily = SpaceGrotesk,
                    fontSize = 13.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onResetDefaults()
                        showResetDialog = false
                    },
                    shape = DekuShapes.small,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = accent.color,
                        contentColor = Color.White
                    ),
                    modifier = Modifier.testTag("confirm_reset_btn")
                ) {
                    Text(
                        text = "RESET",
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp
                    )
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showResetDialog = false }
                ) {
                    Text(
                        text = "CANCEL",
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.Medium,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = DekuShapes.medium
        )
    }
}

@Composable
private fun SettingsCard(
    title: String,
    accentColor: Color,
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
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 1.sp,
                color = accentColor
            )
            Spacer(modifier = Modifier.height(12.dp))
            content()
        }
    }
}

@Composable
private fun SpecRow(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surfaceVariant, DekuShapes.extraSmall)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, DekuShapes.extraSmall)
            .padding(horizontal = 10.dp, vertical = 7.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            fontFamily = SpaceGrotesk,
            fontSize = 12.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Text(
            text = value,
            fontFamily = JetBrainsMono,
            fontWeight = FontWeight.Medium,
            fontSize = 11.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun LegalLinkRow(
    title: String,
    subtitle: String,
    accentColor: Color,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(DekuShapes.extraSmall)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, DekuShapes.extraSmall)
            .clickable { onClick() }
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .testTag("link_${title.lowercase().replace(" ", "_")}"),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.SemiBold,
                fontSize = 13.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                text = subtitle,
                fontFamily = SpaceGrotesk,
                fontSize = 11.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
            contentDescription = "Navigate to $title",
            tint = accentColor
        )
    }
}

@Composable
private fun InstallStepItem(
    stepNumber: String,
    title: String,
    description: String,
    accentColor: Color
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(DekuShapes.extraSmall)
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, DekuShapes.extraSmall)
            .padding(10.dp),
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.Top
    ) {
        Box(
            modifier = Modifier
                .background(accentColor.copy(alpha = 0.15f), DekuShapes.extraSmall)
                .border(1.dp, accentColor.copy(alpha = 0.5f), DekuShapes.extraSmall)
                .padding(horizontal = 6.dp, vertical = 3.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = stepNumber,
                fontFamily = JetBrainsMono,
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = accentColor
            )
        }

        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = title,
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = MaterialTheme.colorScheme.onSurface
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = description,
                fontFamily = SpaceGrotesk,
                fontSize = 11.sp,
                lineHeight = 15.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
