package com.example.videoview.video

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.os.Message

private const val TIME_EVENT = 1

class VideoPlayer: MediaPlayer() {
    var onStartVideoPlayerListener: OnStartVideoPlayerListener? = null
    var onPauseVideoPlayerListener: OnPauseVideoPlayerListener? = null
    var onPrepareVideoPlayerListener: OnStartPrepareVideoPlayerListener? = null
    var onTimeVideoPlayerListener: OnTimeVideoPlayerListener? = null

    private val onPrepareListeners = OnPrepareListeners().also {
        super.setOnPreparedListener(it)
    }

    private val timeHandler = TimeHandler(this, Looper.getMainLooper()!!)

    override fun start() {
        super.start()
        timeHandler.sendEmptyMessage(TIME_EVENT)
        onStartVideoPlayerListener?.onStartVideoPlayer(this)
    }

    override fun pause() {
        super.pause()
        timeHandler.removeMessages(TIME_EVENT)
        onPauseVideoPlayerListener?.onPauseVideoPlayer(this)
    }

    override fun prepareAsync() {
        super.prepareAsync()
        onPrepareVideoPlayerListener?.onPrepareVideoPlayer(this)
    }

    override fun setOnCompletionListener(listener: OnCompletionListener?) {
        super.setOnCompletionListener { mp ->
            timeHandler.removeMessages(TIME_EVENT)
            listener?.onCompletion(mp)
        }
    }

    override fun setOnPreparedListener(listener: OnPreparedListener?) {
        listener ?: return
        onPrepareListeners.listeners.add(listener)
    }






    interface OnStartVideoPlayerListener {
        fun onStartVideoPlayer(mp: VideoPlayer)
    }

    interface OnPauseVideoPlayerListener {
        fun onPauseVideoPlayer(mp: VideoPlayer)
    }

    interface OnStartPrepareVideoPlayerListener {
        fun onPrepareVideoPlayer(vp: VideoPlayer)
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
                    onTimeVideoPlayerListener?.onTimeVideoPlayerListener(this@VideoPlayer)
                    sendMessageDelayed(obtainMessage(TIME_EVENT), 1000)
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
}