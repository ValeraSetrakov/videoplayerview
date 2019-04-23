package com.example.videoview.video.controller

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.videoview.R
import com.example.videoview.video.VideoPlayer
import com.example.videoview.video.VideoProgressView

private val TAG = SimpleVideoControllerView::class.java.simpleName

class SimpleVideoControllerView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : VideoControllerView(context, attrs, defStyleAttr) {

//    override var videoPlayer = VideoPlayer()
//    set(value) {
//        field = value
//        field.run {
//            onPrepareVideoPlayerListener = object:
//                VideoPlayer.OnStartPrepareVideoPlayerListener {
//                override fun onPrepareVideoPlayer(vp: VideoPlayer) {
//                    startLoad()
//                }
//            }
//            setOnPreparedListener {
//                stopLoad()
//                progressBar.max = it.duration.toFloat()
//                Log.d(TAG, "Duration of video ${progressBar.max}")
//            }
//            onStartVideoPlayerListener = object: VideoPlayer.OnStartVideoPlayerListener {
//                override fun onStartVideoPlayer(mp: VideoPlayer) {
//
//                }
//            }
//            onPauseVideoPlayerListener = object: VideoPlayer.OnPauseVideoPlayerListener {
//                override fun onPauseVideoPlayer(mp: VideoPlayer) {
//
//                }
//            }
//            setOnCompletionListener {
//
//            }
//            setOnBufferingUpdateListener { mp, percent ->
//                progressBar.buffer = percent.toFloat()
//                Log.d(TAG,"Current buffer of playing video ${progressBar.buffer}")
//            }
//            onTimeVideoPlayerListener = object: VideoPlayer.OnTimeVideoPlayerListener {
//                override fun onTimeVideoPlayerListener(vp: VideoPlayer) {
//                    progressBar.current = vp.currentPosition.toFloat()
//                    Log.d(TAG,"Current position of playing video ${progressBar.current}")
//                }
//            }
//        }
//    }

    private val playButton: ImageView = ImageView(context).apply {
        setImageResource(R.drawable.ic_play)
        setOnClickListener {
            videoPlayer.start()
            pauseButton.visibility = View.VISIBLE
            it.visibility = View.GONE
        }
    }.also {
        addView(it)
    }

    private val pauseButton = ImageView(context).apply {
        setImageResource(R.drawable.ic_pause)
        setOnClickListener {
            videoPlayer.pause()
            playButton.visibility = View.VISIBLE
            it.visibility = View.GONE
        }
        visibility = View.INVISIBLE
    }.also {
        addView(it)
    }

    private val progressBar = VideoProgressView(context).apply {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }.also {
        addView(it)
    }

    private val progressLoad = ProgressBar(context).apply {
        layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT).apply {
            gravity = Gravity.CENTER
        }
        visibility = View.GONE
    }.also {
        addView(it)
    }

    private val buttons = listOf(playButton, pauseButton)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)

        val buttonsHeight = height / 4
        val buttonsWidth = buttonsHeight

        for (button in buttons) {
            val child = button
            val childWidth = buttonsWidth
            val childHeight = buttonsHeight
            child.measure(MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(childHeight, MeasureSpec.EXACTLY))
        }

        val progressBarHeight = 30
        val progressBarWidth = width

        progressBar.measure(MeasureSpec.makeMeasureSpec(progressBarWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(progressBarHeight, MeasureSpec.EXACTLY))

        val progressLoadWidth = height / 3
        val progressLoadHeight = progressLoadWidth

        progressLoad.measure(MeasureSpec.makeMeasureSpec(progressLoadWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(progressLoadHeight, MeasureSpec.EXACTLY))
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)

        val width = right - left
        val height = bottom - top

        val centerX = width / 2
        val centerY = height / 2

        val centerFirstQuarterX = centerX / 2
        val centerFirstQuarterY = centerY / 2
        val centerFirthQuarterX = width * 0.75
        val centerFirthQuarterY = (centerY + centerFirstQuarterY) / 2

        val playButtonWidth = playButton.measuredWidth
        val playButtonHeight = playButton.measuredHeight

        val playButtonLeft = centerX - playButtonWidth / 2
        val playButtonTop = centerY - playButtonHeight / 2
        val playButtonRight = playButtonLeft + playButtonWidth
        val playButtonBottom = playButtonTop + playButtonHeight

        playButton.layout(playButtonLeft, playButtonTop, playButtonRight, playButtonBottom)

        val pauseButtonLeft = playButtonLeft
        val pauseButtonTop = playButtonTop
        val pauseButtonRight = playButtonRight
        val pauseButtonBottom = playButtonBottom

        pauseButton.layout(pauseButtonLeft, pauseButtonTop, pauseButtonRight, pauseButtonBottom)

        val progressBarWidth = progressBar.measuredWidth
        val progressBarHeight = progressBar.measuredHeight

        val progressBarLeft = 0
        val progressBarTop = height - progressBarHeight
        val progressBarRight = progressBarLeft + progressBarWidth
        val progressBarBottom =  progressBarTop + progressBarHeight

        progressBar.layout(progressBarLeft, progressBarTop, progressBarRight.toInt(), progressBarBottom)

    }



    private fun startLoad() {
        progressLoad.visibility = View.VISIBLE
    }

    private fun stopLoad() {
        progressLoad.visibility = View.GONE
    }
}