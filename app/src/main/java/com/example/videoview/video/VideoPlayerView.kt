package com.example.videoview.video

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceHolder
import android.widget.FrameLayout

class VideoPlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        val TAG: String = VideoPlayerView::class.java.simpleName
    }

    private val videoPlayer = VideoPlayer()
    private val videoView = VideoView(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
    }.also {
        addView(it)
    }

    fun playVideo(videoPath: String) {
        videoPlayer.run {
            reset()
            videoView.prepareDisplay(object: VideoSurfaceView.SimpleCallback(){
                override fun surfaceCreated(holder: SurfaceHolder?) {
                    super.surfaceCreated(holder)
                    Log.d(TAG, "Surface created")
                    setDisplay(holder)
                    setDataSource(videoPath)
                    setScreenOnWhilePlaying(true)
                    setOnPreparedListener {
                        Log.d(TAG, "Prepare completed")
                        start()
                    }
                    setOnVideoSizeChangedListener { mp, width, height ->
                        Log.d(TAG, "Size changed width $width height $height")
                    }
                    prepareAsync()
                }
            })
        }
    }

}