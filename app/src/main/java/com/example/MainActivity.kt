package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.data.repository.ThemePreferencesRepository
import com.example.ui.components.DekuNavigationBar
import com.example.ui.components.DekuTopBar
import com.example.ui.screens.HomeDashboardScreen
import com.example.ui.screens.IconStudioScreen
import com.example.ui.screens.LegalDocumentScreen
import com.example.ui.screens.SettingsScreen
import com.example.ui.screens.StudioThemeSelectorScreen
import com.example.ui.screens.WallpaperStudioScreen
import com.example.ui.screens.WidgetStudioScreen
import com.example.ui.theme.DekuTheme
import com.example.ui.viewmodel.StudioTab
import com.example.ui.viewmodel.ThemeStudioViewModel

class MainActivity : ComponentActivity() {

    private val repository by lazy {
        ThemePreferencesRepository(applicationContext)
    }

    private val viewModel: ThemeStudioViewModel by viewModels {
        ThemeStudioViewModel.Factory(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DekuApp(viewModel = viewModel)
        }
    }
}

val PrivacyPolicySections = listOf(
    "1. Zero Network & Offline Architecture" to
            "DEKU Theme Studio operates strictly on-device. The application does not request the android.permission.INTERNET permission and never communicates with external servers, cloud services, or third-party APIs.",
    "2. Zero Telemetry, Analytics, and Trackers" to
            "The application does not include any third-party analytics libraries, telemetry tools, advertising frameworks, or customer tracking SDKs. No usage metrics or device telemetry are ever logged or transmitted.",
    "3. Local Preferences Storage" to
            "All theme preferences (active theme preset, accent variation, selected wallpaper, and widget configurations) are persisted exclusively in your device's private Android SharedPreferences storage. This data never leaves your device and is erased upon uninstalling the application.",
    "4. Media and Photo Access" to
            "When you choose to browse personal photos for custom wallpapers, DEKU integrates Android's modern System Photo Picker (PickVisualMedia). This grants temporary read access only to the specifically chosen image without requiring broad READ_EXTERNAL_STORAGE or media library permissions.",
    "5. Contact & Publication Details" to
            "DEKU Theme Studio is an independent, non-commercial customization utility for Android devices. [Publisher contact and registration details reserved for final public release distribution]."
)

val TermsAndConditionsSections = listOf(
    "1. Acceptance of Terms" to
            "By installing and using DEKU Theme Studio, you agree to these Terms and Conditions. If you do not agree with any portion of these terms, please discontinue using the software.",
    "2. System Limitations on Non-Root Devices" to
            "DEKU Theme Studio is designed for unmodified, unrooted Android devices including the OnePlus Nord CE 4 Lite 5G. The application does not modify, replace, or claim to alter proprietary OxygenOS lock screen keyguards, status bar battery glyphs, quick setting header tiles, or system-wide font binaries.",
    "3. Wallpaper and Widget Capabilities" to
            "Wallpapers are applied using Android's standard WallpaperManager API. Home screen widgets use standard Android AppWidgetProvider APIs. Appearance on third-party launchers depends on individual launcher widget support.",
    "4. Disclaimer of Affiliation" to
            "DEKU is an independent customization application. All referenced trademarks, device names, and system software designations (including OnePlus, OxygenOS, Nothing OS, and Pixel) belong to their respective trademark holders and are mentioned strictly for compatibility and hardware targeting purposes.",
    "5. Limitation of Liability" to
            "The software is provided 'as is' without warranty of any kind. The developers shall not be liable for any system launcher misconfigurations resulting from third-party modifications."
)

@Composable
fun DekuApp(viewModel: ThemeStudioViewModel) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.snackbarMessage) {
        uiState.snackbarMessage?.let { message ->
            snackbarHostState.showSnackbar(message)
            viewModel.dismissMessage()
        }
    }

    // Predictive back handler
    BackHandler(enabled = uiState.currentTab != StudioTab.HOME) {
        if (uiState.currentTab == StudioTab.PRIVACY_POLICY || uiState.currentTab == StudioTab.TERMS_CONDITIONS) {
            viewModel.setTab(StudioTab.SETTINGS)
        } else {
            viewModel.setTab(StudioTab.HOME)
        }
    }

    DekuTheme(
        preset = uiState.currentPreset,
        activeAccent = uiState.activeAccent
    ) {
        Scaffold(
            topBar = {
                DekuTopBar(currentPreset = uiState.currentPreset)
            },
            bottomBar = {
                // Show bottom bar for primary studio screens
                if (uiState.currentTab != StudioTab.PRIVACY_POLICY && uiState.currentTab != StudioTab.TERMS_CONDITIONS) {
                    DekuNavigationBar(
                        currentTab = uiState.currentTab,
                        onTabSelected = { viewModel.setTab(it) }
                    )
                }
            },
            snackbarHost = {
                SnackbarHost(hostState = snackbarHostState)
            },
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            val contentModifier = Modifier.padding(innerPadding)

            when (uiState.currentTab) {
                StudioTab.HOME -> {
                    HomeDashboardScreen(
                        uiState = uiState,
                        onNavigateTab = { viewModel.setTab(it) },
                        modifier = contentModifier
                    )
                }

                StudioTab.THEMES -> {
                    StudioThemeSelectorScreen(
                        uiState = uiState,
                        onPresetSelected = { viewModel.selectPreset(it) },
                        onAccentSelected = { viewModel.selectAccent(it) },
                        modifier = contentModifier
                    )
                }

                StudioTab.WALLPAPERS -> {
                    WallpaperStudioScreen(
                        uiState = uiState,
                        onSelectWallpaper = { viewModel.selectWallpaper(it) },
                        onCustomPhotoSelected = { viewModel.setCustomWallpaperUri(it) },
                        onApplyWallpaper = { ctx, flags, label ->
                            viewModel.applyWallpaper(ctx, flags, label)
                        },
                        onShareWallpaper = { ctx ->
                            viewModel.shareWallpaper(ctx)
                        },
                        modifier = contentModifier
                    )
                }

                StudioTab.ICONS -> {
                    IconStudioScreen(
                        uiState = uiState,
                        onStyleSelected = { viewModel.setIconStyle(it) },
                        modifier = contentModifier
                    )
                }

                StudioTab.WIDGETS -> {
                    WidgetStudioScreen(
                        uiState = uiState,
                        onWidgetTypeSelected = { viewModel.setWidgetType(it) },
                        onNoteTextChanged = { viewModel.setCustomNoteText(it) },
                        onRequestPinWidget = { ctx ->
                            viewModel.requestPinClockWidget(ctx)
                        },
                        modifier = contentModifier
                    )
                }

                StudioTab.SETTINGS -> {
                    SettingsScreen(
                        uiState = uiState,
                        onNavigateTab = { viewModel.setTab(it) },
                        onResetDefaults = { viewModel.resetDefaults() },
                        modifier = contentModifier
                    )
                }

                StudioTab.PRIVACY_POLICY -> {
                    LegalDocumentScreen(
                        title = "Privacy Policy",
                        documentType = "PRIVACY MANIFESTO",
                        contentParagraphs = PrivacyPolicySections,
                        onBack = { viewModel.setTab(StudioTab.SETTINGS) },
                        uiState = uiState,
                        modifier = contentModifier
                    )
                }

                StudioTab.TERMS_CONDITIONS -> {
                    LegalDocumentScreen(
                        title = "Terms & Conditions",
                        documentType = "TERMS OF SERVICE",
                        contentParagraphs = TermsAndConditionsSections,
                        onBack = { viewModel.setTab(StudioTab.SETTINGS) },
                        uiState = uiState,
                        modifier = contentModifier
                    )
                }
            }
        }
    }
}
