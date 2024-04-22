// CustomLineChartView.kt

package com.example.habit_helper

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class CustomBarChartView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var goalStatusData: Map<String, Int> = emptyMap()
    private val barPaints: Map<String, Paint> = mapOf(
        "In Progress" to Paint().apply { color = Color.BLUE },
        "Completed" to Paint().apply { color = Color.GREEN },
        "Pending" to Paint().apply { color = Color.RED }
    )
    private val axisPaint: Paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 2f
        style = Paint.Style.STROKE
    }
    private val titlePaint: Paint = Paint().apply {
        color = Color.BLACK
        textSize = 40f
        textAlign = Paint.Align.CENTER
    }
    private val underlinePaint: Paint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 4f
    }

    private val padding = 40f // Add padding as a member variable

    fun setGoalStatusData(data: Map<String, Int>) {
        goalStatusData = data
        invalidate() // Redraw the chart
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        goalStatusData.let { data ->
            // Define the dimensions of the chart
            val width = measuredWidth
            val height = measuredHeight
            val chartWidth = width - padding * 2
            val chartHeight = height - padding * 3 // Adjusted for additional space

            // Initialize startX and barWidth
            var startX = padding
            val barWidth = chartWidth / data.size.toFloat()

            data.forEach { (status, count) ->
                val paint = barPaints[status]
                paint?.let {
                    val barHeight = chartHeight * (count.toFloat() / data.values.sum())
                    val rectLeft = startX
                    val rectRight = startX + barWidth
                    val rectTop = height - padding * 2 - barHeight // Adjust the top position
                    val rectBottom = height - padding * 2
                    canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, it)
                    startX += barWidth
                }
            }
        }

        // Draw chart title
        val chartTitle = "Goal Status Chart"
        val chartTitleX = width / 2f
        val chartTitleY = padding - 20f // Adjust vertical position
        canvas.drawText(chartTitle, chartTitleX, chartTitleY, titlePaint)

        // Draw underline for chart title
        val underlineStartX = chartTitleX - titlePaint.measureText(chartTitle) / 2
        val underlineEndX = chartTitleX + titlePaint.measureText(chartTitle) / 2
        val underlineY = chartTitleY + 20f // Adjust the vertical position of the underline
        canvas.drawLine(underlineStartX, underlineY, underlineEndX, underlineY, underlinePaint)
    }
}









