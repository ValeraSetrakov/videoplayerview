package com.example.videoview.video.videocontrollerview.delegate

import android.view.View
import android.widget.ImageView
import com.example.videoview.R
import com.example.videoview.video.videoview.videoplayer.BaseVideoPlayer
import com.example.videoview.video.videoview.videoplayer.VideoPlayer
import com.example.videoview.video.videocontrollerview.VideoControllerView

class ButtonControllersDelegate (parent: VideoControllerView):
    VideoControllerDelegate(parent = parent) {

    private val context = parent.context
    private var videoStep = 0

    init {
        videoPlayer.apply {
            addOnStartVideoPlayerListener(object : BaseVideoPlayer.OnStartVideoPlayerListener {
                override fun onStartVideoPlayer(mp: VideoPlayer) {
                    pauseButton.visibility = View.VISIBLE
                    playButton.visibility = View.GONE
                }
            })
            addOnPauseVideoPlayerListener(object: BaseVideoPlayer.OnPauseVideoPlayerListener {
                override fun onPauseVideoPlayer(mp: VideoPlayer) {
                    playButton.visibility = View.VISIBLE
                    pauseButton.visibility = View.GONE
                }
            })
            setOnCompletionListener {
                playButton.visibility = View.VISIBLE
                pauseButton.visibility = View.GONE
            }
            setOnPreparedListener {
                val duration = it.duration
                videoStep = duration / 10
            }
        }

    }

    private val playButton: ImageView = ImageView(context).apply {
        setImageResource(R.drawable.ic_play)
        setOnClickListener {
            videoPlayer.start()
        }
    }.also {
        addView(it)
    }

    private val pauseButton = ImageView(context).apply {
        setImageResource(R.drawable.ic_pause)
        setOnClickListener {
            videoPlayer.pause()
        }
        visibility = View.INVISIBLE
    }.also {
        addView(it)
    }

    private val nextButton = ImageView(context).apply {
        setImageResource(R.drawable.ic_next)
        setOnClickListener {
            videoPlayer.seekToNext()
        }
    }.also {
        addView(it)
    }

    private val prevButton = ImageView(context).apply {
        setImageResource(R.drawable.ic_previous)
        setOnClickListener {
            videoPlayer.seekToPrev()
        }
    }.also {
        addView(it)
    }

    private val buttons = listOf(playButton, pauseButton, nextButton, prevButton)

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = View.MeasureSpec.getSize(heightMeasureSpec)

        val buttonsHeight = height / 4
        val buttonsWidth = buttonsHeight

        for (button in buttons) {
            val child = button
            val childWidth = buttonsWidth
            val childHeight = buttonsHeight
            child.measure(View.MeasureSpec.makeMeasureSpec(childWidth, View.MeasureSpec.EXACTLY), View.MeasureSpec.makeMeasureSpec(childHeight, View.MeasureSpec.EXACTLY))
        }
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

        val prevButtonWidth = prevButton.measuredWidth
        val prevButtonHeight = prevButton.measuredHeight

        val prevButtonLeft = centerFirstQuarterX - prevButtonWidth / 2
        val prevButtonTop = centerY - prevButtonHeight / 2
        val prevButtonRight = prevButtonLeft + prevButtonWidth
        val prevButtonBottom =  prevButtonTop + prevButtonHeight

        prevButton.layout(prevButtonLeft, prevButtonTop, prevButtonRight, prevButtonBottom)

        val nextButtonWidth = nextButton.measuredWidth
        val nextButtonHeight = nextButton.measuredHeight

        val nextButtonLeft = centerFirthQuarterX - nextButtonWidth / 2
        val nextButtonTop = centerY - nextButtonHeight / 2
        val nextButtonRight = nextButtonLeft + nextButtonWidth
        val nextButtonBottom =  nextButtonTop + nextButtonHeight

        nextButton.layout(nextButtonLeft.toInt(), nextButtonTop, nextButtonRight.toInt(), nextButtonBottom)
    }

}