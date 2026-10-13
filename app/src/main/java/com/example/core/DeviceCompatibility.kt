package com.example.core

import android.os.Build

data class DeviceProfile(
    val manufacturer: String,
    val model: String,
    val androidVersion: String,
    val apiLevel: Int,
    val isUnrooted: Boolean = true,
    val isDynamicColorSupported: Boolean,
    val isAppWidgetPinningSupported: Boolean,
    val isPhotoPickerNative: Boolean,
    val manufacturerAdvice: String
)

object DeviceCompatibilityDetector {

    fun getProfile(): DeviceProfile {
        val manufacturer = Build.MANUFACTURER.orEmpty().replaceFirstChar { it.uppercase() }
        val model = Build.MODEL.orEmpty()
        val androidVersion = Build.VERSION.RELEASE.orEmpty()
        val apiLevel = Build.VERSION.SDK_INT

        val isDynamicColor = apiLevel >= Build.VERSION_CODES.S // Android 12+
        val isPinningSupported = apiLevel >= Build.VERSION_CODES.O // Android 8.0+
        val isPhotoPicker = apiLevel >= Build.VERSION_CODES.TIRAMISU // Android 13+ native, backported via Google Play

        val advice = when {
            manufacturer.contains("Oneplus", ignoreCase = true) || manufacturer.contains("Oppo", ignoreCase = true) ->
                "OxygenOS / ColorOS detected. Wallpapers, widgets, and color tokens apply natively. For global status bar or lock screen changes, OxygenOS requires root or proprietary Theme Store packages."
            manufacturer.contains("Samsung", ignoreCase = true) ->
                "Samsung One UI detected. Native WallpaperManager and One UI widgets supported. Use Samsung Good Lock / Theme Park or an alternative launcher for system-wide icon replacement."
            manufacturer.contains("Google", ignoreCase = true) ->
                "Google Pixel detected. Full Material You palette alignment, At-a-Glance layout, and Android App Widgets natively supported."
            manufacturer.contains("Xiaomi", ignoreCase = true) || manufacturer.contains("Redmi", ignoreCase = true) ->
                "Xiaomi HyperOS / MIUI detected. Custom 20:9 wallpapers and widgets apply natively."
            manufacturer.contains("Motorola", ignoreCase = true) ->
                "Motorola MyUX detected. Near-stock Android architecture supports all native wallpaper and widget actions."
            else ->
                "Android $androidVersion ($manufacturer $model) detected. Native WallpaperManager, system photo picker, and Home Screen App Widgets are fully supported."
        }

        return DeviceProfile(
            manufacturer = if (manufacturer.isBlank()) "Android" else manufacturer,
            model = if (model.isBlank()) "Device" else model,
            androidVersion = if (androidVersion.isBlank()) "14" else androidVersion,
            apiLevel = apiLevel,
            isUnrooted = true,
            isDynamicColorSupported = isDynamicColor,
            isAppWidgetPinningSupported = isPinningSupported,
            isPhotoPickerNative = isPhotoPicker,
            manufacturerAdvice = advice
        )
    }
}
