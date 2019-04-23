package com.example.videoview.video.controller

import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.example.videoview.video.VideoPlayer

class LoadVideoControllerDelegate(parent: VideoControllerView):
    VideoControllerDelegate(parent = parent) {

    init {
        videoPlayer.apply {
            onPrepareVideoPlayerListener = object: VideoPlayer.OnStartPrepareVideoPlayerListener {
                override fun onPrepareVideoPlayer(vp: VideoPlayer) {
                    startLoad()
                }
            }
            setOnPreparedListener {
                stopLoad()
            }
        }
    }

    private val progressLoad = ProgressBar(parent.context).apply {
        layoutParams = FrameLayout.LayoutParams(
            FrameLayout.LayoutParams.WRAP_CONTENT,
            FrameLayout.LayoutParams.WRAP_CONTENT
        ).apply {
            gravity = Gravity.CENTER
        }
        visibility = View.GONE
    }.also {
        addView(it)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val height = View.MeasureSpec.getSize(heightMeasureSpec)
        val progressLoadWidth = height / 3
        val progressLoadHeight = progressLoadWidth

        val measureProgressLoadWidth = View.MeasureSpec.makeMeasureSpec(progressLoadWidth, View.MeasureSpec.EXACTLY)
        val measureProgressLoadHeight = View.MeasureSpec.makeMeasureSpec(progressLoadHeight, View.MeasureSpec.EXACTLY)
        progressLoad.measure(measureProgressLoadWidth, measureProgressLoadHeight)
    }

    private fun startLoad() {
        progressLoad.visibility = View.VISIBLE
    }

    private fun stopLoad() {
        progressLoad.visibility = View.GONE
    }
}