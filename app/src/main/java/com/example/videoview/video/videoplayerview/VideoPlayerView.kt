package com.example.videoview.video.videoplayerview

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.FrameLayout
import com.example.videoview.video.videocontrollerview.ComplexVideoControllerView
import com.example.videoview.video.videocontrollerview.delegate.ButtonControllersDelegate
import com.example.videoview.video.videocontrollerview.delegate.LoadVideoControllerDelegate
import com.example.videoview.video.videocontrollerview.delegate.ProgressControllerDelegate
import com.example.videoview.video.videoview.videoplayer.VideoPlayer
import com.example.videoview.video.videoview.VideoView

class VideoPlayerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        val TAG: String = VideoPlayerView::class.java.simpleName
    }

    private val videoPlayer = VideoPlayer().apply {
        setScreenOnWhilePlaying(true)
        setOnPreparedListener {
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

    private val videoControllerView = ComplexVideoControllerView(context)
        .apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
        this.videoPlayer = this@VideoPlayerView.videoPlayer
    }.also {
        addView(it)
        it.addDelegate(LoadVideoControllerDelegate(it))
        it.addDelegate(ProgressControllerDelegate(it))
        it.addDelegate(ButtonControllersDelegate(it))
    }

    fun playVideo(videoPath: String) {
        videoView.playVideo(videoPath)
    }

}