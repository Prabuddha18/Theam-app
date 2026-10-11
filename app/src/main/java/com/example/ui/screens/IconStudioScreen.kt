package com.example.ui.screens

import android.content.Context
import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
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
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.DekuShapes
import com.example.ui.theme.JetBrainsMono
import com.example.ui.theme.SpaceGrotesk
import com.example.ui.viewmodel.IconStyle
import com.example.ui.viewmodel.ThemeStudioUiState

data class DekuIconDef(
    val name: String,
    val type: String
)

val SampleDekuIcons = listOf(
    DekuIconDef("Phone", "phone"),
    DekuIconDef("Messages", "messages"),
    DekuIconDef("Camera", "camera"),
    DekuIconDef("Settings", "settings"),
    DekuIconDef("Files", "files"),
    DekuIconDef("Browser", "browser"),
    DekuIconDef("Clock", "clock"),
    DekuIconDef("Calendar", "calendar"),
    DekuIconDef("Notes", "notes"),
    DekuIconDef("Music", "music"),
    DekuIconDef("Terminal", "terminal"),
    DekuIconDef("Studio", "studio")
)

@Composable
fun IconStudioScreen(
    uiState: ThemeStudioUiState,
    onStyleSelected: (IconStyle) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val accent = uiState.activeAccent
    val currentStyle = uiState.activeIconStyle

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 14.dp)
            .testTag("icon_studio_screen")
    ) {
        // Section Header
        Text(
            text = "ICON STUDIO",
            fontFamily = JetBrainsMono,
            fontSize = 11.sp,
            letterSpacing = 1.5.sp,
            fontWeight = FontWeight.Medium,
            color = accent.color
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Monochrome Iconography",
            fontFamily = SpaceGrotesk,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Original geometric vector artwork with consistent line weights, square proportions, and restrained red focal dots.",
            fontFamily = SpaceGrotesk,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        // Style Segmented Selector
        Text(
            text = "ICON STYLE PRESET",
            fontFamily = JetBrainsMono,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            IconStyle.values().forEach { style ->
                val isSelected = currentStyle == style

                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(DekuShapes.small)
                        .background(if (isSelected) MaterialTheme.colorScheme.surfaceVariant else MaterialTheme.colorScheme.surface)
                        .border(
                            width = if (isSelected) 1.5.dp else 1.dp,
                            color = if (isSelected) accent.color else MaterialTheme.colorScheme.outlineVariant,
                            shape = DekuShapes.small
                        )
                        .clickable { onStyleSelected(style) }
                        .padding(vertical = 10.dp, horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = style.displayName,
                            fontFamily = SpaceGrotesk,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 11.sp,
                            color = if (isSelected) accent.color else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Icon Grid Preview Canvas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(DekuShapes.medium)
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, MaterialTheme.colorScheme.outline, DekuShapes.medium)
                .padding(16.dp)
                .testTag("icon_grid_canvas")
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "VECTOR ARTWORK PREVIEW (12 ITEMS)",
                        fontFamily = JetBrainsMono,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Bold,
                        color = accent.color
                    )

                    Text(
                        text = currentStyle.displayName.uppercase(),
                        fontFamily = JetBrainsMono,
                        fontSize = 9.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                // 4x3 Grid of icons
                val rows = SampleDekuIcons.chunked(3)
                rows.forEach { rowItems ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        rowItems.forEach { item ->
                            IconPreviewTile(
                                item = item,
                                style = currentStyle,
                                accentColor = accent.color,
                                modifier = Modifier.weight(1f)
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Export Action
        Button(
            onClick = {
                // Toast/Snackbar trigger via ViewModel in MainActivity
            },
            shape = DekuShapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("export_icons_btn")
        ) {
            Text(
                text = "EXPORT ICON PACK SPECIFICATION",
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                letterSpacing = 0.5.sp
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Technical Limitations & Launcher Integration Note
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(DekuShapes.small)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(1.dp, MaterialTheme.colorScheme.outlineVariant, DekuShapes.small)
                .padding(14.dp)
        ) {
            Column {
                Text(
                    text = "OXYGENOS & LAUNCHER INTEGRATION NOTICE",
                    fontFamily = JetBrainsMono,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = accent.color
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "Android security boundaries prevent any standard third-party application from replacing home-screen application icons automatically without user configuration.",
                    fontFamily = SpaceGrotesk,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "To apply DEKU icons across your home screen: use launchers with icon pack support (such as Lawnchair or Nova Launcher) or OnePlus's built-in icon customizer under Settings > Wallpaper & style > Icons.",
                    fontFamily = SpaceGrotesk,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun IconPreviewTile(
    item: DekuIconDef,
    style: IconStyle,
    accentColor: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(DekuShapes.small)
            .background(MaterialTheme.colorScheme.background)
            .border(1.dp, MaterialTheme.colorScheme.outlineVariant, DekuShapes.small)
            .padding(vertical = 12.dp, horizontal = 4.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Icon canvas drawing
        Box(
            modifier = Modifier
                .size(44.dp)
                .clip(DekuShapes.small)
                .background(
                    if (style == IconStyle.MONOCHROME_DARK) MaterialTheme.colorScheme.surfaceVariant
                    else MaterialTheme.colorScheme.background
                )
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.outlineVariant,
                    shape = DekuShapes.small
                ),
            contentAlignment = Alignment.Center
        ) {
            Canvas(modifier = Modifier.size(24.dp)) {
                val w = size.width
                val h = size.height

                when (item.type) {
                    "phone" -> {
                        val path = Path().apply {
                            moveTo(w * 0.2f, h * 0.15f)
                            lineTo(w * 0.8f, h * 0.15f)
                            lineTo(w * 0.8f, h * 0.85f)
                            lineTo(w * 0.2f, h * 0.85f)
                            close()
                        }
                        drawPath(path, color = Color(0xFFF2F2EE), style = Stroke(width = 2.5f))
                        drawCircle(color = if (style == IconStyle.RED_ACCENT_DOT) accentColor else Color(0xFFF2F2EE), radius = 2f, center = Offset(w * 0.5f, h * 0.75f))
                    }
                    "messages" -> {
                        drawRoundRect(
                            color = Color(0xFFF2F2EE),
                            topLeft = Offset(w * 0.15f, h * 0.2f),
                            size = Size(w * 0.7f, h * 0.5f),
                            style = Stroke(width = 2.5f),
                            cornerRadius = androidx.compose.ui.geometry.CornerRadius(4f, 4f)
                        )
                        drawCircle(color = if (style == IconStyle.RED_ACCENT_DOT) accentColor else Color(0xFFF2F2EE), radius = 2f, center = Offset(w * 0.35f, h * 0.45f))
                        drawCircle(color = Color(0xFFF2F2EE), radius = 2f, center = Offset(w * 0.5f, h * 0.45f))
                        drawCircle(color = Color(0xFFF2F2EE), radius = 2f, center = Offset(w * 0.65f, h * 0.45f))
                    }
                    "camera" -> {
                        drawRect(
                            color = Color(0xFFF2F2EE),
                            topLeft = Offset(w * 0.15f, h * 0.25f),
                            size = Size(w * 0.7f, h * 0.55f),
                            style = Stroke(width = 2.5f)
                        )
                        drawCircle(color = Color(0xFFF2F2EE), radius = 5f, center = Offset(w * 0.5f, h * 0.52f), style = Stroke(width = 2.5f))
                        if (style == IconStyle.RED_ACCENT_DOT) {
                            drawCircle(color = accentColor, radius = 2f, center = Offset(w * 0.7f, h * 0.35f))
                        }
                    }
                    "settings" -> {
                        drawCircle(color = Color(0xFFF2F2EE), radius = 7f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(width = 2.5f))
                        drawCircle(color = if (style == IconStyle.RED_ACCENT_DOT) accentColor else Color(0xFFF2F2EE), radius = 2.5f, center = Offset(w * 0.5f, h * 0.5f))
                    }
                    "files" -> {
                        val path = Path().apply {
                            moveTo(w * 0.15f, h * 0.25f)
                            lineTo(w * 0.45f, h * 0.25f)
                            lineTo(w * 0.55f, h * 0.35f)
                            lineTo(w * 0.85f, h * 0.35f)
                            lineTo(w * 0.85f, h * 0.75f)
                            lineTo(w * 0.15f, h * 0.75f)
                            close()
                        }
                        drawPath(path, color = Color(0xFFF2F2EE), style = Stroke(width = 2.5f))
                        if (style == IconStyle.RED_ACCENT_DOT) {
                            drawCircle(color = accentColor, radius = 2f, center = Offset(w * 0.7f, h * 0.55f))
                        }
                    }
                    "clock" -> {
                        drawCircle(color = Color(0xFFF2F2EE), radius = 8f, center = Offset(w * 0.5f, h * 0.5f), style = Stroke(width = 2.5f))
                        drawLine(color = Color(0xFFF2F2EE), start = Offset(w * 0.5f, h * 0.5f), end = Offset(w * 0.5f, h * 0.3f), strokeWidth = 2.5f)
                        drawLine(color = if (style == IconStyle.RED_ACCENT_DOT) accentColor else Color(0xFFF2F2EE), start = Offset(w * 0.5f, h * 0.5f), end = Offset(w * 0.65f, h * 0.5f), strokeWidth = 2.5f)
                    }
                    else -> {
                        drawRect(
                            color = Color(0xFFF2F2EE),
                            topLeft = Offset(w * 0.2f, h * 0.2f),
                            size = Size(w * 0.6f, h * 0.6f),
                            style = Stroke(width = 2.5f)
                        )
                        if (style == IconStyle.RED_ACCENT_DOT) {
                            drawCircle(color = accentColor, radius = 2.5f, center = Offset(w * 0.5f, h * 0.5f))
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = item.name,
            fontFamily = SpaceGrotesk,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}
