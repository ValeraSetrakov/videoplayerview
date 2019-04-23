package com.example.videoview.video

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.util.Log
import android.widget.FrameLayout
import com.example.videoview.video.controller.ComplexVideoControllerView
import com.example.videoview.video.controller.LoadVideoControllerDelegate
import com.example.videoview.video.controller.LoadVideoControllerView
import com.example.videoview.video.controller.SimpleVideoControllerView

class VideoPlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        val TAG: String = VideoPlayerView::class.java.simpleName
    }

    private val videoPlayer = VideoPlayer().apply {
        setScreenOnWhilePlaying(true)
        setOnPreparedListener {
            Log.d(TAG, "Prepare video")
            start()
        }
    }

    private val videoView = VideoView(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        setBackgroundColor(Color.GREEN)
        this.videoPlayer = this@VideoPlayerView.videoPlayer
    }.also {
        addView(it)
    }

    private val videoControllerView = ComplexVideoControllerView(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        this.videoPlayer = this@VideoPlayerView.videoPlayer
    }.also {
        addView(it)
        it.addDelegate(LoadVideoControllerDelegate(it))
    }

    fun playVideo(videoPath: String) {
        videoView.playVideo(videoPath)
    }

}