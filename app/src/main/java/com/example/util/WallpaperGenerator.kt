package com.example.util

import android.app.WallpaperManager
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.RectF
import android.net.Uri
import androidx.core.content.FileProvider
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

object WallpaperGenerator {

    const val TARGET_WIDTH = 1080
    const val TARGET_HEIGHT = 2400

    fun renderWallpaperBitmap(
        wallpaperId: String,
        width: Int = TARGET_WIDTH,
        height: Int = TARGET_HEIGHT
    ): Bitmap {
        val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        val paint = Paint(Paint.ANTI_ALIAS_FLAG)

        when (wallpaperId) {
            "deku_monolith" -> renderDekuMonolith(canvas, width, height, paint)
            "ios_aurora" -> renderIosAurora(canvas, width, height, paint)
            "pixel_sand" -> renderPixelSand(canvas, width, height, paint)
            "samsung_fluid" -> renderSamsungFluid(canvas, width, height, paint)
            "amoled_monolith" -> renderAmoledMonolith(canvas, width, height, paint)
            "cyber_grid" -> renderCyberGrid(canvas, width, height, paint)
            "minimal_paper" -> renderMinimalPaper(canvas, width, height, paint)
            "nature_pines" -> renderNaturePines(canvas, width, height, paint)
            "anime_slash" -> renderAnimeSlash(canvas, width, height, paint)
            "luxury_gold" -> renderLuxuryGold(canvas, width, height, paint)
            "retro_crt" -> renderRetroCrt(canvas, width, height, paint)
            else -> renderDekuMonolith(canvas, width, height, paint)
        }

        return bitmap
    }

    private fun renderDekuMonolith(canvas: Canvas, w: Int, h: Int, paint: Paint) {
        canvas.drawColor(Color.parseColor("#080909"))

        paint.color = Color.parseColor("#222424")
        paint.style = Paint.Style.FILL
        val stepX = w / 16f
        val stepY = h / 32f
        for (i in 1..15) {
            for (j in 1..31) {
                canvas.drawCircle(i * stepX, j * stepY, 2.5f, paint)
            }
        }

        paint.color = Color.parseColor("#141616")
        val plate = Path().apply {
            moveTo(w * 0.1f, h * 0.32f)
            lineTo(w * 0.9f, h * 0.26f)
            lineTo(w * 0.9f, h * 0.68f)
            lineTo(w * 0.1f, h * 0.74f)
            close()
        }
        canvas.drawPath(plate, paint)

        paint.color = Color.parseColor("#E5242A")
        val redline = Path().apply {
            moveTo(w * 0.18f, h * 0.45f)
            lineTo(w * 0.85f, h * 0.38f)
            lineTo(w * 0.82f, h * 0.40f)
            lineTo(w * 0.15f, h * 0.47f)
            close()
        }
        canvas.drawPath(redline, paint)

        paint.color = Color.parseColor("#F2F2EE")
        paint.strokeWidth = 3f
        paint.style = Paint.Style.STROKE
        canvas.drawLine(w * 0.15f, h * 0.47f, w * 0.85f, h * 0.58f, paint)

        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#F2F2EE")
        canvas.drawCircle(w * 0.5f, h * 0.52f, 12f, paint)
        paint.color = Color.parseColor("#E5242A")
        canvas.drawCircle(w * 0.5f, h * 0.52f, 5f, paint)
    }

    private fun renderIosAurora(canvas: Canvas, w: Int, h: Int, paint: Paint) {
        canvas.drawColor(Color.parseColor("#050508"))

        // Glowing organic ellipses
        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#182A4D")
        canvas.drawCircle(w * 0.7f, h * 0.35f, w * 0.55f, paint)

        paint.color = Color.parseColor("#0B3954")
        canvas.drawCircle(w * 0.3f, h * 0.65f, w * 0.6f, paint)

        paint.color = Color.parseColor("#0A84FF")
        paint.alpha = 110
        canvas.drawCircle(w * 0.5f, h * 0.5f, w * 0.3f, paint)

        paint.alpha = 255
        paint.color = Color.parseColor("#282830")
        paint.strokeWidth = 2f
        paint.style = Paint.Style.STROKE
        canvas.drawRoundRect(RectF(w * 0.1f, h * 0.2f, w * 0.9f, h * 0.8f), 48f, 48f, paint)
    }

    private fun renderPixelSand(canvas: Canvas, w: Int, h: Int, paint: Paint) {
        canvas.drawColor(Color.parseColor("#111414"))

        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#1D2524")
        val path1 = Path().apply {
            moveTo(0f, h * 0.3f)
            cubicTo(w * 0.4f, h * 0.25f, w * 0.7f, h * 0.45f, w.toFloat(), h * 0.4f)
            lineTo(w.toFloat(), h.toFloat())
            lineTo(0f, h.toFloat())
            close()
        }
        canvas.drawPath(path1, paint)

        paint.color = Color.parseColor("#E06D53")
        paint.alpha = 200
        canvas.drawCircle(w * 0.75f, h * 0.3f, 48f, paint)
        paint.alpha = 255

        paint.color = Color.parseColor("#24302F")
        val path2 = Path().apply {
            moveTo(0f, h * 0.6f)
            cubicTo(w * 0.3f, h * 0.55f, w * 0.6f, h * 0.75f, w.toFloat(), h * 0.65f)
            lineTo(w.toFloat(), h.toFloat())
            lineTo(0f, h.toFloat())
            close()
        }
        canvas.drawPath(path2, paint)
    }

    private fun renderSamsungFluid(canvas: Canvas, w: Int, h: Int, paint: Paint) {
        canvas.drawColor(Color.parseColor("#0F1012"))

        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#1A1D24")
        canvas.drawRoundRect(RectF(w * 0.12f, h * 0.25f, w * 0.88f, h * 0.65f), 64f, 64f, paint)

        paint.color = Color.parseColor("#4F46E5")
        canvas.drawCircle(w * 0.5f, h * 0.45f, w * 0.18f, paint)

        paint.color = Color.parseColor("#0F1012")
        canvas.drawCircle(w * 0.5f, h * 0.45f, w * 0.12f, paint)

        paint.color = Color.parseColor("#F3F4F6")
        paint.strokeWidth = 3f
        paint.style = Paint.Style.STROKE
        canvas.drawRoundRect(RectF(w * 0.2f, h * 0.32f, w * 0.8f, h * 0.58f), 32f, 32f, paint)
    }

    private fun renderAmoledMonolith(canvas: Canvas, w: Int, h: Int, paint: Paint) {
        // True black 100%
        canvas.drawColor(Color.BLACK)

        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#222222")
        paint.strokeWidth = 2f
        canvas.drawRect(RectF(w * 0.15f, h * 0.22f, w * 0.85f, h * 0.78f), paint)

        paint.color = Color.parseColor("#444444")
        paint.strokeWidth = 1f
        canvas.drawLine(w * 0.5f, h * 0.22f, w * 0.5f, h * 0.78f, paint)
        canvas.drawLine(w * 0.15f, h * 0.5f, w * 0.85f, h * 0.5f, paint)

        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        canvas.drawCircle(w * 0.5f, h * 0.5f, 10f, paint)
    }

    private fun renderCyberGrid(canvas: Canvas, w: Int, h: Int, paint: Paint) {
        canvas.drawColor(Color.parseColor("#090A0E"))

        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#152233")
        paint.strokeWidth = 2f
        val step = w / 8f
        for (i in 0..8) {
            canvas.drawLine(i * step, 0f, i * step, h.toFloat(), paint)
        }
        val vStep = h / 16f
        for (j in 0..16) {
            canvas.drawLine(0f, j * vStep, w.toFloat(), j * vStep, paint)
        }

        // Electric cyan horizon
        paint.color = Color.parseColor("#00F0FF")
        paint.strokeWidth = 4f
        canvas.drawLine(0f, h * 0.55f, w.toFloat(), h * 0.55f, paint)

        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#FF0055")
        canvas.drawRect(RectF(w * 0.25f, h * 0.45f, w * 0.75f, h * 0.47f), paint)
    }

    private fun renderMinimalPaper(canvas: Canvas, w: Int, h: Int, paint: Paint) {
        canvas.drawColor(Color.parseColor("#F2EFE9"))

        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#E0DCD4")
        paint.strokeWidth = 1.5f
        val step = w / 10f
        for (i in 0..10) {
            canvas.drawLine(i * step, 0f, i * step, h.toFloat(), paint)
        }
        val vStep = h / 20f
        for (j in 0..20) {
            canvas.drawLine(0f, j * vStep, w.toFloat(), j * vStep, paint)
        }

        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#181816")
        canvas.drawRect(RectF(w * 0.2f, h * 0.35f, w * 0.8f, h * 0.45f), paint)

        paint.color = Color.parseColor("#BF4E30")
        canvas.drawCircle(w * 0.5f, h * 0.58f, 22f, paint)
    }

    private fun renderNaturePines(canvas: Canvas, w: Int, h: Int, paint: Paint) {
        canvas.drawColor(Color.parseColor("#0C1410"))

        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#15201A")
        val mountain1 = Path().apply {
            moveTo(0f, h * 0.55f)
            lineTo(w * 0.45f, h * 0.32f)
            lineTo(w.toFloat(), h * 0.6f)
            lineTo(w.toFloat(), h.toFloat())
            lineTo(0f, h.toFloat())
            close()
        }
        canvas.drawPath(mountain1, paint)

        paint.color = Color.parseColor("#1E2E25")
        val mountain2 = Path().apply {
            moveTo(w * 0.2f, h * 0.65f)
            lineTo(w * 0.75f, h * 0.42f)
            lineTo(w.toFloat(), h * 0.55f)
            lineTo(w.toFloat(), h.toFloat())
            lineTo(0f, h.toFloat())
            close()
        }
        canvas.drawPath(mountain2, paint)

        paint.color = Color.parseColor("#52B788")
        canvas.drawCircle(w * 0.75f, h * 0.28f, 32f, paint)
    }

    private fun renderAnimeSlash(canvas: Canvas, w: Int, h: Int, paint: Paint) {
        canvas.drawColor(Color.parseColor("#0A0A0B"))

        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#18181C")
        val slash = Path().apply {
            moveTo(0f, h * 0.2f)
            lineTo(w.toFloat(), h * 0.45f)
            lineTo(w.toFloat(), h * 0.65f)
            lineTo(0f, h * 0.4f)
            close()
        }
        canvas.drawPath(slash, paint)

        paint.color = Color.parseColor("#FF2A4B")
        paint.strokeWidth = 8f
        paint.style = Paint.Style.STROKE
        canvas.drawLine(w * 0.1f, h * 0.25f, w * 0.9f, h * 0.5f, paint)

        paint.style = Paint.Style.FILL
        paint.color = Color.WHITE
        canvas.drawCircle(w * 0.5f, h * 0.375f, 16f, paint)
    }

    private fun renderLuxuryGold(canvas: Canvas, w: Int, h: Int, paint: Paint) {
        canvas.drawColor(Color.parseColor("#0B0B0C"))

        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#D4AF37")
        paint.strokeWidth = 2.5f

        canvas.drawRect(RectF(w * 0.15f, h * 0.25f, w * 0.85f, h * 0.75f), paint)
        canvas.drawRect(RectF(w * 0.18f, h * 0.27f, w * 0.82f, h * 0.73f), paint)

        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#D4AF37")
        canvas.drawCircle(w * 0.5f, h * 0.5f, 18f, paint)
    }

    private fun renderRetroCrt(canvas: Canvas, w: Int, h: Int, paint: Paint) {
        canvas.drawColor(Color.parseColor("#0E0C08"))

        // Amber CRT scanlines
        paint.style = Paint.Style.STROKE
        paint.color = Color.parseColor("#262013")
        paint.strokeWidth = 2f
        val lineSpacing = 16f
        var y = 0f
        while (y < h) {
            canvas.drawLine(0f, y, w.toFloat(), y, paint)
            y += lineSpacing
        }

        paint.color = Color.parseColor("#FFB000")
        paint.strokeWidth = 4f
        canvas.drawRect(RectF(w * 0.15f, h * 0.35f, w * 0.85f, h * 0.65f), paint)

        paint.style = Paint.Style.FILL
        paint.color = Color.parseColor("#FFB000")
        paint.textSize = 36f
        canvas.drawText("SYSTEM READY >_", w * 0.22f, h * 0.51f, paint)
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
            val chooser = Intent.createChooser(shareIntent, "Set via Device Photos")
            chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(chooser)
            true
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
