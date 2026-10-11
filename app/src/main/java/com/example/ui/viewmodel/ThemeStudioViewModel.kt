package com.example.ui.viewmodel

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.MainActivity
import com.example.data.model.AccentOption
import com.example.data.model.AvailableAccents
import com.example.data.model.CuratedWallpapers
import com.example.data.model.DarkMonoPreset
import com.example.data.model.EmberPreset
import com.example.data.model.GraphitePreset
import com.example.data.model.LightMonoPreset
import com.example.data.model.ThemePreset
import com.example.data.model.ThemePresetId
import com.example.data.model.WallpaperItem
import com.example.data.repository.ThemePreferencesRepository
import com.example.util.WallpaperGenerator
import com.example.widget.DekuClockWidgetProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

enum class StudioTab(val title: String) {
    HOME("Home"),
    THEMES("Themes"),
    WALLPAPERS("Wallpapers"),
    ICONS("Icons"),
    WIDGETS("Widgets"),
    SETTINGS("Settings"),
    PRIVACY_POLICY("Privacy Policy"),
    TERMS_CONDITIONS("Terms & Conditions")
}

enum class IconStyle(val displayName: String) {
    MONOCHROME_DARK("Solid Dark"),
    MONOCHROME_OUTLINE("Clean Outline"),
    RED_ACCENT_DOT("Red Dot Focal")
}

enum class WidgetType(val displayName: String) {
    LARGE_DOT_MATRIX("Large Dot Clock"),
    COMPACT_CLOCK("Compact Clock"),
    DATE_DISPLAY("Date & Calendar"),
    NOTES_THOUGHTS("Notes & Thoughts")
}

data class ThemeStudioUiState(
    val currentPresetId: ThemePresetId = ThemePresetId.DARK_MONO,
    val currentPreset: ThemePreset = DarkMonoPreset,
    val activeAccent: AccentOption = AvailableAccents[0],
    val currentTab: StudioTab = StudioTab.HOME,
    val selectedWallpaperId: String = "deku_monolith",
    val selectedWallpaper: WallpaperItem = CuratedWallpapers[0],
    val customWallpaperUri: String? = null,
    val activeIconStyle: IconStyle = IconStyle.MONOCHROME_DARK,
    val activeWidgetType: WidgetType = WidgetType.LARGE_DOT_MATRIX,
    val customNoteText: String = "Design is intelligence made visible.",
    val snackbarMessage: String? = null
)

private data class InternalUiControls(
    val currentTab: StudioTab = StudioTab.HOME,
    val activeIconStyle: IconStyle = IconStyle.MONOCHROME_DARK,
    val activeWidgetType: WidgetType = WidgetType.LARGE_DOT_MATRIX,
    val customNoteText: String = "Design is intelligence made visible.",
    val snackbarMessage: String? = null
)

class ThemeStudioViewModel(
    private val repository: ThemePreferencesRepository
) : ViewModel() {

    private val _controls = MutableStateFlow(InternalUiControls())

    val uiState: StateFlow<ThemeStudioUiState> = combine(
        repository.preferencesState,
        _controls
    ) { prefs, controls ->
        val preset = when (prefs.themePresetId) {
            ThemePresetId.DARK_MONO -> DarkMonoPreset
            ThemePresetId.LIGHT_MONO -> LightMonoPreset
            ThemePresetId.GRAPHITE -> GraphitePreset
            ThemePresetId.EMBER -> EmberPreset
        }
        val accent = AvailableAccents.find { it.id == prefs.accentId } ?: AvailableAccents[0]
        val wallpaper = CuratedWallpapers.find { it.id == prefs.selectedWallpaperId } ?: CuratedWallpapers[0]

        ThemeStudioUiState(
            currentPresetId = prefs.themePresetId,
            currentPreset = preset,
            activeAccent = accent,
            currentTab = controls.currentTab,
            selectedWallpaperId = prefs.selectedWallpaperId,
            selectedWallpaper = wallpaper,
            customWallpaperUri = prefs.customWallpaperUri,
            activeIconStyle = controls.activeIconStyle,
            activeWidgetType = controls.activeWidgetType,
            customNoteText = controls.customNoteText,
            snackbarMessage = controls.snackbarMessage
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = ThemeStudioUiState()
    )

    fun selectPreset(presetId: ThemePresetId) {
        repository.saveThemePreset(presetId)
        _controls.value = _controls.value.copy(snackbarMessage = "${presetId.displayName} theme activated and saved")
    }

    fun selectAccent(accentId: String) {
        repository.saveAccent(accentId)
        val accentName = AvailableAccents.find { it.id == accentId }?.name ?: "Accent"
        _controls.value = _controls.value.copy(snackbarMessage = "$accentName accent applied")
    }

    fun selectWallpaper(wallpaperId: String) {
        repository.saveSelectedWallpaper(wallpaperId)
        repository.saveCustomWallpaperUri(null) // clear custom uri if bundled wallpaper selected
    }

    fun setCustomWallpaperUri(uri: Uri?) {
        val uriString = uri?.toString()
        repository.saveCustomWallpaperUri(uriString)
        if (uriString != null) {
            _controls.value = _controls.value.copy(snackbarMessage = "Custom image imported from Photo Picker")
        }
    }

    fun setTab(tab: StudioTab) {
        _controls.value = _controls.value.copy(currentTab = tab)
    }

    fun setIconStyle(style: IconStyle) {
        _controls.value = _controls.value.copy(activeIconStyle = style)
    }

    fun setWidgetType(type: WidgetType) {
        _controls.value = _controls.value.copy(activeWidgetType = type)
    }

    fun setCustomNoteText(text: String) {
        _controls.value = _controls.value.copy(customNoteText = text)
    }

    fun applyWallpaper(context: Context, whichFlags: Int, targetLabel: String) {
        viewModelScope.launch {
            val currentWallpaperId = uiState.value.selectedWallpaperId
            val customUri = uiState.value.customWallpaperUri
            val success = WallpaperGenerator.applyWallpaper(context, currentWallpaperId, customUri, whichFlags)
            if (success) {
                repository.markWallpaperApplied()
                _controls.value = _controls.value.copy(snackbarMessage = "Wallpaper applied to $targetLabel successfully")
            } else {
                _controls.value = _controls.value.copy(snackbarMessage = "Direct application failed. Tap 'Export' to set via OxygenOS Photos.")
            }
        }
    }

    fun shareWallpaper(context: Context) {
        val currentWallpaperId = uiState.value.selectedWallpaperId
        val customUri = uiState.value.customWallpaperUri
        val success = WallpaperGenerator.shareWallpaper(context, currentWallpaperId, customUri)
        if (success) {
            _controls.value = _controls.value.copy(snackbarMessage = "Wallpaper exported to device")
        } else {
            _controls.value = _controls.value.copy(snackbarMessage = "Unable to export wallpaper image.")
        }
    }

    fun requestPinClockWidget(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val myProvider = ComponentName(context, DekuClockWidgetProvider::class.java)

            if (appWidgetManager.isRequestPinAppWidgetSupported) {
                val successCallback = PendingIntent.getActivity(
                    context,
                    0,
                    Intent(context, MainActivity::class.java),
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                appWidgetManager.requestPinAppWidget(myProvider, null, successCallback)
                _controls.value = _controls.value.copy(snackbarMessage = "Pin request sent. Tap 'Add' on system dialog.")
            } else {
                _controls.value = _controls.value.copy(snackbarMessage = "Pinning not supported by current launcher. Long press home screen to add.")
            }
        } else {
            _controls.value = _controls.value.copy(snackbarMessage = "Long press on home screen > Widgets > DEKU to add.")
        }
    }

    fun resetDefaults() {
        repository.resetToDefaults()
        _controls.value = InternalUiControls(snackbarMessage = "DEKU Studio restored to defaults")
    }

    fun dismissMessage() {
        _controls.value = _controls.value.copy(snackbarMessage = null)
    }

    class Factory(private val repository: ThemePreferencesRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ThemeStudioViewModel(repository) as T
        }
    }
}
