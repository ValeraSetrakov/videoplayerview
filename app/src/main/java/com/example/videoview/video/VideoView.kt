package com.example.videoview.video

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout


private val TAG = VideoView::class.java.simpleName

class VideoView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr){

    lateinit var videoSurfaceView: VideoSurfaceView

    fun prepareDisplay (callback: VideoSurfaceView.SimpleCallback) {
        if (::videoSurfaceView.isInitialized) {
            videoSurfaceView.holder.surface.release()
            removeView(videoSurfaceView)
        }
        this.videoSurfaceView = createDisplay(callback)
    }

    private fun createDisplay(callback: VideoSurfaceView.SimpleCallback): VideoSurfaceView {
        return VideoSurfaceView(context).apply {
            val lp = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)
            layoutParams = lp
            holder.addCallback(callback)
        }.also {
            addView(it)
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        if (::videoSurfaceView.isInitialized) {
            val parentWidth = MeasureSpec.getSize(widthMeasureSpec)
            val parentHeight = MeasureSpec.getSize(heightMeasureSpec)
            var videoSurfaceWidth = videoSurfaceView.videoWidth.toFloat()
            var videoSurfaceHeight = videoSurfaceView.videoHeight.toFloat()
            if (videoSurfaceWidth > 0 && videoSurfaceHeight > 0) {
                var ratio: Float
                if (videoSurfaceWidth > videoSurfaceHeight) {
                    ratio = videoSurfaceWidth / parentWidth.toFloat()
                    videoSurfaceWidth /= ratio
                    videoSurfaceHeight /= ratio
                } else if (videoSurfaceHeight > videoSurfaceWidth) {
                    ratio = videoSurfaceHeight / parentHeight.toFloat()
                    videoSurfaceWidth /= ratio
                    videoSurfaceHeight /= ratio
                }
                videoSurfaceView.measure(MeasureSpec.makeMeasureSpec(videoSurfaceWidth.toInt(), MeasureSpec.EXACTLY),
                    MeasureSpec.makeMeasureSpec(videoSurfaceHeight.toInt(), MeasureSpec.EXACTLY))

                Log.d(TAG, """
                    On measure
                    videoSurfaceView measuredWidth ${videoSurfaceView.measuredWidth}
                    videoSurfaceView measuredHeight ${videoSurfaceView.measuredHeight}
                    videoSurfaceView surface frame ${videoSurfaceView.holder.surfaceFrame}
                """.trimIndent())

            }
        }
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (::videoSurfaceView.isInitialized) {
            val videoSurfaceWidth = videoSurfaceView.measuredWidth
            val videoSurfaceHeight = videoSurfaceView.measuredHeight

            val videoSurfaceLeft = (right - left) / 2 - videoSurfaceWidth / 2
            val videoSurfaceTop = (bottom - top) / 2 - videoSurfaceHeight / 2
            val videoSurfaceRight = videoSurfaceLeft + videoSurfaceWidth
            val videoSurfaceBottom = videoSurfaceTop + videoSurfaceHeight

            videoSurfaceView.layout(videoSurfaceLeft, videoSurfaceTop, videoSurfaceRight, videoSurfaceBottom)

            Log.d(TAG, """
                On layout
                videoSurfaceView videoSurfaceLeft $videoSurfaceLeft
                videoSurfaceView videoSurfaceTop $videoSurfaceTop
                videoSurfaceView videoSurfaceRight $videoSurfaceRight
                videoSurfaceView videoSurfaceBottom $videoSurfaceBottom
            """.trimIndent())
        }

    }

    fun setVideoSize (width: Int, height: Int) {
        videoSurfaceView.videoWidth = width
        videoSurfaceView.videoHeight = height
        videoSurfaceView.requestLayout()
    }
}