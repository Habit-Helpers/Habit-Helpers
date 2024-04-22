package com.example.habit_helper

// PieChartView.kt

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin

class PieChartView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    interface OnColorSegmentClickListener {
        fun onColorSegmentClick(colorLabel: String)
    }

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val chartData: MutableList<Pair<String, Float>> = mutableListOf()
    var onColorSegmentClickListener: OnColorSegmentClickListener? = null

    fun setChartData(data: List<Pair<String, Float>>) {
        chartData.clear()
        chartData.addAll(data)
        invalidate()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val centerX = width / 2f
        val centerY = height / 2f
        val radius = (width.coerceAtMost(height) / 2f) * 0.8f

        var startAngle = 0f

        // Preallocate text position variables
        var textAngle: Float
        var textX: Float
        var textY: Float

        // Draw title
        val titlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            textSize = 24f // Adjust text size as needed
            textAlign = Paint.Align.CENTER
        }
        val titleY = 40f // Adjust vertical position as needed
        canvas.drawText("Activity Breakdown", centerX, titleY, titlePaint)

        // Draw underline
        val underlinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = Color.BLACK
            strokeWidth = 2f // Adjust line thickness as needed
        }
        val underlineStartX = centerX - 100f // Adjust the start and end positions as needed
        val underlineEndX = centerX + 100f
        val underlineY = titleY + 10f // Adjust vertical position as needed
        canvas.drawLine(underlineStartX, underlineY, underlineEndX, underlineY, underlinePaint)

        chartData.forEach { (label, value) ->
            val sweepAngle = 360f * (value / getTotalValue())
            paint.color = getColorForLabel(label)
            canvas.drawArc(
                RectF(centerX - radius, centerY - radius, centerX + radius, centerY + radius),
                startAngle,
                sweepAngle,
                true,
                paint
            )

            // Calculate percentage
            val percentage = (value / getTotalValue()) * 100

            // Calculate and draw text
            textAngle = startAngle + sweepAngle / 2
            textX = centerX + radius * 0.8f * cos(Math.toRadians(textAngle.toDouble())).toFloat()
            textY = centerY + radius * 0.8f * sin(Math.toRadians(textAngle.toDouble())).toFloat()
            val text = "${label}: ${String.format("%.1f", percentage)}%"
            canvas.drawText(text, textX, textY, paint)

            startAngle += sweepAngle
        }
    }



    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_UP) {
            val angle = Math.toDegrees(
                atan2(
                    event.y.toDouble() - height / 2.toDouble(),
                    event.x.toDouble() - width / 2.toDouble()
                )
            ).toFloat()

            // Calculate the angle relative to the startAngle
            val relativeAngle = if (angle < 0) 360 + angle else angle
            var startAngle = 0f
            chartData.forEachIndexed { index, (_, value) ->
                val sweepAngle = 360f * (value / getTotalValue())
                if (relativeAngle in startAngle..(startAngle + sweepAngle)) {
                    val colorLabel = chartData[index].first
                    onColorSegmentClickListener?.onColorSegmentClick(colorLabel)
                    performClick()
                    return true
                }
                startAngle += sweepAngle
            }
        }
        return true
    }

    private fun getTotalValue(): Float {
        var totalValue = 0f
        chartData.forEach { (_, value) ->
            totalValue += value
        }
        return totalValue
    }

    private fun getColorForLabel(label: String): Int {
        // Map each label to a specific color
        return when (label) {
            "Red" -> Color.RED
            "Green" -> Color.GREEN
            "Blue" -> Color.BLUE
            "Yellow" -> Color.YELLOW
            else -> Color.BLACK // Default color
        }
    }
}
