package com.example.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.data.model.ThemePresetId
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class SavedThemePreferences(
    val themePresetId: ThemePresetId = ThemePresetId.DARK_MONO,
    val accentId: String = "deku_red",
    val selectedWallpaperId: String = "deku_monolith",
    val customWallpaperUri: String? = null,
    val lastAppliedTimestamp: Long = 0L
)

class ThemePreferencesRepository(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("deku_theme_studio_prefs", Context.MODE_PRIVATE)

    private val _preferencesState = MutableStateFlow(loadPreferences())
    val preferencesState: StateFlow<SavedThemePreferences> = _preferencesState.asStateFlow()

    private fun loadPreferences(): SavedThemePreferences {
        val themeStr = prefs.getString(KEY_THEME_PRESET, ThemePresetId.DARK_MONO.name) ?: ThemePresetId.DARK_MONO.name
        val themePresetId = try {
            ThemePresetId.valueOf(themeStr)
        } catch (_: Exception) {
            ThemePresetId.DARK_MONO
        }
        val accentId = prefs.getString(KEY_ACCENT_ID, "deku_red") ?: "deku_red"
        val wallpaperId = prefs.getString(KEY_WALLPAPER_ID, "deku_monolith") ?: "deku_monolith"
        val customUri = prefs.getString(KEY_CUSTOM_WALLPAPER_URI, null)
        val timestamp = prefs.getLong(KEY_LAST_APPLIED, 0L)

        return SavedThemePreferences(
            themePresetId = themePresetId,
            accentId = accentId,
            selectedWallpaperId = wallpaperId,
            customWallpaperUri = customUri,
            lastAppliedTimestamp = timestamp
        )
    }

    fun saveThemePreset(presetId: ThemePresetId) {
        prefs.edit().putString(KEY_THEME_PRESET, presetId.name).apply()
        _preferencesState.value = _preferencesState.value.copy(themePresetId = presetId)
    }

    fun saveAccent(accentId: String) {
        prefs.edit().putString(KEY_ACCENT_ID, accentId).apply()
        _preferencesState.value = _preferencesState.value.copy(accentId = accentId)
    }

    fun saveSelectedWallpaper(wallpaperId: String) {
        prefs.edit().putString(KEY_WALLPAPER_ID, wallpaperId).apply()
        _preferencesState.value = _preferencesState.value.copy(selectedWallpaperId = wallpaperId)
    }

    fun saveCustomWallpaperUri(uriString: String?) {
        prefs.edit().putString(KEY_CUSTOM_WALLPAPER_URI, uriString).apply()
        _preferencesState.value = _preferencesState.value.copy(customWallpaperUri = uriString)
    }

    fun markWallpaperApplied() {
        val now = System.currentTimeMillis()
        prefs.edit().putLong(KEY_LAST_APPLIED, now).apply()
        _preferencesState.value = _preferencesState.value.copy(lastAppliedTimestamp = now)
    }

    fun resetToDefaults() {
        prefs.edit().clear().apply()
        _preferencesState.value = SavedThemePreferences()
    }

    companion object {
        private const val KEY_THEME_PRESET = "theme_preset_id"
        private const val KEY_ACCENT_ID = "accent_id"
        private const val KEY_WALLPAPER_ID = "wallpaper_id"
        private const val KEY_CUSTOM_WALLPAPER_URI = "custom_wallpaper_uri"
        private const val KEY_LAST_APPLIED = "last_applied_timestamp"
    }
}
