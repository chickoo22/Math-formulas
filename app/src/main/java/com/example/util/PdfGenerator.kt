package com.example.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.os.Environment
import android.widget.Toast
import com.example.data.repository.FormulaWithBookmark
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

object PdfGenerator {

    fun generateFormulaSheetPdf(context: Context, formulas: List<FormulaWithBookmark>) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create() // A4 size
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val titlePaint = Paint().apply {
            textSize = 16f
            isFakeBoldText = true
            color = Color.rgb(25, 118, 210) // Primary blue
        }
        val subPaint = Paint().apply {
            textSize = 10f
            color = Color.GRAY
        }
        val textPaint = Paint().apply {
            textSize = 11f
            color = Color.BLACK
        }
        val formulaPaint = Paint().apply {
            textSize = 11f
            isFakeBoldText = true
            color = Color.rgb(0, 102, 204)
        }

        var y = 40f
        canvas.drawText("MathVerse - Complete Formula Sheet (Std 10th & 12th)", 40f, y, titlePaint)
        y += 18f
        canvas.drawText("Generated offline for student board exam preparation", 40f, y, subPaint)
        y += 28f

        for (item in formulas) {
            if (y > 780f) break // Single page fit for summary or concise export
            canvas.drawText("• ${item.formula.title} [${item.formula.category.title}]", 40f, y, titlePaint)
            y += 16f
            canvas.drawText("   Formula: ${item.formula.formulaDisplay}", 40f, y, formulaPaint)
            y += 16f
            canvas.drawText("   Note: ${item.formula.description}", 40f, y, textPaint)
            y += 22f
        }

        pdfDocument.finishPage(page)

        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "MathVerse_Formula_Sheet.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()
            Toast.makeText(context, "Formula Sheet PDF saved to Documents!", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            pdfDocument.close()
            Toast.makeText(context, "Failed to save PDF: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }

    fun generateRootsPdf(context: Context) {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(595, 842, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas: Canvas = page.canvas

        val titlePaint = Paint().apply {
            textSize = 16f
            isFakeBoldText = true
            color = Color.rgb(56, 142, 60) // Green
        }
        val headerPaint = Paint().apply {
            textSize = 11f
            isFakeBoldText = true
            color = Color.BLACK
        }
        val rowPaint = Paint().apply {
            textSize = 10f
            color = Color.BLACK
        }

        var y = 40f
        canvas.drawText("MathVerse - Square & Cube Roots Table (1–50)", 40f, y, titlePaint)
        y += 25f

        canvas.drawText(String.format(java.util.Locale.US, "%-4s | %-12s | %-15s | %-10s | %-10s", "N", "Square (N²)", "Square Root (√N)", "Cube (N³)", "Cube Root (∛N)"), 40f, y, headerPaint)
        y += 18f

        for (n in 1..35) {
            val sq = n * n
            val sqrtVal = kotlin.math.sqrt(n.toDouble())
            val cb = n * n * n
            val cbrtVal = kotlin.math.cbrt(n.toDouble())
            val line = String.format(java.util.Locale.US, "%-4d | %-12d | %-15.4f | %-10d | %.4f", n, sq, sqrtVal, cb, cbrtVal)
            canvas.drawText(line, 40f, y, rowPaint)
            y += 16f
        }

        pdfDocument.finishPage(page)

        val file = File(context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS), "MathVerse_Roots_Table.pdf")
        try {
            pdfDocument.writeTo(FileOutputStream(file))
            pdfDocument.close()
            Toast.makeText(context, "Roots Table PDF saved to Documents!", Toast.LENGTH_LONG).show()
        } catch (e: IOException) {
            e.printStackTrace()
            pdfDocument.close()
            Toast.makeText(context, "Failed to save PDF: ${e.localizedMessage}", Toast.LENGTH_SHORT).show()
        }
    }
}
