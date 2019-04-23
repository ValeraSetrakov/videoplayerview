package com.example.videoview.video

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View

class VideoProgressView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    var max = 0f
        set(value) {
            field = value
            part = measuredWidth / field
            calculateAllRects()
            invalidate()
        }
    var buffer = 0f
        set(value) {
            field = value
            calculateBufferRect()
            calculateMaxRect()
            invalidate()
        }
    var current = 0f
        set(value) {
            field = value
            calculateAllRects()
            invalidate()
        }
    var part = 0f

    private val currentRect = Rect()
    private val bufferRect = Rect()
    private val maxRect = Rect()

    private val currentPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.RED
    }

    private val bufferPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.GRAY
    }

    private val maxPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        color = Color.BLUE
    }

    private fun calculateAllRects() {
        calculateCurrentRect()
        calculateBufferRect()
        calculateMaxRect()
    }

    private fun calculateCurrentRect() {
        val currentRectLeft = 0
        val currentRectTop = 0
        val currentRectRight = part * current
        val currentRectBottom = height

        currentRect.set(currentRectLeft, currentRectTop, currentRectRight.toInt(), currentRectBottom)
    }

    private fun calculateBufferRect() {
        val bufferRectLeft = 0
        val bufferRectTop = 0
        val bufferRectRight = width * (buffer / 100)
        val bufferRectBottom = height

        bufferRect.set(bufferRectLeft, bufferRectTop, bufferRectRight.toInt(), bufferRectBottom)
    }

    private fun calculateMaxRect() {
        val maxRectLeft = 0
        val maxRectTop = 0
        val maxRectRight = width
        val maxRectBottom = height

        maxRect.set(maxRectLeft, maxRectTop, maxRectRight, maxRectBottom)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.run {
            drawRect(maxRect, maxPaint)
            drawRect(bufferRect, bufferPaint)
            drawRect(currentRect, currentPaint)
        }
    }
}