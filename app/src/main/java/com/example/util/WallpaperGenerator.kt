package com.example.util

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object WallpaperGenerator {

    const val NORD_WIDTH = 1080
    const val NORD_HEIGHT = 2400

    fun renderWallpaperBitmap(
        wallpaperId: String,
        width: Int = NORD_WIDTH,
        height: Int = NORD_HEIGHT
    ): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        when (wallpaperId) {
            "deku_monolith" -> renderDekuMonolith(canvas, width, height, paint)
            "glyphic_redline" -> renderGlyphicRedline(canvas, width, height, paint)
            "paper_blueprint" -> renderPaperBlueprint(canvas, width, height, paint)
            "graphite_slate" -> renderGraphiteSlate(canvas, width, height, paint)
            else -> renderDekuMonolith(canvas, width, height, paint)
        }

        return bitmap
    }

    private fun renderDekuMonolith(canvas: Canvas, w: Int, h: Int, paint: Paint) {
        // Deep #080909 near-black
        canvas.drawColor(android.graphics.Color.parseColor("#080909"))

        // Dot matrix Cartesian grid
        paint.color = android.graphics.Color.parseColor("#222424")
        paint.style = Paint.Style.FILL
        val stepX = w / 16f
        val stepY = h / 32f
        for (i in 1..15) {
            for (j in 1..31) {
                canvas.drawCircle(i * stepX, j * stepY, 2.5f, paint)
            }
        }

        // Secondary geometric architectural plate
        paint.color = android.graphics.Color.parseColor("#141616")
        val plate = Path()
        plate.moveTo(w * 0.1f, h * 0.32f)
        plate.lineTo(w * 0.9f, h * 0.26f)
        plate.lineTo(w * 0.9f, h * 0.68f)
        plate.lineTo(w * 0.1f, h * 0.74f)
        plate.close()
        canvas.drawPath(plate, paint)

        // Precision DEKU Red accent monolith line (#E5242A)
        paint.color = android.graphics.Color.parseColor("#E5242A")
        val redline = Path()
        redline.moveTo(w * 0.18f, h * 0.45f)
        redline.lineTo(w * 0.85f, h * 0.38f)
        redline.lineTo(w * 0.82f, h * 0.40f)
        redline.lineTo(w * 0.15f, h * 0.47f)
        redline.close()
        canvas.drawPath(redline, paint)

        // Clean ivory structural hairline
        paint.color = android.graphics.Color.parseColor("#F2F2EE")
        paint.strokeWidth = 3f
        paint.style = Paint.Style.STROKE
        canvas.drawLine(w * 0.15f, h * 0.47f, w * 0.85f, h * 0.58f, paint)

        // Monogram dot cluster
        paint.style = Paint.Style.FILL
        paint.color = android.graphics.Color.parseColor("#F2F2EE")
        canvas.drawCircle(w * 0.5f, h * 0.52f, 12f, paint)
        paint.color = android.graphics.Color.parseColor("#E5242A")
        canvas.drawCircle(w * 0.5f, h * 0.52f, 5f, paint)

        // Tech specs
        paint.color = android.graphics.Color.parseColor("#6B6E6C")
        paint.textSize = 28f
        canvas.drawText("DEKU STUDIO // MONOLITH 01", w * 0.15f, h * 0.78f, paint)
        canvas.drawText("1080 x 2400 AMOLED // ONEPLUS NORD", w * 0.15f, h * 0.80f, paint)
    }

    private fun renderGlyphicRedline(canvas: Canvas, w: Int, h: Int, paint: Paint) {
        canvas.drawColor(android.graphics.Color.parseColor("#121314"))

        // Matte slab
        paint.style = Paint.Style.FILL
        paint.color = android.graphics.Color.parseColor("#1C1E20")
        canvas.drawRect(0f, h * 0.4f, w.toFloat(), h * 0.65f, paint)

        // Red focal beam
        paint.color = android.graphics.Color.parseColor("#E5242A")
        canvas.drawRect(0f, h * 0.52f, w.toFloat(), h * 0.535f, paint)

        // Subtly spaced horizontal lines
        paint.color = android.graphics.Color.parseColor("#282C2E")
        paint.strokeWidth = 2f
        paint.style = Paint.Style.STROKE
        canvas.drawLine(0f, h * 0.4f, w.toFloat(), h * 0.4f, paint)
        canvas.drawLine(0f, h * 0.65f, w.toFloat(), h * 0.65f, paint)

        paint.style = Paint.Style.FILL
        paint.color = android.graphics.Color.parseColor("#A0A3A1")
        paint.textSize = 26f
        canvas.drawText("DEKU // GLYPHIC DIVISION", w * 0.1f, h * 0.60f, paint)
    }

    private fun renderPaperBlueprint(canvas: Canvas, w: Int, h: Int, paint: Paint) {
        // High-key paper #F1F1ED
        canvas.drawColor(android.graphics.Color.parseColor("#F1F1ED"))

        // Slate drafting grid
        paint.color = android.graphics.Color.parseColor("#DFE0DA")
        paint.strokeWidth = 1.5f
        paint.style = Paint.Style.STROKE
        val step = w / 12f
        for (i in 0..12) {
            canvas.drawLine(i * step, 0f, i * step, h.toFloat(), paint)
        }
        val vStep = h / 24f
        for (j in 0..24) {
            canvas.drawLine(0f, j * vStep, w.toFloat(), j * vStep, paint)
        }

        // Deep charcoal structural blocks
        paint.style = Paint.Style.FILL
        paint.color = android.graphics.Color.parseColor("#151616")
        canvas.drawRect(RectF(w * 0.15f, h * 0.38f, w * 0.85f, h * 0.48f), paint)

        // Red accent notch
        paint.color = android.graphics.Color.parseColor("#E5242A")
        canvas.drawRect(RectF(w * 0.45f, h * 0.48f, w * 0.55f, h * 0.52f), paint)

        paint.color = android.graphics.Color.parseColor("#151616")
        paint.textSize = 26f
        canvas.drawText("DEKU ARCHITECTURAL PAPER // SPEC 03", w * 0.15f, h * 0.35f, paint)
    }

    private fun renderGraphiteSlate(canvas: Canvas, w: Int, h: Int, paint: Paint) {
        canvas.drawColor(android.graphics.Color.parseColor("#191B1B"))

        // Bottom dock zone
        paint.style = Paint.Style.FILL
        paint.color = android.graphics.Color.parseColor("#252727")
        canvas.drawRoundRect(w * 0.08f, h * 0.82f, w * 0.92f, h * 0.94f, 20f, 20f, paint)

        // Center card
        paint.color = android.graphics.Color.parseColor("#202224")
        canvas.drawRect(w * 0.15f, h * 0.25f, w * 0.85f, h * 0.65f, paint)

        // Red indicator dot
        paint.color = android.graphics.Color.parseColor("#E5242A")
        canvas.drawCircle(w * 0.5f, h * 0.45f, 16f, paint)

        // Border
        paint.style = Paint.Style.STROKE
        paint.color = android.graphics.Color.parseColor("#33373A")
        paint.strokeWidth = 2f
        canvas.drawRect(w * 0.15f, h * 0.25f, w * 0.85f, h * 0.65f, paint)
    }

    fun loadBitmapFromUri(context: Context, uri: Uri): Bitmap? {
        return try {
            val input: InputStream? = context.contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(input)
            input?.close()
            bitmap
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    fun applyWallpaper(context: Context, wallpaperId: String, customUriString: String?, whichFlags: Int): Boolean {
        return try {
            val bitmap: Bitmap = if (!customUriString.isNullOrEmpty()) {
                val customUri = Uri.parse(customUriString)
                loadBitmapFromUri(context, customUri) ?: renderWallpaperBitmap(wallpaperId)
            } else {
                renderWallpaperBitmap(wallpaperId)
            }

            val wallpaperManager = WallpaperManager.getInstance(context)
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
                wallpaperManager.setBitmap(bitmap, null, true, whichFlags)
            } else {
                wallpaperManager.setBitmap(bitmap)
            }
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }

    fun shareWallpaper(context: Context, wallpaperId: String, customUriString: String?): Boolean {
        return try {
            val bitmap: Bitmap = if (!customUriString.isNullOrEmpty()) {
                val customUri = Uri.parse(customUriString)
                loadBitmapFromUri(context, customUri) ?: renderWallpaperBitmap(wallpaperId)
            } else {
                renderWallpaperBitmap(wallpaperId)
            }

            val cachePath = File(context.cacheDir, "wallpapers")
            cachePath.mkdirs()
            val file = File(cachePath, "${wallpaperId}_deku.png")
            val stream = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
            stream.flush()
            stream.close()

            val contentUri = FileProvider.getUriForFile(
                context,
                "${context.packageName}.fileprovider",
                file
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "image/png"
                putExtra(Intent.EXTRA_STREAM, contentUri)
                addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            }
            val chooser = Intent.createChooser(shareIntent, "Set via OxygenOS Photos")
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
