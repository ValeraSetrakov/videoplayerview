package com.example.videoview.video.videoview.videoplayer

import android.os.Handler
import android.os.Looper
import android.os.Message

private const val TIME_EVENT = 1

class VideoPlayer: BaseVideoPlayer() {

    private var defaultSeekMSecStep = 0

    private var onTimeVideoPlayerListeners =
        OnTimeVideoPlayerListeners()

    private val timeHandler = TimeHandler(this, Looper.getMainLooper()!!)

    init {
        setOnPreparedListener { mp ->
            val duration = mp.duration
            defaultSeekMSecStep = duration / 10
        }
        setOnCompletionListener {
            timeHandler.removeMessages(TIME_EVENT)
        }
    }

    override fun start() {
        super.start()
        timeHandler.sendEmptyMessage(TIME_EVENT)
        onStartVideoPlayerListeners.onStartVideoPlayer(this)
    }

    override fun pause() {
        super.pause()
        timeHandler.removeMessages(TIME_EVENT)
        onPauseVideoPlayerListeners.onPauseVideoPlayer(this)
    }

    override fun prepareAsync() {
        super.prepareAsync()
        onStartPrepareVideoPlayerListeners.onStartPrepareVideoPlayerListener(this)
    }

    fun seekToNext(step: Int = defaultSeekMSecStep) {
        if (duration <= currentPosition || step <= 0)
            return
        var newPosition = currentPosition + step
        if (duration - newPosition < 0)
           newPosition = duration
        super.seekTo(newPosition)
    }

    fun seekToPrev(step: Int = defaultSeekMSecStep) {
        if (currentPosition <= 0 || step <= 0)
            return
        var newPosition = currentPosition - step
        if (newPosition < 0)
            newPosition = 0
        super.seekTo(newPosition)
    }

    fun addOnTimeVideoPlayerListener (listener: OnTimeVideoPlayerListener) {
        onTimeVideoPlayerListeners.listeners.add(listener)
    }














    interface OnTimeVideoPlayerListener {
        fun onTimeVideoPlayerListener(vp: VideoPlayer)
    }

    private inner class TimeHandler(private val videoPlayer: VideoPlayer, looper: Looper): Handler(looper) {
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            msg ?: return
            when(msg.what) {
                TIME_EVENT -> {
                    onTimeVideoPlayerListeners.onTimeVideoPlayerListener(this@VideoPlayer)
                    sendMessageDelayed(obtainMessage(TIME_EVENT), 200)
                }
            }
        }
    }

    private class OnTimeVideoPlayerListeners:
        OnTimeVideoPlayerListener {
        val listeners = mutableListOf<OnTimeVideoPlayerListener>()
        override fun onTimeVideoPlayerListener(vp: VideoPlayer) {
            listeners.forEach{ it.onTimeVideoPlayerListener(vp) }
        }
    }
}