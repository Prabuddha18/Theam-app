package com.example.ui.screens

import android.app.WallpaperManager
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.material.icons.filled.AddPhotoAlternate
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.CuratedWallpapers
import com.example.data.model.WallpaperItem
import com.example.ui.theme.DekuShapes
import com.example.ui.theme.JetBrainsMono
import com.example.ui.theme.SpaceGrotesk
import com.example.ui.viewmodel.ThemeStudioUiState
import com.example.util.WallpaperGenerator

@Composable
fun WallpaperStudioScreen(
    uiState: ThemeStudioUiState,
    onSelectWallpaper: (String) -> Unit,
    onCustomPhotoSelected: (Uri?) -> Unit,
    onApplyWallpaper: (Context, Int, String) -> Unit,
    onShareWallpaper: (Context) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val selectedWallpaper = uiState.selectedWallpaper
    val accent = uiState.activeAccent

    // Android System Photo Picker Launcher (Zero file permissions required!)
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri: Uri? ->
        onCustomPhotoSelected(uri)
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(horizontal = 20.dp, vertical = 14.dp)
            .testTag("wallpaper_studio_screen")
    ) {
        // Section Header
        Text(
            text = "WALLPAPER ARCHITECTURE",
            fontFamily = JetBrainsMono,
            fontSize = 11.sp,
            letterSpacing = 1.5.sp,
            fontWeight = FontWeight.Medium,
            color = accent.color
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Wallpaper Library",
            fontFamily = SpaceGrotesk,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Original bundled 1080x2400 graphics and custom photo selection via Android system picker.",
            fontFamily = SpaceGrotesk,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        // 20:9 Phone Mockup Canvas Preview
        Box(
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .width(190.dp)
                .aspectRatio(9f / 19.5f) // Matches OnePlus Nord CE 4 Lite 20:9 ratio
                .clip(DekuShapes.medium)
                .background(Color(0xFF080909))
                .border(2.dp, MaterialTheme.colorScheme.outline, DekuShapes.medium)
                .testTag("wallpaper_phone_mockup")
        ) {
            if (!uiState.customWallpaperUri.isNullOrEmpty()) {
                val customBitmap = remember(uiState.customWallpaperUri) {
                    WallpaperGenerator.loadBitmapFromUri(context, Uri.parse(uiState.customWallpaperUri))
                }
                if (customBitmap != null) {
                    Image(
                        bitmap = customBitmap.asImageBitmap(),
                        contentDescription = "Custom user wallpaper",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    WallpaperCanvas(
                        wallpaperId = selectedWallpaper.id,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                WallpaperCanvas(
                    wallpaperId = selectedWallpaper.id,
                    modifier = Modifier.fillMaxSize()
                )
            }

            // OnePlus Nord CE 4 Lite center punch-hole camera dot
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(7.dp)
                    .clip(CircleShape)
                    .background(Color.Black)
                    .border(0.5.dp, Color(0x66FFFFFF), CircleShape)
                    .align(Alignment.TopCenter)
            )

            // Bottom home gesture pill
            Box(
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .width(44.dp)
                    .height(3.dp)
                    .background(Color(0x88FFFFFF), DekuShapes.extraSmall)
                    .align(Alignment.BottomCenter)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Selected Wallpaper Meta Info
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(DekuShapes.small)
                .background(MaterialTheme.colorScheme.surface)
                .border(1.dp, MaterialTheme.colorScheme.outline, DekuShapes.small)
                .padding(14.dp)
        ) {
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = if (!uiState.customWallpaperUri.isNullOrEmpty()) "CUSTOM PHOTO IMPORT" else selectedWallpaper.title.uppercase(),
                        fontFamily = SpaceGrotesk,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Text(
                        text = "1080 x 2400",
                        fontFamily = JetBrainsMono,
                        fontSize = 10.sp,
                        color = accent.color
                    )
                }

                Text(
                    text = if (!uiState.customWallpaperUri.isNullOrEmpty()) "Imported through Android System Photo Picker" else selectedWallpaper.subtitle,
                    fontFamily = JetBrainsMono,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = if (!uiState.customWallpaperUri.isNullOrEmpty()) "Ready to set directly to Home Screen, Lock Screen, or export via OxygenOS." else selectedWallpaper.description,
                    fontFamily = SpaceGrotesk,
                    fontSize = 12.sp,
                    lineHeight = 16.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // System Photo Picker Action Card
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(DekuShapes.medium)
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .border(1.dp, accent.color.copy(alpha = 0.5f), DekuShapes.medium)
                .clickable {
                    photoPickerLauncher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
                .padding(14.dp)
                .testTag("browse_photos_button")
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(accent.color.copy(alpha = 0.15f), DekuShapes.small),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.AddPhotoAlternate,
                        contentDescription = "Browse Images",
                        tint = accent.color
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = "BROWSE USER IMAGES",
                        fontFamily = JetBrainsMono,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        text = "Select custom photos using zero-permission Android Photo Picker",
                        fontFamily = SpaceGrotesk,
                        fontSize = 11.sp,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Bundled Wallpapers Grid
        Text(
            text = "BUNDLED ORIGINAL WALLPAPERS",
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
            CuratedWallpapers.take(2).forEach { item ->
                WallpaperThumbCard(
                    wallpaper = item,
                    isSelected = uiState.customWallpaperUri == null && item.id == uiState.selectedWallpaperId,
                    accentColor = accent.color,
                    onSelect = { onSelectWallpaper(item.id) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CuratedWallpapers.drop(2).take(2).forEach { item ->
                WallpaperThumbCard(
                    wallpaper = item,
                    isSelected = uiState.customWallpaperUri == null && item.id == uiState.selectedWallpaperId,
                    accentColor = accent.color,
                    onSelect = { onSelectWallpaper(item.id) },
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(22.dp))

        // Action Buttons (Rectangular, 8-16dp radius)
        Text(
            text = "APPLY ACTIONS",
            fontFamily = JetBrainsMono,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                onApplyWallpaper(context, WallpaperManager.FLAG_SYSTEM, "Home Screen")
            },
            shape = DekuShapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = accent.color,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("apply_home_btn")
        ) {
            Text(
                text = "APPLY TO HOME SCREEN",
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                letterSpacing = 0.5.sp
            )
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            OutlinedButton(
                onClick = {
                    onApplyWallpaper(context, WallpaperManager.FLAG_LOCK, "Lock Screen")
                },
                shape = DekuShapes.small,
                modifier = Modifier
                    .weight(1f)
                    .testTag("apply_lock_btn")
            ) {
                Text(
                    text = "SET LOCK SCREEN",
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            OutlinedButton(
                onClick = {
                    val flags = WallpaperManager.FLAG_SYSTEM or WallpaperManager.FLAG_LOCK
                    onApplyWallpaper(context, flags, "Home & Lock Screens")
                },
                shape = DekuShapes.small,
                modifier = Modifier
                    .weight(1f)
                    .testTag("apply_both_btn")
            ) {
                Text(
                    text = "SET BOTH SCREENS",
                    fontFamily = SpaceGrotesk,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 11.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onShareWallpaper(context) },
            shape = DekuShapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant,
                contentColor = MaterialTheme.colorScheme.onSurface
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("export_png_btn")
        ) {
            Text(
                text = "EXPORT / OPEN IN OXYGENOS PHOTOS",
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // System Scope Disclaimer
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
                    text = "SYSTEM SCOPE NOTICE",
                    fontFamily = JetBrainsMono,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = accent.color
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Setting wallpaper updates the Android home and lock screen canvas. On non-root OxygenOS, third-party apps cannot modify proprietary status bar battery glyphs, quick setting headers, or OxygenOS system icons directly.",
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
private fun WallpaperThumbCard(
    wallpaper: WallpaperItem,
    isSelected: Boolean,
    accentColor: Color,
    onSelect: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(DekuShapes.small)
            .background(MaterialTheme.colorScheme.surface)
            .border(
                width = if (isSelected) 2.dp else 1.dp,
                color = if (isSelected) accentColor else MaterialTheme.colorScheme.outlineVariant,
                shape = DekuShapes.small
            )
            .clickable { onSelect() }
            .padding(8.dp)
            .testTag("thumb_${wallpaper.id}")
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .clip(DekuShapes.extraSmall)
                    .background(Color(0xFF080909))
            ) {
                WallpaperCanvas(
                    wallpaperId = wallpaper.id,
                    modifier = Modifier.fillMaxSize()
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = wallpaper.title,
                fontFamily = SpaceGrotesk,
                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                fontSize = 11.sp,
                maxLines = 1,
                color = if (isSelected) accentColor else MaterialTheme.colorScheme.onSurface
            )

            Text(
                text = wallpaper.accentHex,
                fontFamily = JetBrainsMono,
                fontSize = 9.sp,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun WallpaperCanvas(
    wallpaperId: String,
    modifier: Modifier = Modifier
) {
    Canvas(modifier = modifier) {
        val w = size.width
        val h = size.height

        when (wallpaperId) {
            "deku_monolith" -> {
                drawRect(color = Color(0xFF080909))

                // Dot matrix grid
                val stepX = w / 8f
                val stepY = h / 16f
                for (i in 1..7) {
                    for (j in 1..15) {
                        drawCircle(Color(0xFF202222), radius = 1.2f, center = Offset(i * stepX, j * stepY))
                    }
                }

                // Plate
                val plate = Path().apply {
                    moveTo(w * 0.1f, h * 0.32f)
                    lineTo(w * 0.9f, h * 0.26f)
                    lineTo(w * 0.9f, h * 0.68f)
                    lineTo(w * 0.1f, h * 0.74f)
                    close()
                }
                drawPath(plate, color = Color(0xFF141616))

                // Redline
                val redline = Path().apply {
                    moveTo(w * 0.18f, h * 0.45f)
                    lineTo(w * 0.85f, h * 0.38f)
                    lineTo(w * 0.82f, h * 0.40f)
                    lineTo(w * 0.15f, h * 0.47f)
                    close()
                }
                drawPath(redline, color = Color(0xFFE5242A))

                drawLine(
                    color = Color(0xFFF2F2EE),
                    start = Offset(w * 0.15f, h * 0.47f),
                    end = Offset(w * 0.85f, h * 0.58f),
                    strokeWidth = 1.5f
                )
            }
            "glyphic_redline" -> {
                drawRect(color = Color(0xFF121314))
                drawRect(color = Color(0xFF1C1E20), topLeft = Offset(0f, h * 0.4f), size = Size(w, h * 0.25f))
                drawRect(color = Color(0xFFE5242A), topLeft = Offset(0f, h * 0.52f), size = Size(w, h * 0.02f))
            }
            "paper_blueprint" -> {
                drawRect(color = Color(0xFFF1F1ED))
                val step = w / 8f
                for (i in 0..8) {
                    drawLine(Color(0xFFDFE0DA), Offset(i * step, 0f), Offset(i * step, h), strokeWidth = 1f)
                }
                drawRect(color = Color(0xFF151616), topLeft = Offset(w * 0.15f, h * 0.38f), size = Size(w * 0.7f, h * 0.1f))
                drawRect(color = Color(0xFFE5242A), topLeft = Offset(w * 0.45f, h * 0.48f), size = Size(w * 0.1f, h * 0.04f))
            }
            "graphite_slate" -> {
                drawRect(color = Color(0xFF191B1B))
                drawRoundRect(
                    color = Color(0xFF252727),
                    topLeft = Offset(w * 0.08f, h * 0.82f),
                    size = Size(w * 0.84f, h * 0.12f),
                    cornerRadius = androidx.compose.ui.geometry.CornerRadius(8f, 8f)
                )
                drawRect(color = Color(0xFF202224), topLeft = Offset(w * 0.15f, h * 0.25f), size = Size(w * 0.7f, h * 0.4f))
                drawCircle(color = Color(0xFFE5242A), radius = 6f, center = Offset(w * 0.5f, h * 0.45f))
                drawRect(color = Color(0xFF33373A), topLeft = Offset(w * 0.15f, h * 0.25f), size = Size(w * 0.7f, h * 0.4f), style = Stroke(width = 1.5f))
            }
        }
    }
}
