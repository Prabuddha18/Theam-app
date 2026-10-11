package com.example.ui.screens

import android.content.Context
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.DotMatrixClock
import com.example.ui.theme.DekuShapes
import com.example.ui.theme.JetBrainsMono
import com.example.ui.theme.SpaceGrotesk
import com.example.ui.viewmodel.ThemeStudioUiState
import com.example.ui.viewmodel.WidgetType
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun WidgetStudioScreen(
    uiState: ThemeStudioUiState,
    onWidgetTypeSelected: (WidgetType) -> Unit,
    onNoteTextChanged: (String) -> Unit,
    onRequestPinWidget: (Context) -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val accent = uiState.activeAccent
    val selectedType = uiState.activeWidgetType

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
            .testTag("widget_studio_screen")
    ) {
        // Section Header
        Text(
            text = "WIDGET STUDIO",
            fontFamily = JetBrainsMono,
            fontSize = 11.sp,
            letterSpacing = 1.5.sp,
            fontWeight = FontWeight.Medium,
            color = accent.color
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = "Home Screen Widgets",
            fontFamily = SpaceGrotesk,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Text(
            text = "Live home-screen widgets powered by Android RemoteViews and AppWidgetProvider. Synchronized with real device time.",
            fontFamily = SpaceGrotesk,
            fontSize = 13.sp,
            lineHeight = 18.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 4.dp, bottom = 16.dp)
        )

        // Widget Type Selection Tabs
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            WidgetType.values().forEach { type ->
                val isSelected = selectedType == type

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
                        .clickable { onWidgetTypeSelected(type) }
                        .padding(vertical = 10.dp, horizontal = 4.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = type.displayName,
                        fontFamily = SpaceGrotesk,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        fontSize = 10.sp,
                        color = if (isSelected) accent.color else MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Live Interactive In-App Widget Preview Canvas
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(DekuShapes.medium)
                .background(Color(0xFF080909))
                .border(1.dp, Color(0xFF2C2F2F), DekuShapes.medium)
                .padding(20.dp)
                .testTag("widget_preview_canvas")
        ) {
            when (selectedType) {
                WidgetType.LARGE_DOT_MATRIX -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(modifier = Modifier.size(6.dp).background(accent.color, CircleShape))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("DEKU 4x2", fontFamily = JetBrainsMono, fontSize = 9.sp, color = Color(0xFFA0A3A1))
                            }
                            Text(formattedDate, fontFamily = JetBrainsMono, fontSize = 9.sp, color = Color(0xFFA0A3A1))
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        DotMatrixClock(
                            dotSize = 5.dp,
                            dotSpacing = 2.5.dp,
                            activeColor = Color(0xFFF2F2EE),
                            inactiveColor = Color(0x18F2F2EE)
                        )

                        Spacer(modifier = Modifier.height(12.dp))

                        Text(
                            text = "ONEPLUS NORD CE 4 LITE 5G // 120HZ AMOLED",
                            fontFamily = JetBrainsMono,
                            fontSize = 8.sp,
                            color = Color(0xFF6B6E6C)
                        )
                    }
                }
                WidgetType.COMPACT_CLOCK -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text("COMPACT 2x1", fontFamily = JetBrainsMono, fontSize = 9.sp, color = Color(0xFFA0A3A1))
                        Spacer(modifier = Modifier.height(8.dp))
                        DotMatrixClock(
                            dotSize = 3.5.dp,
                            dotSpacing = 2.dp,
                            activeColor = Color(0xFFF2F2EE),
                            inactiveColor = Color(0x18F2F2EE)
                        )
                    }
                }
                WidgetType.DATE_DISPLAY -> {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(8.dp)
                                .background(accent.color, CircleShape)
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = formattedDate,
                            fontFamily = SpaceGrotesk,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            color = Color(0xFFF2F2EE)
                        )
                        Text(
                            text = "NO EVENT PENDING",
                            fontFamily = JetBrainsMono,
                            fontSize = 10.sp,
                            color = Color(0xFFA0A3A1)
                        )
                    }
                }
                WidgetType.NOTES_THOUGHTS -> {
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("QUICK THOUGHT", fontFamily = JetBrainsMono, fontSize = 9.sp, color = accent.color)
                            Text("LOCAL ONLY", fontFamily = JetBrainsMono, fontSize = 8.sp, color = Color(0xFFA0A3A1))
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = uiState.customNoteText,
                            fontFamily = SpaceGrotesk,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            color = Color(0xFFF2F2EE)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (selectedType == WidgetType.NOTES_THOUGHTS) {
            OutlinedTextField(
                value = uiState.customNoteText,
                onValueChange = onNoteTextChanged,
                label = { Text("Custom Note Text", fontFamily = JetBrainsMono, fontSize = 10.sp) },
                singleLine = false,
                maxLines = 3,
                shape = DekuShapes.small,
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = accent.color,
                    unfocusedBorderColor = MaterialTheme.colorScheme.outline
                ),
                modifier = Modifier.fillMaxWidth().testTag("notes_input_field")
            )
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Direct Pin Action
        Button(
            onClick = { onRequestPinWidget(context) },
            shape = DekuShapes.small,
            colors = ButtonDefaults.buttonColors(
                containerColor = accent.color,
                contentColor = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .testTag("pin_widget_button")
        ) {
            Text(
                text = "PIN DEKU CLOCK TO HOME SCREEN",
                fontFamily = SpaceGrotesk,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                letterSpacing = 0.5.sp
            )
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Android Home Screen Manual Setup Instructions Card
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
                    text = "MANUAL HOME SCREEN PLACEMENT",
                    fontFamily = JetBrainsMono,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = accent.color
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = "1. Long-press any empty area on your OnePlus OxygenOS home screen.\n" +
                            "2. Tap 'Widgets' from the launcher bottom menu.\n" +
                            "3. Scroll to 'DEKU Theme Studio'.\n" +
                            "4. Touch and hold the DEKU Clock widget, then drag it to your preferred position.",
                    fontFamily = SpaceGrotesk,
                    fontSize = 11.sp,
                    lineHeight = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = "Note: Battery and weather widgets are omitted in accordance with our Zero Fabricated Data policy.",
                    fontFamily = SpaceGrotesk,
                    fontSize = 10.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }

        Spacer(modifier = Modifier.height(24.dp))
    }
}
