package com.example.data.model

import androidx.compose.ui.graphics.Color
import com.example.ui.theme.*

enum class ThemePresetId(val displayName: String) {
    DARK_MONO("Dark Mono"),
    LIGHT_MONO("Light Mono"),
    GRAPHITE("Graphite"),
    EMBER("Ember")
}

data class AccentOption(
    val id: String,
    val name: String,
    val color: Color,
    val hexCode: String
)

val AvailableAccents = listOf(
    AccentOption("deku_red", "DEKU Red", DekuAccentRed, "#E5242A"),
    AccentOption("pure_white", "Pure White", Color(0xFFFFFFFF), "#FFFFFF"),
    AccentOption("smoke_gray", "Smoke Gray", Color(0xFFA0A3A1), "#A0A3A1"),
    AccentOption("burnt_orange", "Burnt Orange", Color(0xFFD95427), "#D95427")
)

data class ThemePreset(
    val id: ThemePresetId,
    val name: String,
    val tagline: String,
    val description: String,
    val isDark: Boolean,
    val background: Color,
    val surface: Color,
    val surfaceVariant: Color,
    val textPrimary: Color,
    val textSecondary: Color,
    val textMuted: Color,
    val accentDefault: Color,
    val border: Color,
    val backgroundHex: String,
    val surfaceHex: String,
    val textPrimaryHex: String,
    val textSecondaryHex: String,
    val borderHex: String,
    val contrastRatioText: String,
    val contrastRatioAccent: String
)

val DarkMonoPreset = ThemePreset(
    id = ThemePresetId.DARK_MONO,
    name = "Dark Mono",
    tagline = "Near-Black Foundation // Nothing x Pixel DNA",
    description = "True near-black foundation (#080909) designed for the OnePlus Nord CE 4 Lite 5G AMOLED panel. Features crisp #F2F2EE typography, #191B1B cards, and restrained #E5242A red highlights.",
    isDark = true,
    background = DekuDarkBackground,
    surface = DekuCardBackground,
    surfaceVariant = DekuElevatedSurface,
    textPrimary = DekuPrimaryText,
    textSecondary = DekuSecondaryText,
    textMuted = Color(0xFF6B6E6C),
    accentDefault = DekuAccentRed,
    border = DekuBorderSubtle,
    backgroundHex = "#080909",
    surfaceHex = "#191B1B",
    textPrimaryHex = "#F2F2EE",
    textSecondaryHex = "#A0A3A1",
    borderHex = "#2C2F2F",
    contrastRatioText = "17.4:1 (AAA)",
    contrastRatioAccent = "5.2:1 (AA)"
)

val LightMonoPreset = ThemePreset(
    id = ThemePresetId.LIGHT_MONO,
    name = "Light Mono",
    tagline = "Tactile Studio Paper // High Sunlight Clarity",
    description = "Pristine architectural light foundation (#F1F1ED) paired with deep charcoal ink typography (#151616), pure white cards, and precision #E5242A accent markings.",
    isDark = false,
    background = DekuLightBackground,
    surface = DekuLightCard,
    surfaceVariant = DekuLightElevated,
    textPrimary = DekuLightTextPrimary,
    textSecondary = DekuLightTextSecondary,
    textMuted = Color(0xFF909492),
    accentDefault = DekuAccentRed,
    border = DekuLightBorder,
    backgroundHex = "#F1F1ED",
    surfaceHex = "#FFFFFF",
    textPrimaryHex = "#151616",
    textSecondaryHex = "#6B6E6C",
    borderHex = "#D4D4CE",
    contrastRatioText = "15.8:1 (AAA)",
    contrastRatioAccent = "4.8:1 (AA)"
)

val GraphitePreset = ThemePreset(
    id = ThemePresetId.GRAPHITE,
    name = "Graphite",
    tagline = "Machined Carbon & Industrial Slate",
    description = "Subtle charcoal slate atmosphere (#121314) inspired by machined industrial hardware. Deep card elevation (#202224) and soft-white readout (#E2E4E0).",
    isDark = true,
    background = DekuGraphiteBackground,
    surface = DekuGraphiteCard,
    surfaceVariant = DekuGraphiteElevated,
    textPrimary = DekuGraphiteText,
    textSecondary = DekuGraphiteSecondary,
    textMuted = Color(0xFF666B69),
    accentDefault = DekuAccentRed,
    border = DekuGraphiteBorder,
    backgroundHex = "#121314",
    surfaceHex = "#202224",
    textPrimaryHex = "#E2E4E0",
    textSecondaryHex = "#909492",
    borderHex = "#33373A",
    contrastRatioText = "14.9:1 (AAA)",
    contrastRatioAccent = "5.1:1 (AA)"
)

val EmberPreset = ThemePreset(
    id = ThemePresetId.EMBER,
    name = "Ember",
    tagline = "Smoked Obsidian & Molten Crimson",
    description = "Deep warm-tinted obsidian canvas (#0D0A0A) with smoked surface containers (#1C1515) and laser-sharp #E5242A indicators.",
    isDark = true,
    background = DekuEmberBackground,
    surface = DekuEmberCard,
    surfaceVariant = DekuEmberElevated,
    textPrimary = DekuEmberText,
    textSecondary = DekuEmberSecondary,
    textMuted = Color(0xFF75625D),
    accentDefault = DekuAccentRed,
    border = DekuEmberBorder,
    backgroundHex = "#0D0A0A",
    surfaceHex = "#1C1515",
    textPrimaryHex = "#F5EBE6",
    textSecondaryHex = "#A6938E",
    borderHex = "#382929",
    contrastRatioText = "16.1:1 (AAA)",
    contrastRatioAccent = "5.3:1 (AA)"
)

val AllThemes = listOf(DarkMonoPreset, LightMonoPreset, GraphitePreset, EmberPreset)

data class WallpaperItem(
    val id: String,
    val title: String,
    val subtitle: String,
    val presetId: ThemePresetId,
    val description: String,
    val primaryBgHex: String,
    val accentHex: String,
    val customUriString: String? = null
)

val CuratedWallpapers = listOf(
    WallpaperItem(
        id = "deku_monolith",
        title = "DEKU Monolith Matrix",
        subtitle = "Monochrome Grid & Crimson Accent",
        presetId = ThemePresetId.DARK_MONO,
        description = "Deep #080909 AMOLED field with precision dot-matrix Cartesian guide-lattice and an offset 45° DEKU red architectural line.",
        primaryBgHex = "#080909",
        accentHex = "#E5242A"
    ),
    WallpaperItem(
        id = "glyphic_redline",
        title = "Glyphic Redline",
        subtitle = "Geometric Minimalist Division",
        presetId = ThemePresetId.DARK_MONO,
        description = "Minimalist dual-block division with matte charcoal planes, centered 20:9 punch-hole clearance, and laser-etched red focal stripe.",
        primaryBgHex = "#121314",
        accentHex = "#E5242A"
    ),
    WallpaperItem(
        id = "paper_blueprint",
        title = "Studio Paper Blueprint",
        subtitle = "Light Editorial Drafting Field",
        presetId = ThemePresetId.LIGHT_MONO,
        description = "High-key #F1F1ED drafting grid with delicate slate Cartesian hairlines and geometric DEKU framing anchors.",
        primaryBgHex = "#F1F1ED",
        accentHex = "#E5242A"
    ),
    WallpaperItem(
        id = "graphite_slate",
        title = "Graphite Slate 20:9",
        subtitle = "Industrial Carbon Geometry",
        presetId = ThemePresetId.GRAPHITE,
        description = "Matte industrial carbon textures calibrated for the OnePlus Nord CE 4 Lite 5G display ratio with bottom dock clearance.",
        primaryBgHex = "#191B1B",
        accentHex = "#E5242A"
    )
)
