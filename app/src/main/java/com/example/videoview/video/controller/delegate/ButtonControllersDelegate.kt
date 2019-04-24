package com.example.videoview.video.controller.delegate

import android.view.View
import android.widget.ImageView
import com.example.videoview.R
import com.example.videoview.video.VideoPlayer
import com.example.videoview.video.controller.VideoControllerView

class ButtonControllersDelegate (parent: VideoControllerView):
    VideoControllerDelegate(parent = parent) {

    init {
        videoPlayer.apply {
            addOnStartVideoPlayerListener(object : VideoPlayer.OnStartVideoPlayerListener {
                override fun onStartVideoPlayer(mp: VideoPlayer) {
                    pauseButton.visibility = View.VISIBLE
                    playButton.visibility = View.GONE
                }
            })
            addOnPauseVideoPlayerListener(object: VideoPlayer.OnPauseVideoPlayerListener {
                override fun onPauseVideoPlayer(mp: VideoPlayer) {
                    playButton.visibility = View.VISIBLE
                    pauseButton.visibility = View.GONE
                }
            })
            setOnCompletionListener {
                playButton.visibility = View.VISIBLE
                pauseButton.visibility = View.GONE
            }
        }

    }

    private val playButton: ImageView = ImageView(parent.context).apply {
        setImageResource(R.drawable.ic_play)
        setOnClickListener {
            videoPlayer.start()
        }
    }.also {
        addView(it)
    }

    private val pauseButton = ImageView(parent.context).apply {
        setImageResource(R.drawable.ic_pause)
        setOnClickListener {
            videoPlayer.pause()
        }
        visibility = View.INVISIBLE
    }.also {
        addView(it)
    }

    private val buttons = listOf(playButton, pauseButton)

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
    }

}