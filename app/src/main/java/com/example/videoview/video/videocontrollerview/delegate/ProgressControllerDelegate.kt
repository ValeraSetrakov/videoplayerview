package com.example.videoview.video.videocontrollerview.delegate

import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.example.videoview.video.videoview.videoplayer.VideoPlayer
import com.example.videoview.video.VideoProgressView
import com.example.videoview.video.videocontrollerview.VideoControllerView

private val TAG = ProgressControllerDelegate::class.java.simpleName

class ProgressControllerDelegate(parent: VideoControllerView):
    VideoControllerDelegate(parent = parent) {

    init {
        videoPlayer.apply {
            setOnPreparedListener {
                progressBar.max = it.duration.toFloat()
            }
            setOnBufferingUpdateListener { mp, percent ->
                progressBar.buffer = percent.toFloat()
            }
            addOnTimeVideoPlayerListener(object: VideoPlayer.OnTimeVideoPlayerListener {
                override fun onTimeVideoPlayerListener(vp: VideoPlayer) {
                    progressBar.current = vp.currentPosition.toFloat()
                }
            })
            setOnCompletionListener {
                progressBar.current = progressBar.max
            }
            setOnSeekCompleteListener {
                Log.d(TAG, "Current position after seek ${it.currentPosition}")
            }
        }
    }

    private val progressBar = VideoProgressView(parent.context).apply {
        layoutParams =
            FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.WRAP_CONTENT)
    }.also {
        addView(it)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val progressBarHeight = 15
        val progressBarWidth = width

        val measureProgressBarWidth = View.MeasureSpec.makeMeasureSpec(progressBarWidth, View.MeasureSpec.EXACTLY)
        val measureProgressBarHeight = View.MeasureSpec.makeMeasureSpec(progressBarHeight, View.MeasureSpec.EXACTLY)

        progressBar.measure(measureProgressBarWidth, measureProgressBarHeight)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val height = bottom - top

        val progressBarWidth = progressBar.measuredWidth
        val progressBarHeight = progressBar.measuredHeight

        val progressBarLeft = 0
        val progressBarTop = height - progressBarHeight
        val progressBarRight = progressBarLeft + progressBarWidth
        val progressBarBottom =  progressBarTop + progressBarHeight

        progressBar.layout(progressBarLeft, progressBarTop, progressBarRight.toInt(), progressBarBottom)
    }

}