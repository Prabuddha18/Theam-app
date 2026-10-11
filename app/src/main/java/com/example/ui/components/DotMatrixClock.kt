package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object DotMatrixGlyphs {
    // 5x7 matrix for digits 0-9
    val ZERO = arrayOf(
        booleanArrayOf(true, true, true, true, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, true, true, true, true)
    )

    val ONE = arrayOf(
        booleanArrayOf(false, false, true, false, false),
        booleanArrayOf(false, true, true, false, false),
        booleanArrayOf(false, false, true, false, false),
        booleanArrayOf(false, false, true, false, false),
        booleanArrayOf(false, false, true, false, false),
        booleanArrayOf(false, false, true, false, false),
        booleanArrayOf(false, true, true, true, false)
    )

    val TWO = arrayOf(
        booleanArrayOf(true, true, true, true, true),
        booleanArrayOf(false, false, false, false, true),
        booleanArrayOf(false, false, false, false, true),
        booleanArrayOf(true, true, true, true, true),
        booleanArrayOf(true, false, false, false, false),
        booleanArrayOf(true, false, false, false, false),
        booleanArrayOf(true, true, true, true, true)
    )

    val THREE = arrayOf(
        booleanArrayOf(true, true, true, true, true),
        booleanArrayOf(false, false, false, false, true),
        booleanArrayOf(false, false, false, false, true),
        booleanArrayOf(true, true, true, true, true),
        booleanArrayOf(false, false, false, false, true),
        booleanArrayOf(false, false, false, false, true),
        booleanArrayOf(true, true, true, true, true)
    )

    val FOUR = arrayOf(
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, true, true, true, true),
        booleanArrayOf(false, false, false, false, true),
        booleanArrayOf(false, false, false, false, true),
        booleanArrayOf(false, false, false, false, true)
    )

    val FIVE = arrayOf(
        booleanArrayOf(true, true, true, true, true),
        booleanArrayOf(true, false, false, false, false),
        booleanArrayOf(true, false, false, false, false),
        booleanArrayOf(true, true, true, true, true),
        booleanArrayOf(false, false, false, false, true),
        booleanArrayOf(false, false, false, false, true),
        booleanArrayOf(true, true, true, true, true)
    )

    val SIX = arrayOf(
        booleanArrayOf(true, true, true, true, true),
        booleanArrayOf(true, false, false, false, false),
        booleanArrayOf(true, false, false, false, false),
        booleanArrayOf(true, true, true, true, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, true, true, true, true)
    )

    val SEVEN = arrayOf(
        booleanArrayOf(true, true, true, true, true),
        booleanArrayOf(false, false, false, false, true),
        booleanArrayOf(false, false, false, true, false),
        booleanArrayOf(false, false, true, false, false),
        booleanArrayOf(false, true, false, false, false),
        booleanArrayOf(false, true, false, false, false),
        booleanArrayOf(false, true, false, false, false)
    )

    val EIGHT = arrayOf(
        booleanArrayOf(true, true, true, true, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, true, true, true, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, true, true, true, true)
    )

    val NINE = arrayOf(
        booleanArrayOf(true, true, true, true, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, false, false, false, true),
        booleanArrayOf(true, true, true, true, true),
        booleanArrayOf(false, false, false, false, true),
        booleanArrayOf(false, false, false, false, true),
        booleanArrayOf(true, true, true, true, true)
    )

    // 2x7 matrix for colon ':'
    val COLON = arrayOf(
        booleanArrayOf(false, false),
        booleanArrayOf(false, false),
        booleanArrayOf(true, true),
        booleanArrayOf(false, false),
        booleanArrayOf(true, true),
        booleanArrayOf(false, false),
        booleanArrayOf(false, false)
    )

    fun getGlyph(char: Char): Array<BooleanArray>? {
        return when (char) {
            '0' -> ZERO
            '1' -> ONE
            '2' -> TWO
            '3' -> THREE
            '4' -> FOUR
            '5' -> FIVE
            '6' -> SIX
            '7' -> SEVEN
            '8' -> EIGHT
            '9' -> NINE
            ':' -> COLON
            else -> null
        }
    }
}

@Composable
fun DotMatrixClock(
    modifier: Modifier = Modifier,
    dotSize: Dp = 4.dp,
    dotSpacing: Dp = 2.5.dp,
    activeColor: Color = MaterialTheme.colorScheme.onBackground,
    inactiveColor: Color = activeColor.copy(alpha = 0.08f),
    showSeconds: Boolean = false
) {
    var currentTimeString by remember {
        mutableStateOf(getDeviceTimeString(showSeconds))
    }

    LaunchedEffect(showSeconds) {
        while (true) {
            currentTimeString = getDeviceTimeString(showSeconds)
            // Update every second if showing seconds, else update every 10 seconds
            delay(if (showSeconds) 1000L else 10000L)
        }
    }

    val density = LocalDensity.current
    val dotRadiusPx = with(density) { dotSize.toPx() / 2f }
    val stepPx = with(density) { (dotSize + dotSpacing).toPx() }
    val charSpacingPx = with(density) { (dotSize * 1.5f).toPx() }

    // Calculate total required width and height in Dp
    val totalCols = currentTimeString.sumOf { char ->
        when (char) {
            ':' -> 2
            in '0'..'9' -> 5
            else -> 2
        }
    }
    val numChars = currentTimeString.length
    val totalWidthPx = (totalCols * stepPx) + ((numChars - 1) * charSpacingPx)
    val totalHeightPx = 7 * stepPx

    val totalWidthDp = with(density) { totalWidthPx.toDp() }
    val totalHeightDp = with(density) { totalHeightPx.toDp() }

    Box(
        modifier = modifier.semantics {
            contentDescription = "Current time is $currentTimeString"
        },
        contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier
                .width(totalWidthDp)
                .height(totalHeightDp)
        ) {
            var currentXOffset = 0f

            currentTimeString.forEach { char ->
                val glyph = DotMatrixGlyphs.getGlyph(char)
                if (glyph != null) {
                    val cols = glyph[0].size
                    val rows = glyph.size

                    for (r in 0 until rows) {
                        for (c in 0 until cols) {
                            val isActive = glyph[r][c]
                            val dotColor = if (isActive) activeColor else inactiveColor

                            val cx = currentXOffset + (c * stepPx) + dotRadiusPx
                            val cy = (r * stepPx) + dotRadiusPx

                            drawCircle(
                                color = dotColor,
                                radius = dotRadiusPx,
                                center = Offset(cx, cy)
                            )
                        }
                    }
                    currentXOffset += (cols * stepPx) + charSpacingPx
                }
            }
        }
    }
}

private fun getDeviceTimeString(showSeconds: Boolean): String {
    val pattern = if (showSeconds) "HH:mm:ss" else "HH:mm"
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date())
}
