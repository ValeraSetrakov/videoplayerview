package com.example.videoview.video.controller

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.widget.ProgressBar
import com.example.videoview.video.videocontrollerview.VideoControllerView

open class LoadVideoControllerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : VideoControllerView(context, attrs, defStyleAttr) {

//    override var videoPlayer: VideoPlayer
//        get() = super.videoPlayer
//        set(value) {
//            super.videoPlayer = value.apply {
//                onPrepareVideoPlayerListener = object: VideoPlayer.OnStartPrepareVideoPlayerListener {
//                    override fun onPrepareVideoPlayer(vp: VideoPlayer) {
//                        startLoad()
//                    }
//                }
//                setOnPreparedListener {
//                    stopLoad()
//                }
//            }
//        }

    private val progressLoad = ProgressBar(context).apply {
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.CENTER
        }
        visibility = View.GONE
    }.also {
        addView(it)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val progressLoadWidth = height / 3
        val progressLoadHeight = progressLoadWidth

        progressLoad.measure(MeasureSpec.makeMeasureSpec(progressLoadWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(progressLoadHeight, MeasureSpec.EXACTLY))
    }

    private fun startLoad() {
        progressLoad.visibility = View.VISIBLE
    }

    private fun stopLoad() {
        progressLoad.visibility = View.GONE
    }
}