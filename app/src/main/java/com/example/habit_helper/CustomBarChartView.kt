// CustomLineChartView.kt
package com.example.habit_helper

import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View

class CustomBarChartView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var goals: List<Goal> = emptyList()
    private var goalStatusData: MutableMap<String, Int> = mutableMapOf()
    private val barPaints: Map<String, Paint> = mapOf(
        "In Progress" to Paint().apply { color = Color.BLUE },
        "Completed" to Paint().apply { color = Color.GREEN },
        "To Do" to Paint().apply { color = Color.RED }
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
    private val textPaint: Paint = Paint().apply {
        color = Color.BLACK
        textSize = 30f
        textAlign = Paint.Align.CENTER
    }

    private val padding = 40f
    private var barWidth = 0f

    private var startX = padding
    private var startY = 0f
    private var endX = 0f
    private var endY = 0f

    private var selectedStatus: String? = null

    private var onBarClickListener: OnBarClickListener? = null

    init {
        log("CustomBarChartView initialized")
    }

    fun setOnBarClickListener(listener: OnBarClickListener) {
        onBarClickListener = listener
    }

    fun setGoals(data: List<Goal>) {
        goals = data
        calculateGoalStatusData()
        invalidate()
    }


    private fun calculateGoalStatusData() {
        goalStatusData.clear()
        goals.forEach { goal ->
            val status = goal.status
            val count = goalStatusData.getOrDefault(status, 0)
            goalStatusData[status] = count + 1
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        log("onDraw called")

        val width = measuredWidth
        val height = measuredHeight
        val chartWidth = width - padding * 2
        val chartHeight = height - padding * 3

        barWidth = chartWidth / (goalStatusData.size * 2).toFloat()

        startX = padding + barWidth
        goalStatusData.forEach { (status, count) ->
            val barHeight = chartHeight * (count.toFloat() / goals.size)
            val rectLeft = startX - barWidth / 2
            val rectRight = startX + barWidth / 2
            val rectTop = height - padding * 2 - barHeight
            val rectBottom = height - padding * 2
            canvas.drawRect(rectLeft, rectTop, rectRight, rectBottom, barPaints[status] ?: axisPaint)

            canvas.drawText(status, startX, rectTop - 20f, titlePaint)
            canvas.drawText(count.toString(), startX, rectBottom + 40f, textPaint)

            if (status == selectedStatus) {
                startY = height - padding * 2 - barHeight
                endX = startX + barWidth
                endY = height.toFloat()
            }

            startX += barWidth * 2
        }

        // Draw title and underline
        val titleX = width / 2f
        val titleY = padding + titlePaint.textSize
        canvas.drawText("Goal Status", titleX, titleY, titlePaint)

        // Calculate the position of the underline
        val titleWidth = titlePaint.measureText("Goal Status")
        val underlineStartX = titleX - titleWidth / 2
        val underlineEndX = titleX + titleWidth / 2

        // Draw the underline
        canvas.drawLine(underlineStartX, titleY + 10, underlineEndX, titleY + 10, axisPaint)

        // Draw axis lines
        canvas.drawLine(padding, padding, padding, height - padding * 2, axisPaint)
        canvas.drawLine(padding, height - padding * 2, width - padding, height - padding * 2, axisPaint)

        startX = padding + barWidth
        goalStatusData.keys.forEach { status ->
            canvas.drawText(status, startX, height - padding / 4, textPaint)
            startX += barWidth * 2
        }

        if (selectedStatus != null) {
            canvas.drawRect(startX, startY, endX, endY, Paint().apply { color = Color.parseColor("#80000000") })
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val x = event.x
                val y = event.y

                Log.d(TAG, "Touch coordinates: x=$x, y=$y")

                startX = padding + barWidth / 2 // Adjusted to start from the center of the first bar
                goalStatusData.forEach { (status, _) ->
                    endX = startX + barWidth
                    startY = height - padding * 2
                    endY = height.toFloat()

                    Log.d(TAG, "Bar $status coordinates: startX=$startX, endX=$endX, startY=$startY, endY=$endY")

                    if (x in startX..endX && y >= startY && y <= endY) {
                        selectedStatus = status
                        invalidate()
                        onBarClickListener?.onBarClicked(status)
                        performClick() // Call performClick when a bar is clicked
                        Log.d(TAG, "Bar clicked: $status")

                        return true
                    }

                    startX += barWidth * 2
                }
            }
        }
        return super.onTouchEvent(event)
    }



    interface OnBarClickListener {
        fun onBarClicked(status: String) {
            Log.d(TAG, "onBarClicked: $status")
        }
    }


    override fun performClick(): Boolean {
        super.performClick()
        Log.d(TAG, "performClick() called")

        return true
    }

    private fun log(message: String) {
        Log.d("CustomBarChartView", message)
    }
}















