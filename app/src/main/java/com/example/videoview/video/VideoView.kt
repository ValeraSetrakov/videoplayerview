package com.example.videoview.video

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout

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
            val lp = LayoutParams(200, 500)
            layoutParams = lp
            holder.addCallback(callback)
        }.also {
            addView(it)
        }
    }
}