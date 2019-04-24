package com.example.videoview.video

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.os.Message

private const val TIME_EVENT = 1

class VideoPlayer: MediaPlayer() {

    private val onStartVideoPlayerListeners = OnStartVideoPlayerListeners()
    private var onStartPrepareVideoPlayerListeners = OnStartPrepareVideoPlayerListeners()
    private var onPauseVideoPlayerListeners = OnPauseVideoPlayerListeners()
    private var onTimeVideoPlayerListeners = OnTimeVideoPlayerListeners()

    private val onPrepareListeners = OnPrepareListeners().also {
        super.setOnPreparedListener(it)
    }
    private val onCompletionListeners = OnCompletionListeners().also {
        it.listeners.add(OnCompletionListener {
            timeHandler.removeMessages(TIME_EVENT)
        })
        super.setOnCompletionListener(it)
    }

    private val timeHandler = TimeHandler(this, Looper.getMainLooper()!!)

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

    override fun setOnCompletionListener(listener: OnCompletionListener?) {
        listener ?: return
        onCompletionListeners.listeners.add(listener)
    }

    override fun setOnPreparedListener(listener: OnPreparedListener?) {
        listener ?: return
        onPrepareListeners.listeners.add(listener)
    }

    fun addOnStartVideoPlayerListener (listener: OnStartVideoPlayerListener) {
        onStartVideoPlayerListeners.listeners.add(listener)
    }

    fun addOnStartPrepareVideoPlayerListener (listener: OnStartPrepareVideoPlayerListener) {
        onStartPrepareVideoPlayerListeners.listeners.add(listener)
    }

    fun addOnPauseVideoPlayerListener (listener: OnPauseVideoPlayerListener) {
        onPauseVideoPlayerListeners.listeners.add(listener)
    }

    fun addOnTimeVideoPlayerListener (listener: OnTimeVideoPlayerListener) {
        onTimeVideoPlayerListeners.listeners.add(listener)
    }





    interface OnStartVideoPlayerListener {
        fun onStartVideoPlayer(mp: VideoPlayer)
    }

    interface OnPauseVideoPlayerListener {
        fun onPauseVideoPlayer(mp: VideoPlayer)
    }

    interface OnStartPrepareVideoPlayerListener {
        fun onStartPrepareVideoPlayerListener(vp: VideoPlayer)
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

    private class OnPrepareListeners: OnPreparedListener {
        val listeners = mutableListOf<OnPreparedListener>()
        override fun onPrepared(mp: MediaPlayer?) {
            listeners.forEach { it.onPrepared(mp) }
        }
    }

    private class OnCompletionListeners: OnCompletionListener {
        val listeners = mutableListOf<OnCompletionListener>()
        override fun onCompletion(mp: MediaPlayer?) {
            listeners.forEach { it.onCompletion(mp) }
        }
    }

    private class OnStartVideoPlayerListeners: OnStartVideoPlayerListener {
        val listeners = mutableListOf<OnStartVideoPlayerListener>()
        override fun onStartVideoPlayer(mp: VideoPlayer) {
            listeners.forEach { it.onStartVideoPlayer(mp) }
        }
    }

    private class OnStartPrepareVideoPlayerListeners: OnStartPrepareVideoPlayerListener {
        val listeners = mutableListOf<OnStartPrepareVideoPlayerListener>()
        override fun onStartPrepareVideoPlayerListener(vp: VideoPlayer) {
            listeners.forEach{ it.onStartPrepareVideoPlayerListener(vp) }
        }
    }

    private class OnPauseVideoPlayerListeners: OnPauseVideoPlayerListener {
        val listeners = mutableListOf<OnPauseVideoPlayerListener>()
        override fun onPauseVideoPlayer(vp: VideoPlayer) {
            listeners.forEach{ it.onPauseVideoPlayer(vp) }
        }
    }

    private class OnTimeVideoPlayerListeners: OnTimeVideoPlayerListener {
        val listeners = mutableListOf<OnTimeVideoPlayerListener>()
        override fun onTimeVideoPlayerListener(vp: VideoPlayer) {
            listeners.forEach{ it.onTimeVideoPlayerListener(vp) }
        }
    }
}